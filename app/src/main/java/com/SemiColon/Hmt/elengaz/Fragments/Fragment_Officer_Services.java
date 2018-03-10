package com.SemiColon.Hmt.elengaz.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Activities.ServiceProvider_Home;
import com.SemiColon.Hmt.elengaz.Adapters.Office_Oreders_Adapter;
import com.SemiColon.Hmt.elengaz.Adapters.Office_Services_Adapter;
import com.SemiColon.Hmt.elengaz.Model.Office_Service_Model;
import com.SemiColon.Hmt.elengaz.Model.office_order_model;
import com.SemiColon.Hmt.elengaz.R;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Fragment_Officer_Services extends Fragment {
    ArrayList<Office_Service_Model> model;
    Office_Services_Adapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    //String id;
    ServiceProvider_Home home;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_officer_services,container,false);
        home= (ServiceProvider_Home) getActivity();
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "JannaLT-Regular.ttf", true);




        recyclerView = view.findViewById(R.id.recyc_office_services);
        model = new ArrayList<>();

        mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new Office_Services_Adapter(getContext(), model);
        recyclerView.setAdapter(adapter);
       // Toast.makeText(getContext(), ""+home.office_id, Toast.LENGTH_SHORT).show();


        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<List<Office_Service_Model>> call = service.Display_OfficeService(home.office_id);
        call.enqueue(new Callback<List<Office_Service_Model>>() {
            @Override
            public void onResponse(Call<List<Office_Service_Model>> call, Response<List<Office_Service_Model>> response) {

               if (response.isSuccessful()) {
                   model.clear();
                   model.addAll(response.body());
                   adapter.notifyDataSetChanged();

               }else {
                   Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
               }


            }

            @Override
            public void onFailure(Call<List<Office_Service_Model>> call, Throwable t) {

                Log.e("mm",t+"");
                  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });





        return view;
    }


}

