package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.Fragments.Fragment_Search_Offices_Result;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;


public class OfficesAdapter_SearchResult extends RecyclerView.Adapter<OfficesAdapter_SearchResult.Holder> {
    Context context;
    Fragment_Search_Offices_Result fsor;
    List<Officces> officcesList;
    public OfficesAdapter_SearchResult(Context context, List<Officces> officcesList, Fragment_Search_Offices_Result fsor) {
        this.context = context;
        this.officcesList = officcesList;
        this.fsor=fsor;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_office, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Officces  officces = officcesList.get(position);
        holder.BindData(officces);



    }

    @Override
    public int getItemCount() {
        return officcesList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox check;

        public Holder(View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.chkid);

            check.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            fsor.setPos(check,position);
        }

        public void BindData(Officces officces)
        {
            check.setText(officces.getOffice_title().toString());
        }


    }


}
