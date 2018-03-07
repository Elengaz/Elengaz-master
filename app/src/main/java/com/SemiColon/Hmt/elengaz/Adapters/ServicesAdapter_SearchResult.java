package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.Model.Services;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Search_Services_Result;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;


public class ServicesAdapter_SearchResult extends RecyclerView.Adapter<ServicesAdapter_SearchResult.Holder> {
    Context context;
    List<Services> servicesList;
    Fragment_Search_Services_Result fragment;


    public ServicesAdapter_SearchResult(Context context, List<Services> servicesList , Fragment_Search_Services_Result fragment) {
        this.context = context;
        this.servicesList = servicesList;
        this.fragment = fragment;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.services_list_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
       Services  services =servicesList.get(position);
        holder.BindData(services.getService_title());
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

         Holder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.txtlist);
            text.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            fragment.setPos(pos);

        }

        public void BindData(String service)
        {
            text.setText(service);
        }


    }





}
