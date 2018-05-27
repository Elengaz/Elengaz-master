package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contact extends AppCompatActivity {
    private String m_name,m_message,m_subject;
    private EditText name,subj,msg;
    private Button sendEmailBtn;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initView();
        CreateProgressDialog();

    }

    private void CreateProgressDialog() {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.s));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminateDrawable(drawable);

    }

    private void initView() {
        name=  findViewById(R.id.your_name);
        subj=  findViewById(R.id.your_subject);
        msg =  findViewById(R.id.your_message);
        sendEmailBtn =  findViewById(R.id.post_message);
        sendEmailBtn.setOnClickListener(view ->  {
            {
                m_name      = name.getText().toString();
                m_subject   = subj.getText().toString();
                m_message   = msg.getText().toString();

                if (TextUtils.isEmpty(m_name))
                {
                    name.setError(getString(R.string.en));
                }else if (TextUtils.isEmpty(m_subject))
                {
                    name.setError(null);
                    subj.setError(getString(R.string.esub));
                }else if (TextUtils.isEmpty(m_message))
                {
                    subj.setError(null);
                    msg.setError(getString(R.string.em));
                }else
                {
                    dialog.show();
                    msg.setError(null);
                    sendDataToServer(m_name,m_message,m_subject);

                }

            }});
    }

    private void sendDataToServer(String m_name, String m_message, String m_subject) {


        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<ResponseModel> userCall = service.ContactUs(m_name,m_subject,m_message);

        userCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (response.body().getSuccess()==1)
                    {
                        getEmail(m_subject,m_message);

                    }else
                        {
                            Toast.makeText(Contact.this, "failed", Toast.LENGTH_SHORT).show();

                        }
                }else
                {
                    dialog.dismiss();
                    Toast.makeText(Contact.this, "failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                dialog.dismiss();

                Toast.makeText(Contact.this, R.string.something_went_haywire, Toast.LENGTH_SHORT).show();

                Log.d("onFailure", t.toString());

            }
        });    }

    private void getEmail(String m_subject, String m_message) {

        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<ResponseModel> call = service.getEmail();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getSuccess()==1)
                    {
                        if (!TextUtils.isEmpty(response.body().getEmail()))
                        {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/rfc822");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{response.body().getEmail().toString()});
                            intent.putExtra(Intent.EXTRA_SUBJECT, m_subject);
                            intent.putExtra(Intent.EXTRA_TEXT, m_message);

                            startActivity(Intent.createChooser(intent, "Send Email"));
                            finish();
                        }
                    }else
                        {

                            dialog.dismiss();
                            Toast.makeText(Contact.this, R.string.something_went_haywire, Toast.LENGTH_SHORT).show();

                        }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                dialog.dismiss();

                Log.e("Error",t.getMessage());
                Toast.makeText(Contact.this, R.string.something_went_haywire, Toast.LENGTH_SHORT).show();
            }
        });

    }

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