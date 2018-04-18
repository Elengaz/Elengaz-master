package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.AboutUsModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        initView();

        GetDataFromServer();
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
        Call<List<AboutUsModel>> call = servicesApi.GetAboutUs();
        call.enqueue(new Callback<List<AboutUsModel>>() {
            @Override
            public void onResponse(Call<List<AboutUsModel>> call, Response<List<AboutUsModel>> response) {

                if (response.isSuccessful()) {

                    title.setText(response.body().get(0).getTitle());
                    content.setText(response.body().get(0).getContent());
                    facebook_url = response.body().get(4).getFacebook_url();
                    twitter_url = response.body().get(4).getTwitter_url();
                    instgram_url = response.body().get(4).getInstgram_url();
                    google_url = response.body().get(4).getGoogle_url();

                }

            }

            @Override
            public void onFailure(Call<List<AboutUsModel>> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_facebook:
                if (facebook_url!=null|| !TextUtils.isEmpty(facebook_url))
                {
                    if (facebook_url.startsWith("https://")){
                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url",facebook_url);
                        startActivity(intent);
                    }
                    else {

                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url","https://"+facebook_url);
                        startActivity(intent);
                    }

                }else
                    {
                        Toast.makeText(this, "لا يوجد إيميل فيس بوك", Toast.LENGTH_SHORT).show();
                    }

                break;
            case R.id.img_twitter:
                if (twitter_url!=null|| !TextUtils.isEmpty(twitter_url))
                {
                    if (twitter_url.startsWith("https://")){
                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url",twitter_url);
                        startActivity(intent);
                    }
                    else {

                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url","https://"+twitter_url);
                        startActivity(intent);
                    }
                }else
                {
                    Toast.makeText(this, "لا يوجد إيميل تويتر", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_instgram:
                if (instgram_url!=null|| !TextUtils.isEmpty(instgram_url))
                {
                    if (instgram_url.startsWith("https://")){
                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url",instgram_url);
                        startActivity(intent);
                    }
                    else {

                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url","https://"+instgram_url);
                        startActivity(intent);
                    }
                }else
                {
                    Toast.makeText(this, "لا يوجد إيميل إنستجرام", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_google:
                if (google_url!=null|| !TextUtils.isEmpty(google_url))
                {
                    if (google_url.startsWith("https://")){
                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url",google_url);
                        startActivity(intent);
                    }
                    else {

                        Intent intent=new Intent(About.this,WebViewActivity.class);
                        intent.putExtra("url","https://"+google_url);
                        startActivity(intent);
                    }
                }else
                {
                    Toast.makeText(this, "لا يوجد إيميل جوجل", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }
}
