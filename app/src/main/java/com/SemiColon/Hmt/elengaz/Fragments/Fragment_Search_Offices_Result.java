package com.SemiColon.Hmt.elengaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.Adapters.OfficesAdapter_SearchResult;
import com.SemiColon.Hmt.elengaz.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Search_Offices_Result extends Fragment {

    private RecyclerView recView_searchOffice;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<Officces> officcesList;
    private List<Officces> SelectedList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search_offices_result, container, false);
        initView(view);
        getDataFromSearch_Bundle();
        return view;
    }



    private void initView(View view) {

        SelectedList = new ArrayList<>();

        recView_searchOffice = view.findViewById(R.id.recView_searchOffice);
        manager = new LinearLayoutManager(getContext());
        recView_searchOffice.setLayoutManager(manager);
        recView_searchOffice.setHasFixedSize(true);


    }

    private void getDataFromSearch_Bundle()
    {
        Bundle bundle = getArguments();

        if (bundle!=null)
        {
            officcesList = (List<Officces>) bundle.getSerializable("search");
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


}
