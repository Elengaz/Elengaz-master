package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.Preferences;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Adapters.ServicesAdapter;
import com.SemiColon.Hmt.elengaz.Model.Services;
import com.SemiColon.Hmt.elengaz.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<Services> Model;
    private ServicesAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Toolbar toolbar;
    private Preferences preferences;
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;
    private ImageView close;

    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        preferences = new Preferences(this);
        initView();
        getDataFromIntent();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               // Toast.makeText(Home.this, ""+s, Toast.LENGTH_SHORT).show();

                ServicesApi service = APIClient.getClient().create(ServicesApi.class);

                Call<List<Services>> callApi = service.searchservice(s);
                callApi.enqueue(new Callback<List<Services>>() {
                    @Override
                    public void onResponse(Call<List<Services>> call, Response<List<Services>>response) {

                        if (response.isSuccessful()) {

                            List<Services> servicesList = response.body();
                            Intent intent1 = new Intent(Home.this,Activity_Search_Results.class);

                            intent1.putExtra("servicesList", (Serializable) servicesList);
                            intent1.putExtra("clientId",id);
                            startActivity(intent1);

                          //  Toast.makeText(Home.this, ""+response.body().get(0).getService_title(), Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onFailure(Call<List<Services>> call, Throwable t) {
                       // Toast.makeText(Home.this, "rrrrr", Toast.LENGTH_SHORT).show();


                    }
                });



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }



    private void initView()
    {
        Model=new ArrayList<>();

        toolbar = findViewById(R.id.home_toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        nav_view.setNavigationItemSelectedListener(this);

        View view = nav_view.getHeaderView(0);
        close = view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });



        searchView=  findViewById(R.id.searchView);
        searchView.setQueryHint(Html.fromHtml("<font color = #000>" + "ابحث عن خدمه" + "</font>"));

        recyclerView = findViewById(R.id.recyc_service);
        recyclerView.setLayoutManager(new GridLayoutManager(Home.this,1));
        recyclerView.setHasFixedSize(true);
        adapter=new ServicesAdapter(Home.this,Model);
        recyclerView.setAdapter(adapter);



    }
    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("id"))
            {
                id=intent.getStringExtra("id");

            }

        }
      //  Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();


    }
    private void getServiceData()
    {
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);

        Call<List<Services>> call = service.getServicesData();
        call.enqueue(new Callback<List<Services>>() {
            @Override
            public void onResponse(Call<List<Services>> call, Response<List<Services>>response) {

                if (response.isSuccessful()){
                    Model.addAll(response.body());
                    adapter.notifyDataSetChanged();}else {
                 //   Toast.makeText(Home.this, "error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<List<Services>> call, Throwable t) {
             //   Toast.makeText(Home.this, "rrrrr", Toast.LENGTH_SHORT).show();


            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);

        /* if (item.getItemId()==R.id.logout)
        {
            LogOut();
        }else if(item.getItemId()==R.id.responce){
            Intent intent=new Intent(this,Client_Response_Orders.class);
            intent.putExtra("client_id",id);
            startActivity(intent);
        }*/
    }

    private void LogOut()
    {


        preferences.ClearSharedPref();
        Intent intent = new Intent(this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getServiceData();
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                super.onBackPressed();
            }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.profile_menu:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.offer_menu:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

                break;
            case R.id.logout_menu:
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();

                break;
        }
        return false;
    }

}
