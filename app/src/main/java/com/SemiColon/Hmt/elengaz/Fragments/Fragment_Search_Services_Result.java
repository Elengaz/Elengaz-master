package com.SemiColon.Hmt.elengaz.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.Model.Services;
import com.SemiColon.Hmt.elengaz.Activities.OfficeWork;
import com.SemiColon.Hmt.elengaz.Adapters.ServicesAdapter_SearchResult;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;


public class Fragment_Search_Services_Result extends Fragment {

    private RecyclerView recView_searchServices;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<Services> servicesList;
    private String clientId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search_services_result, container, false);
        initView(view);
        getDataFromSearchResult_Bundle();

        return view;
    }

    private void getDataFromSearchResult_Bundle() {
        Bundle bundle = getArguments();

        if (bundle!=null)
        {
            servicesList = (List<Services>) bundle.getSerializable("servicesList");
            clientId = bundle.getString("clientId");

            Log.e("data",servicesList.get(0).getService_title()+"   id"+clientId);
            adapter = new ServicesAdapter_SearchResult(getActivity(),servicesList,this);
            adapter.notifyDataSetChanged();
            recView_searchServices.setAdapter(adapter);
        }
    }

    private void initView(View view) {
        recView_searchServices = view.findViewById(R.id.recView_searchServices);
        manager = new LinearLayoutManager(getActivity());
        recView_searchServices.setLayoutManager(manager);
        recView_searchServices.setHasFixedSize(true);

    }


    public void setPos(int pos)
    {
        Intent intent = new Intent(getActivity(), OfficeWork.class);
        intent.putExtra("service_id",servicesList.get(pos).getClient_service_id());
        intent.putExtra("client_id",clientId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
        Toast.makeText(getActivity(), "fragment_search_servicecs  "+servicesList.get(pos).getService_title()+"  clientId"+clientId, Toast.LENGTH_SHORT).show();
    }


}
