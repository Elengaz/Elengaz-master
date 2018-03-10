package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.Preferences;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Register_Client_Model;
import com.SemiColon.Hmt.elengaz.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText username,password;
    private Button login;
    private TextView signup;
    private ProgressDialog pDialog;
    String id;
    private Preferences preferences;
    private ShimmerTextView txt_shimmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);
        preferences = new Preferences(this);
        initView();
        Log.e("ddddddddddddddddddd",FirebaseInstanceId.getInstance().getToken());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(username.getText().toString())){
                    username.setError(getString(R.string.empty_username));
                }
                else if (TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError(getString(R.string.empty_password));
                }else
                    {
                        loginByServer();

                    }

            }
        });
    }

    private void initView() {
        username=findViewById(R.id.edt_user_name);
        password=findViewById(R.id.edt_user_pass);
        login=findViewById(R.id.btnlogin);
        signup=findViewById(R.id.txtsignup);
        txt_shimmer = findViewById(R.id.txt_shimmer);

        Shimmer shimmer = new Shimmer();
        shimmer .setDuration(1500)
                .setStartDelay(300);
        shimmer.start(txt_shimmer);
    }

    private void loginByServer() {
        pDialog = new ProgressDialog(Login.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage(getString(R.string.login));
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(false);

        showpDialog();

        String user = username.getText().toString();
        String pass = password.getText().toString();


        ServicesApi service = APIClient.getClient().create(ServicesApi.class);

        Call<Register_Client_Model> userCall = service.userLogIn(user,pass);

        userCall.enqueue(new Callback<Register_Client_Model>()
        {
            @Override
            public void onResponse(Call<Register_Client_Model> call, Response<Register_Client_Model> response) {
                hidepDialog();
                if (response.isSuccessful())
                {
                    if (response.body().getSuccess() == 1) {
                        id=response.body().getClient_id();
                        preferences.CreateSharedPref(id,"client","logged_in");

                        Intent i=new Intent(Login.this, Main_Home.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("id",id);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Login.this,getString(R.string.faild), Toast.LENGTH_SHORT).show();
                    }
                }else
                    {
                        Toast.makeText(Login.this,getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

                    }

            }

            @Override
            public void onFailure(Call<Register_Client_Model> call, Throwable t) {
                hidepDialog();
                Toast.makeText(Login.this,getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
    }

    public void onLoginSuccess() {
        login.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    public static String convertPassMd5(String pass) {

        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
