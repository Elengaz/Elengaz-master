package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class OrderState extends AppCompatActivity {

    TextView txtrate;
    private String client_service_id,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_state);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        txtrate=findViewById(R.id.txtrate);

        getDateFromIntent();

        txtrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OrderState.this,AddRate.class);
                i.putExtra("client_service_id",client_service_id);
                startActivity(i);
            }
        });
    }

    private void getDateFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("client_service_id"))
            {
                client_service_id = intent.getStringExtra("client_service_id");
                state=intent.getStringExtra("state");
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);
        String user_type = pref.getString("user_type","");

        if (!TextUtils.isEmpty(user_type))
        {
            if (user_type.equals("client"))
            {
                String id = pref.getString("id","");

                if (!TextUtils.isEmpty(id))
                {
                    Intent intent = new Intent(this,Client_Response_Orders.class);
                    intent.putExtra("client_id",id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }
    }
}
