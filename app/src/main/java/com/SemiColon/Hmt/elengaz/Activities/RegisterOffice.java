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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.ProfileModel;
import com.SemiColon.Hmt.elengaz.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterOffice extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignupActivity";
    EditText username,password ,phone, email,title,city,area;
    Button register;
    private ImageView doneBtn;
    private ProgressDialog pDialog;
    private CircleImageView user_photo;
    private final int IMAGE_REQ=125;
    private String enCodedImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_office);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);
        username=findViewById(R.id.edtusername);
        password=findViewById(R.id.edtpass);
        email=findViewById(R.id.edtemail);

        phone=findViewById(R.id.edtphone);
        title=findViewById(R.id.edt_office_title);
        city=findViewById(R.id.edt_office_city);
        area=findViewById(R.id.edt_office_area);
        user_photo = findViewById(R.id.user_photo);
        doneBtn = findViewById(R.id.doneBtn);

        user_photo.setOnClickListener(this);
        doneBtn.setOnClickListener(this);

        //register=findViewById(R.id.register);

       /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              *//*  Officcer officer=new Officcer(
                        username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        title.getText().toString(),
                        city.getText().toString(),
                        area.getText().toString()


                );*//*
                signup();

                // sendNetworkRequest(user);
            }
        });*/


    }


    public void signup() {
       /* Log.d(TAG, "Signup");

        if (validate() == false) {
            onSignupFailed();
            return;
        }*/
        String name = username.getText().toString();
        String pass = password.getText().toString();
        String uemail = email.getText().toString();
        String mobile = phone.getText().toString();
        String otitle = title.getText().toString();
        String ocity = city.getText().toString();
        String oarea=area.getText().toString();

        if (TextUtils.isEmpty(enCodedImage))
        {
            Toast.makeText(this, R.string.sel_per_photo, Toast.LENGTH_LONG).show();

        }
        if (name.isEmpty() || name.length() < 3) {
            username.setError(getString(R.string.at_least_3ch));
        } else {
            username.setError(null);
        }


        if (uemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(uemail).matches()) {
            email.setError(getString(R.string.inv_email));
        } else {
            email.setError(null);
        }


        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            password.setError(getString(R.string.pass_bet_4_10));
        } else {
            password.setError(null);
        }

        if (mobile.isEmpty() || !Patterns.PHONE.matcher(mobile).matches()) {
            phone.setError(getString(R.string.inv_phone));
        } else {
            phone.setError(null);
        }

        if (otitle.isEmpty() || otitle.length() < 3) {
            title.setError(getString(R.string.title_3ch));
        } else {
            title.setError(null);
        }
        if (ocity.isEmpty() || ocity.length() < 3) {
            city.setError(getString(R.string.city_at_3ch));
        } else {
            city.setError(null);
        }
        if (oarea.isEmpty() || oarea.length() < 3) {
            area.setError(getString(R.string.address_at_3));
        } else {
            area.setError(null);
        }
        saveToServerDB();

    }


    public void onSignupSuccess() {
        register.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        register.setEnabled(true);
    }

   /* public boolean validate() {


    }*/

    private void saveToServerDB() {
        pDialog = new ProgressDialog(RegisterOffice.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String name = username.getText().toString();
        String pass = password.getText().toString();

        String uemail = email.getText().toString();
        String mobile = phone.getText().toString();
        String otitle = title.getText().toString();
        String ocity = city.getText().toString();
        String oarea=area.getText().toString();
        final String taken_id= FirebaseInstanceId.getInstance().getToken();
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);


        Call<ProfileModel> userCall = service.officeSignUp(name,pass, uemail, mobile,otitle,ocity,taken_id,oarea);
        // startActivity(new Intent(Register.this, ListMarma.class));

        userCall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                hidepDialog();
                //onSignupSuccess();
//                Log.d("onResponse", "" + response.body().getMessage());


                if (response.isSuccessful()) {
                    startActivity(new Intent(RegisterOffice.this, OfficeLogin.class));
                  //  Toast.makeText(RegisterOffice.this, ""+taken_id, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterOffice.this, "failed" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                hidepDialog();
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
            case R.id.user_photo:
                SelectPhoto();
                break;
            case R.id.doneBtn:
                signup();
                break;
        }
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
    private void SelectPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,getString(R.string.choose_photo)),IMAGE_REQ);

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
