package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.Model.Services;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Search_Offices_Result;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Search_Services_Result;
import com.SemiColon.Hmt.elengaz.R;

import java.io.Serializable;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Activity_Search_Results extends AppCompatActivity {

    List<Services> servicesList;
    String clientId,category_id,service_id;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        initView();
        getDataFromIntentForSearch();
    }

    private void initView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDataFromIntentForSearch() {

        Intent intent = getIntent();

        if (intent!=null)
        {
            clientId = intent.getStringExtra("clientId");
            category_id=intent.getStringExtra("category_id");
            service_id=intent.getStringExtra("service_id");
            servicesList = (List<Services>) intent.getSerializableExtra("servicesList");

            if (intent.hasExtra("servicesList"))
            {

                Bundle bundle = new Bundle();
                bundle.putSerializable("servicesList", (Serializable) servicesList);
                bundle.putString("clientId",clientId);

                Fragment_Search_Services_Result fssr = new Fragment_Search_Services_Result();
                fssr.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_search_result,fssr).commit();
            }
            else if (intent.hasExtra("search"))
            {
                List<Officces> officcesList = (List<Officces>) intent.getSerializableExtra("search");
                Bundle bundle = new Bundle();
                bundle.putString("clientId",clientId);
                bundle.putString("service_id",service_id);
                bundle.putString("category_id",category_id);

                bundle.putSerializable("search", (Serializable) officcesList);

                Fragment_Search_Offices_Result fsor = new Fragment_Search_Offices_Result();
                fsor.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_search_result,fsor).commit();




            }
        }
    }


}
