package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.AboutUsModel;
import com.SemiColon.Hmt.elengaz.R;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class About extends AppCompatActivity implements View.OnClickListener {
    private TextView title, content;
    private ImageView facebook, twitter, instgram, google;
    private String facebook_url, twitter_url, instgram_url, google_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

        GetDataFromServer();
        initView();
    }

    private void initView() {

        title = findViewById(R.id.txt_title);
        content = findViewById(R.id.txt_content);
        facebook = findViewById(R.id.img_facebook);
        twitter = findViewById(R.id.img_twitter);
        instgram = findViewById(R.id.img_instgram);
        google = findViewById(R.id.img_google);

        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        instgram.setOnClickListener(this);
        google.setOnClickListener(this);


    }

    private void GetDataFromServer() {

        ServicesApi servicesApi = APIClient.getClient().create(ServicesApi.class);
        Call<AboutUsModel> call = servicesApi.GetAboutUs();
        call.enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {

                if (response.isSuccessful()) {

                    title.setText(response.body().getTitle());
                    content.setText(response.body().getContent());
                    facebook_url = response.body().getFacebook_url();
                    twitter_url = response.body().getTwitter_url();
                    instgram_url = response.body().getInstgram_url();
                    google_url = response.body().getGoogle_url();

                }

            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_facebook:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(facebook_url));
                startActivity(i);
                break;
            case R.id.img_twitter:
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(twitter_url));
                startActivity(i2);
                break;
            case R.id.img_instgram:
                Intent i3 = new Intent(Intent.ACTION_VIEW);
                i3.setData(Uri.parse(instgram_url));
                startActivity(i3);
                break;
            case R.id.img_google:
                Intent i4 = new Intent(Intent.ACTION_VIEW);
                i4.setData(Uri.parse(google_url));
                startActivity(i4);
                break;

        }

    }
}
