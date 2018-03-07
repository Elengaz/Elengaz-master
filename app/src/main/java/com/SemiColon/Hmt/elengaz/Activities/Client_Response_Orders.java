package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Adapters.Client_Oreders_Adapter;
import com.SemiColon.Hmt.elengaz.Model.DisplayServicesModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Client_Response_Orders extends AppCompatActivity {
    ArrayList<DisplayServicesModel> model;
    Client_Oreders_Adapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private String client_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_response_orders);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);
        Intent intent=getIntent();
        client_id= intent.getStringExtra("client_id");
        recyclerView = findViewById(R.id.recyc_client_responce);
        model = new ArrayList<>();

        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new Client_Oreders_Adapter(this, model);
        recyclerView.setAdapter(adapter);


        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<List<DisplayServicesModel>> call = service.Display_AllServiceOrder(client_id);
        call.enqueue(new Callback<List<DisplayServicesModel>>() {
            @Override
            public void onResponse(Call<List<DisplayServicesModel>> call, Response<List<DisplayServicesModel>> response) {
                model.clear();
                model.addAll(response.body());
                adapter.notifyDataSetChanged();
                //    Log.e("mmm", response.body().toString());


            }

            @Override
            public void onFailure(Call<List<DisplayServicesModel>> call, Throwable t) {

                Log.e("mm",t+"");

            }
        });




    }
}
