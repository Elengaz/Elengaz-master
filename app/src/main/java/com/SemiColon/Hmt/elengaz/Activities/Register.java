package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.Preferences;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.MSG;
import com.SemiColon.Hmt.elengaz.Model.User;
import com.SemiColon.Hmt.elengaz.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignupActivity";
    private EditText username,password ,phone, email;
   // private Button register;
    private ProgressDialog pDialog;
    private ImageView doneBtn;
    private CircleImageView user_photo;
    private final int IMAGE_REQ=120;
    private String enCodedImage="";
    User user;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);
        preferences = new Preferences(this);

        initView();


        /*doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               *//* user=new User(
                        username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString()

                );*//*

                // sendNetworkRequest(user);
            }
        });*/


    }

    private void initView()
    {
        username=findViewById(R.id.edtusername);
        password=findViewById(R.id.edtpass);
        email=findViewById(R.id.edtemail);
        phone=findViewById(R.id.edtphone);
        doneBtn = findViewById(R.id.doneBtn);
        user_photo = findViewById(R.id.user_photo);


        doneBtn.setOnClickListener(this);
        user_photo.setOnClickListener(this);

        //register=findViewById(R.id.register);
    }


    public void signup() {
       /* Log.d(TAG, "Signup");

        if (validate() == false) {
            onSignupFailed();
            return;
        }*/
        /*if (TextUtils.isEmpty(enCodedImage))
        {
            Toast.makeText(this, R.string.sel_per_photo, Toast.LENGTH_LONG).show();
        }*/
        if (TextUtils.isEmpty(username.getText().toString()))
        {
            username.setError(getString(R.string.empty_username));
        }else
            {
                username.setError(null);
            }
        if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError(getString(R.string.empty_password));
        }else
            {
                password.setError(null);
            }
        if (TextUtils.isEmpty(email.getText().toString()))
        {
            email.setError(getString(R.string.empty_email));
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            email.setError(getString(R.string.inv_email));
        }else
            {
                email.setError(null);
            }
        if (TextUtils.isEmpty(phone.getText().toString()))
        {
            phone.setError(getString(R.string.empty_phone));
        }else if (!Patterns.PHONE.matcher(phone.getText().toString()).matches())
        {
            phone.setError(getString(R.string.inv_phone));
        }else
            {
                phone.setError(null);
            }


            saveToServerDB();

            //signup();



    }


    public void onSignupSuccess() {
        //register.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

   /* public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        register.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = username.getText().toString();
        String uemail = email.getText().toString();
        String pass = password.getText().toString();
        String mobile = phone.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            username.setError("at least 3 characters");
            valid = false;
        } else {
            username.setError(null);
        }


        if (uemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(uemail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 8 || mobile.length() > 13) {
            phone.setError("phone Do not match");
            valid = false;
        } else {
            phone.setError(null);
        }

        return valid;
    }*/

    private void saveToServerDB() {
        pDialog = new ProgressDialog(Register.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage(getString(R.string.create_one));
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(false);

        showpDialog();

        String name = username.getText().toString();
        String pass = password.getText().toString();

        String uemail = email.getText().toString();
        String mobile = phone.getText().toString();
        final String token_id= FirebaseInstanceId.getInstance().getToken();

        ServicesApi service = APIClient.getClient().create(ServicesApi.class);


        Call<MSG> userCall = service.userSignUp(name,pass, uemail, mobile,token_id);
        // startActivity(new Intent(Register.this, ListMarma.class));

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();

                if (response.isSuccessful())
                {
                    if (response.body().getSuccess() == 1) {

                        startActivity(new Intent(Register.this, Home.class));
                        //  Toast.makeText(Register.this, "email"+user.getEmail()+"10"+user.getPassword(), Toast.LENGTH_SHORT).show();

                        //  Toast.makeText(Register.this, ""+taken_id, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, "" +getString(R.string.faild), Toast.LENGTH_SHORT).show();
                    }
                }else
                    {
                        Toast.makeText(Register.this, "" +getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

                    }

            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Toast.makeText(Register.this, "" +getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

                Log.d("onFailure", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.doneBtn:
                signup();
                break;
            case R.id.user_photo:
                SelectPhoto();
        }

    }

    private void SelectPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,getString(R.string.choose_photo)),IMAGE_REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMAGE_REQ && resultCode==RESULT_OK && data!=null)
        {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                user_photo.setImageBitmap(bitmap);
                enCodedImage = enCodeImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String enCodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
        byte [] bytes = outputStream.toByteArray();

        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
    /*private void sendNetworkRequest(User user){
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://clup.alatheertech.com/Api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ServicesApi client=retrofit.create(ServicesApi.class);
       Call<User>call= client.createAccount(user);
       call.enqueue(new Callback<User>() {
           @Override
           public void onResponse(Call<User> call, Response<User> response) {
               Toast.makeText(Register.this, "Yeah ,User Id ", Toast.LENGTH_SHORT).show();
               Intent i=new Intent(Register.this,ListMarma.class);
               startActivity(i);
           }

           @Override
           public void onFailure(Call<User> call, Throwable t) {

               Toast.makeText(Register.this, "somthing wrong", Toast.LENGTH_SHORT).show();

           }
       });

    }*/
}
