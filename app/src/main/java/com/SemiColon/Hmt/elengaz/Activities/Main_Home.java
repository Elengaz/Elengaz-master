package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.Preferences;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.API.Service.Tags;
import com.SemiColon.Hmt.elengaz.Adapters.ServicesAdapter;
import com.SemiColon.Hmt.elengaz.Model.Client_Model;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.Model.Services;
import com.SemiColon.Hmt.elengaz.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Services> Model;
    private ServicesAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Toolbar toolbar;
    private Preferences preferences;
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;
    private CircleImageView user_image;
    private TextView user_email,user_phone;
    public String id;
    public Client_Model client_model;
    private ProgressBar progBar;
    private Target target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        initView();
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
                    public void onResponse(Call<List<Services>> call, Response<List<Services>> response) {

                        if (response.isSuccessful()) {

                            List<Services> servicesList = response.body();
                            Intent intent1 = new Intent(Main_Home.this,Activity_Search_Results.class);

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


        drawerLayout =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view =  findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        View view  = nav_view.getHeaderView(0);
        user_image = view.findViewById(R.id.user_photo);
        user_email = view.findViewById(R.id.user_email);
        user_phone = view.findViewById(R.id.user_phone);
        searchView=  findViewById(R.id.searchView);
        searchView.setQueryHint(Html.fromHtml("<font color = #000>" + "ابحث عن خدمه" + "</font>"));

        progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        recyclerView = findViewById(R.id.recyc_service);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setHasFixedSize(true);
        adapter=new ServicesAdapter(this,Model);
        recyclerView.setAdapter(adapter);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

    }
    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("id"))
            {
                id=intent.getStringExtra("id");

            }else
                {
                    SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);

                    String cid =pref.getString("id","");
                    if (!TextUtils.isEmpty(cid))
                    {
                        id=cid;
                    }
                }

        }


    }
    private void getServiceData()
    {
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);

        Call<List<Services>> call = service.getServicesData();
        call.enqueue(new Callback<List<Services>>() {
            @Override
            public void onResponse(Call<List<Services>> call, Response<List<Services>>response) {

                if (response.isSuccessful()){
                    Model.clear();
                    Model.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progBar.setVisibility(View.GONE);
                }else {

                    Toast.makeText(Main_Home.this, ""+getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<List<Services>> call, Throwable t) {
                Toast.makeText(Main_Home.this, ""+getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void LogOut()
    {
        preferences.ClearSharedPref();
        Intent intent = new Intent(this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    private void Display_ClientData()
    {
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<Client_Model> call = service.DisplayClientData(id);

        call.enqueue(new Callback<Client_Model>() {
            @Override
            public void onResponse(Call<Client_Model> call, Response<Client_Model> response) {
                if (response.isSuccessful())
                {
                    client_model = response.body();
                    Update_UI(client_model);
                }else
                    {
                        Toast.makeText(Main_Home.this,getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<Client_Model> call, Throwable t) {
                Toast.makeText(Main_Home.this,getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Update_UI(Client_Model client_model) {

        user_email.setText(client_model.getClient_email());
        user_phone.setText(client_model.getClient_phone());

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                user_image.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        if (client_model.getClient_img()!=null||!TextUtils.isEmpty(client_model.getClient_img()))
        {
            Picasso.with(Main_Home.this).load(Tags.Image_Path+client_model.getClient_img()).into(target);
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getServiceData();
        Display_ClientData();
        UpdateToken();
    }
    private void UpdateToken() {
        Map<String,String> map = new HashMap<>();
        map.put("client_id",id);
        map.put("new_token_id", FirebaseInstanceId.getInstance().getToken());
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<ResponseModel> call = service.UpdateToken(map);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                {
                    /*if (response.body().getSuccess()==1)
                    {
                        Log.e("client_token","updated successfully");
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.profile_menu) {

            if (client_model!=null)
            {
                if (!TextUtils.isEmpty(client_model.getClient_phone())||client_model.getClient_phone()!=null&&!TextUtils.isEmpty(client_model.getClient_email())||client_model.getClient_email()!=null&&client_model.getClient_img()!=null)
                {
                    Intent intent = new Intent(Main_Home.this, Client_Profile_Activity.class);
                    intent.putExtra("client_model",client_model);
                    intent.putExtra("client_id",id);

                    startActivity(intent);
                }else
                {
                    Toast.makeText(this, getString(R.string.connection), Toast.LENGTH_SHORT).show();
                }
            }



        } else if (item.getItemId() == R.id.myservice_menu) {
            Intent intent = new Intent(Main_Home.this, Client_Response_Orders.class);
            intent.putExtra("client_id",id);
            startActivity(intent);

        }
        else if (item.getItemId() == R.id.contact_menu) {
            Intent intent = new Intent(Main_Home.this, Contact.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.about_menu) {
            Intent intent = new Intent(Main_Home.this, About.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.Terms_menu) {
            Intent intent = new Intent(Main_Home.this, TermsActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.logout_menu) {
            LogOut();
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(target);
    }
}
