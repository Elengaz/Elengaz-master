package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Details;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Orders;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Profile;
import com.SemiColon.Hmt.elengaz.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import me.anwarshahriar.calligrapher.Calligrapher;

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
        office_id=intent.getStringExtra("office_id");

    }

    private void initView() {
        bNav = findViewById(R.id.bNav);
        bNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bNav.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
        bNav.setAccentColor(Color.parseColor("#45A059"));
        bNav.setInactiveColor(Color.parseColor("#9E9E9E"));
        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(this, R.menu.nav_menu);
        adapter.setupWithBottomNavigation(bNav);

        if (bNav.getCurrentItem()==0)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Orders()).commit();
        }

        bNav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position)
                {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Orders()).commit();
                        bNav.setCurrentItem(position,false);
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Details()).commit();
                        bNav.setCurrentItem(position,false);
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Profile()).commit();
                        bNav.setCurrentItem(position,false);
                        break;

                }
                return false;
            }
        });

    }

}
