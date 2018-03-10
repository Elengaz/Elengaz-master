package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Adapters.OfficesAdapter;
import com.SemiColon.Hmt.elengaz.Model.Client_Model;
import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OfficeWork extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Officces> model;
    private OfficesAdapter adapter;
    private RecyclerView recyclerView;
    private Button add,btnsearchrate,btnsearchplace;
    String client_id,service_id,category_id;
    ArrayList<String> ids_list;
    private SearchView searchView;
    private Toolbar mToolBar;
    private ImageView back;
    String client_service_id;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    public Client_Model client_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_work);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        initView();
        getDataFromIntent();
        getOffices();

    }
    private void initView() {


        mToolBar = findViewById(R.id.mToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = findViewById(R.id.progBar);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ids_list=new ArrayList<>();
        model=new ArrayList<>();

        add=findViewById(R.id.btnadd);
        btnsearchrate = findViewById(R.id.btnsearchrate);
        btnsearchplace=findViewById(R.id.btnsearchplace);



        add.setOnClickListener(this);
        btnsearchrate.setOnClickListener(this);
        btnsearchplace.setOnClickListener(this);


        searchView=  findViewById(R.id.searchView);
        searchView.setQueryHint(Html.fromHtml("<font color = #000>" + "الرياض" + "</font>"));
        recyclerView=findViewById(R.id.recycle_offices);
        recyclerView.setLayoutManager(new LinearLayoutManager(OfficeWork.this));
        recyclerView.setHasFixedSize(true);
        adapter=new OfficesAdapter(OfficeWork.this,model);
        recyclerView.setAdapter(adapter);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        ProgressBar  bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.add_service));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminateDrawable(drawable);



    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        if (intent!=null)
        {
            client_id= intent.getStringExtra("client_id");
            service_id= intent.getStringExtra("service_id");
            category_id=intent.getStringExtra("category_id");
            client_model = (Client_Model) intent.getSerializableExtra("client_data");
            // Toast.makeText(this, "get data"+service_id, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnadd:
             //   Toast.makeText(this, ""+service_id, Toast.LENGTH_SHORT).show();
                addOffices();
                break;
            case R.id.btnsearchrate:
             //   Toast.makeText(this, "toasssssss", Toast.LENGTH_SHORT).show();
                searchByRate_Place("2");

                break;
            case R.id.btnsearchplace:
                searchByRate_Place("1");
              //  Toast.makeText(this, "tos", Toast.LENGTH_SHORT).show();

                break;
        }
    }



    private void getOffices()
    {
        ServicesApi service= APIClient.getClient().create(ServicesApi.class);
        Call<List<Officces>> call=service.getofficces();
        call.enqueue(new Callback<List<Officces>>() {
            @Override
            public void onResponse(Call<List<Officces>> call, Response<List<Officces>> response) {

                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    model.clear();
                    model.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
              //  Toast.makeText(OfficeWork.this, ""+response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Officces>> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    private void addOffices()
    {
        if (ids_list.size()>0)
        {
            dialog.show();
            ServicesApi servicesApi=APIClient.getClient().create(ServicesApi.class);
            Call<Officces> call=servicesApi.sendoffices(ids_list,client_id,category_id);
            call.enqueue(new Callback<Officces>() {
                @Override
                public void onResponse(Call<Officces> call, Response<Officces> response) {

                    if (response.isSuccessful()){
                        client_service_id=response.body().getClient_service_id();
                        //  Toast.makeText(OfficeWork.this, ""+ids_list+" "+client_id+" "+ service_id, Toast.LENGTH_SHORT).show();
                        //   Toast.makeText(OfficeWork.this, ""+client_service_id, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(OfficeWork.this,AddService.class);
                        intent.putExtra("client_id",client_id);
                        intent.putExtra("service_id",client_service_id);
                        intent.putExtra("client_data",client_model);
                        dialog.dismiss();
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<Officces> call, Throwable t) {
                    Log.e("error",t.getMessage());
                    dialog.dismiss();
                }
            });

        }else
            {
                  Toast.makeText(OfficeWork.this, ""+getString(R.string.choose_office), Toast.LENGTH_SHORT).show();

            }


    }

    private void searchByRate_Place(String type)
    {
        String service = searchView.getQuery().toString();
        if (TextUtils.isEmpty(service))
        {
            Toast.makeText(this,R.string.add_srv_u_want, Toast.LENGTH_SHORT).show();
        }else
        {

           // Toast.makeText(this, ""+service+"   "+type, Toast.LENGTH_SHORT).show();
            Map<String,String> map = new HashMap<>();
             map.put("type",type);
            map.put("search_office_place",service);

            Retrofit retrofit = APIClient.getClient();
            ServicesApi servicesApi = retrofit.create(ServicesApi.class);

            Call<List<Officces>> call=servicesApi.searchByRate(map);

            call.enqueue(new Callback<List<Officces>>() {
                @Override
                public void onResponse(Call<List<Officces>> call, Response<List<Officces>> response) {
                    if (response.isSuccessful())
                    {
                        List<Officces> officcesList = response.body();

                       // Toast.makeText(OfficeWork.this, "انا ف ان سكسسفل", Toast.LENGTH_SHORT).show();
                        if (officcesList.size()>0)
                        {
                         //   Toast.makeText(OfficeWork.this, "انا ف ان الاراي اكبر من صفر", Toast.LENGTH_SHORT).show();

                            Intent intent1 = new Intent(OfficeWork.this,Activity_Search_Results.class);
                            intent1.putExtra("search", (Serializable) officcesList);
                            intent1.putExtra("clientId",client_id);
                            intent1.putExtra("service_id",service_id);
                            intent1.putExtra("category_id",category_id);
                            startActivity(intent1);

                        }
                        else
                        {
                            Toast.makeText(OfficeWork.this, "empty data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                      //  Toast.makeText(OfficeWork.this, "انا مبرجعش رسبونس", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Officces>> call, Throwable t) {
//                    Log.e("error",t.getMessage());
                }
            });
        }
    }


    public void setPosition(View v,int position)
    {
        if (((CheckBox) v).isChecked()){
            ids_list.add(model.get(position).getOffice_id());
        }else {
            ids_list.remove(model.get(position).getOffice_id());
        }
    }

}
