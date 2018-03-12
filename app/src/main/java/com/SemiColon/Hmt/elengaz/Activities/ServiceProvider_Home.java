package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Details;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Officer_Services;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Orders;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Profile;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProvider_Home extends AppCompatActivity{
    private AHBottomNavigation bNav;
    public String office_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider_home);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        initView();
        get_Intent();

    }

    private void get_Intent() {
        Intent intent=getIntent();
        if (intent!=null) {
            if (intent.hasExtra("office_id"))
            {
                office_id = intent.getStringExtra("office_id");

            }else
                {
                    SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);
                    String ofid =pref.getString("id","");
                    if (TextUtils.isEmpty(ofid))
                    {
                        office_id = ofid;
                    }
                }
        }
    }

    private void initView() {
        bNav = findViewById(R.id.bNav);
        bNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bNav.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
        bNav.setAccentColor(Color.parseColor("#45A059"));
        bNav.setInactiveColor(Color.parseColor("#9E9E9E"));
        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(this, R.menu.nav_menu);
        adapter.setupWithBottomNavigation(bNav);

        if (bNav.getCurrentItem()==1)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Orders()).commit();
        }

        bNav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position)
                {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Officer_Services()).commit();
                        bNav.setCurrentItem(position,false);
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Orders()).commit();
                        bNav.setCurrentItem(position,false);
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Details()).commit();
                        bNav.setCurrentItem(position,false);
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Profile()).commit();
                        bNav.setCurrentItem(position,false);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        UpdateToken();
    }

    private void UpdateToken() {
        Map<String,String> map = new HashMap<>();
        map.put("office_id",office_id);
        map.put("new_token_id", FirebaseInstanceId.getInstance().getToken());
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<ResponseModel> call = service.UpdateOfficeToken(map);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getSuccess()==1)
                    {
                        Log.e("office_token","updated successfully");

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }
}
