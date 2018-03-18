package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Order_State_Model;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderState extends AppCompatActivity {

    private TextView order_name,order_date,order_state,office_name,rate;
    private String client_service_id,state;
    private Button doneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_state);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        initView();
        getDateFromIntent();
        getDateFromServer();

        /*rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OrderState.this,AddRate.class);
                i.putExtra("client_service_id",client_service_id);
                startActivity(i);
            }
        });*/
    }

    private void initView() {
        order_name=findViewById(R.id.txtorder);
        order_date=findViewById(R.id.txt_date);
        order_state=findViewById(R.id.txt_state);
        office_name=findViewById(R.id.txt_officename);
        rate = findViewById(R.id.order_rate);
        doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);
                String user_type = pref.getString("user_type","");
                if (!TextUtils.isEmpty(user_type))
                {
                    if (user_type.equals("client"))
                    {
                        String client_id = pref.getString("id","");

                        Intent intent = new Intent(OrderState.this,Main_Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("id",client_id);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    private void getDateFromServer() {


        ServicesApi servicesApi = APIClient.getClient().create(ServicesApi.class);
        Call<List<Order_State_Model>> call = servicesApi.ViewServiceState(client_service_id);
        call.enqueue(new Callback<List<Order_State_Model>>() {
            @Override
            public void onResponse(Call<List<Order_State_Model>> call, Response<List<Order_State_Model>> response) {
                if(response.isSuccessful()){
                    Order_State_Model order_state_model = response.body().get(0);

                    if (response.body().size()>0)
                    {
                        rate.setText(order_state_model.getClient_evaluation_state());
                        order_name.setText(order_state_model.getClient_service_name());
                        order_date.setText(order_state_model.getClient_service_date());
                        order_state.setText(order_state_model.getState_name());
                        office_name.setText(order_state_model.getOffice_name());
                    }

                    Log.e("ssssssssss",order_state_model.getClient_evaluation_state()+"\n"+order_state_model.getClient_service_name()+"\n"+order_state_model.getClient_service_date()+"\n"+order_state_model.getState_name()+"\n"+order_state_model.getOffice_name());
                }
            }

            @Override
            public void onFailure(Call<List<Order_State_Model>> call, Throwable t) {
                Log.e("error",t.getMessage());
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
