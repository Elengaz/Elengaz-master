package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;
    private LinearLayout container_noservices;
    private ImageView back;

    private String client_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_response_orders);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

        initView();
        getDataFromIntent();








    }

    private void getDataFromIntent() {
        Intent intent=getIntent();
        if (intent!=null)
        {
            client_id= intent.getStringExtra("client_id");

        }
    }

    private void initView() {

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progBar);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        container_noservices = findViewById(R.id.container_noserv);

        model = new ArrayList<>();

        recyclerView = findViewById(R.id.recy_client_response);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new Client_Oreders_Adapter(this, model);
        recyclerView.setAdapter(adapter);
    }

    private void DisplayAllServices() {
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<List<DisplayServicesModel>> call = service.Display_AllServiceOrder(client_id);
        call.enqueue(new Callback<List<DisplayServicesModel>>() {
            @Override
            public void onResponse(Call<List<DisplayServicesModel>> call, Response<List<DisplayServicesModel>> response) {
                if (response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    model.clear();
                    model.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (model.size()>0)
                    {

                        container_noservices.setVisibility(View.GONE);
                    }else
                        {

                            container_noservices.setVisibility(View.VISIBLE);
                        }
                }




            }

            @Override
            public void onFailure(Call<List<DisplayServicesModel>> call, Throwable t) {

                Log.e("mm",t+"");

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,Main_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DisplayAllServices();
    }


}
