package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Order_State_Model;
import com.SemiColon.Hmt.elengaz.Model.Register_Client_Model;
import com.SemiColon.Hmt.elengaz.R;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderState extends AppCompatActivity {

    TextView order_name,order_date,order_state,office_name,rate;
    private String client_service_id,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_state);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        initView();
        getDateFromIntent();
        getDateFromServer();

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OrderState.this,AddRate.class);
                i.putExtra("client_service_id",client_service_id);
                startActivity(i);
            }
        });
    }

    private void initView() {
        rate=findViewById(R.id.txt_rate);
        order_name=findViewById(R.id.txtorder);
        order_date=findViewById(R.id.txt_date);
        order_state=findViewById(R.id.txt_state);
        office_name=findViewById(R.id.txt_officename);

    }

    private void getDateFromServer() {


        ServicesApi servicesApi = APIClient.getClient().create(ServicesApi.class);
        Call<Order_State_Model> call = servicesApi.ViewServiceState(client_service_id);
        call.enqueue(new Callback<Order_State_Model>() {
            @Override
            public void onResponse(Call<Order_State_Model> call, Response<Order_State_Model> response) {
                if(response.isSuccessful()){
                    rate.setText(response.body().getClient_evaluation_state());
                    order_name.setText(response.body().getClient_service_name());
                    order_date.setText(response.body().getClient_service_date());
                    order_state.setText(response.body().getState_name());
                    office_name.setText(response.body().getOffice_name());
                }
            }

            @Override
            public void onFailure(Call<Order_State_Model> call, Throwable t) {

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
