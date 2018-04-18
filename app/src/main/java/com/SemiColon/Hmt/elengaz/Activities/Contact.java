package com.SemiColon.Hmt.elengaz.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.ContactModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contact extends AppCompatActivity {
    private Activity activity;
    String name,email,message,subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        final EditText your_name        =  findViewById(R.id.your_name);
       // final EditText your_email       =  findViewById(R.id.your_email);
        final EditText your_subject     =  findViewById(R.id.your_subject);
        final EditText your_message     =  findViewById(R.id.your_message);

        Button bemail =  findViewById(R.id.post_message);
        bemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 name      = your_name.getText().toString();
               //  email     = your_email.getText().toString();
                 subject   = your_subject.getText().toString();
                 message   = your_message.getText().toString();
                if (TextUtils.isEmpty(name)){
                    your_name.setError("Enter Your Name");
                    your_name.requestFocus();
                    return;
                }

                Boolean onError = false;
              /*  if (!isValidEmail(email)) {
                    onError = true;
                    your_email.setError("Invalid Email");
                    return;
                }*/

                if (TextUtils.isEmpty(subject)){
                    your_subject.setError("Enter Your Subject");
                    your_subject.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)){
                    your_message.setError("Enter Your Message");
                    your_message.requestFocus();
                    return;
                }

                sendDataToServer();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mohamedelashry323@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(intent, "Send Email"));
                finish();
            }
        });
    }

    private void sendDataToServer() {

        ServicesApi service = APIClient.getClient().create(ServicesApi.class);


        Call<ContactModel> userCall = service.ContactUs(name,subject,message);
        // startActivity(new Intent(Register.this, ListMarma.class));

        userCall.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {

                if (response.isSuccessful())
                {
                  //  Toast.makeText(Contact.this, "success", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(Contact.this, "failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
               // hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });    }

    @Override
    public void onResume() {
        super.onResume();
        //Get a Tracker (should auto-report)


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    // validating email id

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}