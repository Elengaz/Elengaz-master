package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
}
