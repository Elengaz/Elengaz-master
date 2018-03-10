package com.SemiColon.Hmt.elengaz.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Activities.AddService;
import com.SemiColon.Hmt.elengaz.Adapters.OfficesAdapter_SearchResult;
import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Search_Offices_Result extends Fragment {

    private RecyclerView recView_searchOffice;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<Officces> officcesList;
    private List<Officces> SelectedList;
    private Button btnadd;
    private String client_service_id,client_id,category_id;
    ArrayList<String> ids_list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search_offices_result, container, false);
        initView(view);
        getDataFromSearch_Bundle();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOffices();
            }
        });
        return view;
    }



    private void initView(View view) {
        ids_list=new ArrayList<>();
        btnadd = view.findViewById(R.id.btnadd);
        SelectedList = new ArrayList<>();

        recView_searchOffice = view.findViewById(R.id.recView_searchOffice);
        manager = new LinearLayoutManager(getContext());
        recView_searchOffice.setLayoutManager(manager);
        recView_searchOffice.setHasFixedSize(true);


    }
    private void addOffices()
    {
        if (ids_list.size()>0)
        {
            ServicesApi servicesApi= APIClient.getClient().create(ServicesApi.class);
            Call<Officces> call=servicesApi.sendoffices(ids_list,client_id,category_id);
            call.enqueue(new Callback<Officces>() {
                @Override
                public void onResponse(Call<Officces> call, Response<Officces> response) {

                    if (response.isSuccessful()){
                        client_service_id=response.body().getClient_service_id();
                        //  Toast.makeText(OfficeWork.this, ""+ids_list+" "+client_id+" "+ service_id, Toast.LENGTH_SHORT).show();
                        //   Toast.makeText(OfficeWork.this, ""+client_service_id, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),AddService.class);
                        intent.putExtra("client_id",client_id);
                        intent.putExtra("service_id",client_service_id);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<Officces> call, Throwable t) {

                }
            });

        }else
        {
          //  Toast.makeText(OfficeWork.this, ""+getString(R.string.choose_office), Toast.LENGTH_SHORT).show();

        }


    }
    private void getDataFromSearch_Bundle()
    {
        Bundle bundle = getArguments();

        if (bundle!=null)
        {
            officcesList = (List<Officces>) bundle.getSerializable("search");
            client_id=bundle.getString("clientId");
            category_id=bundle.getString("category_id");
            adapter = new OfficesAdapter_SearchResult(getActivity(),officcesList,this);
            adapter.notifyDataSetChanged();
            recView_searchOffice.setAdapter(adapter);

        }
    }
    public void setPos(View view ,int pos)
    {
        Officces officces = officcesList.get(pos);
        if (((CheckBox)view).isChecked())
        {
            SelectedList.add(officces);
        }else
            {
                SelectedList.remove(officces);
            }
    }
    public void setPosition(View v,int position)
    {
        if (((CheckBox) v).isChecked()){
            ids_list.add(officcesList.get(position).getOffice_id());
        }else {
            ids_list.remove(officcesList.get(position).getOffice_id());
        }
    }


}
