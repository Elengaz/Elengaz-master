package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.SemiColon.Hmt.elengaz.API.Service.Network;
import com.SemiColon.Hmt.elengaz.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SelectActivity extends AppCompatActivity {

    private Button ameell,service;
    private ShimmerTextView txt_shimmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        ameell=(Button) findViewById(R.id.ameel);
        service=(Button) findViewById(R.id.service);
        txt_shimmer = findViewById(R.id.txt_shimmer);
        Shimmer shimmer = new Shimmer();
        shimmer .setDuration(1500)
                .setStartDelay(300);
        shimmer.start(txt_shimmer);


        //Log.e("token", FirebaseInstanceId.getInstance().getToken());
        ameell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SelectActivity.this,Login.class);
                startActivity(i);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SelectActivity.this,OfficeLogin.class);
                startActivity(i);
            }
        });
    }

    private void CheckLogin() {
        SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);
        String session = pref.getString("session","");

        Log.e("shared",session+"\n"+pref.getString("id","")+"\n"+pref.getString("user_type",""));

        if (Network.getConnection(this))
        {
            if (session.equals("logged_in"))
            {
                String user_type = pref.getString("user_type","");
                if (!TextUtils.isEmpty(user_type))
                {
                    if (user_type.equals("client"))
                    {
                        String id = pref.getString("id","");
                        if (!TextUtils.isEmpty(id))
                        {
                            Intent intent = new Intent(this,Main_Home.class);
                            intent.putExtra("id",id);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }else if (user_type.equals("office"))
                    {
                        String id = pref.getString("id","");
                        if (!TextUtils.isEmpty(id))
                        {
                            Intent intent = new Intent(this,ServiceProvider_Home.class);
                            intent.putExtra("office_id",id);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckLogin();

    }
}
