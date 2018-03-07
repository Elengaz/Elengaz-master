package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.Activities.Offers;
import com.SemiColon.Hmt.elengaz.Model.DisplayServicesModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;


public class Client_Oreders_Adapter extends RecyclerView.Adapter<Client_Oreders_Adapter.Holder> {
    Context context;
    DisplayServicesModel mmodel;
    List<DisplayServicesModel> Array;

    String cost;
    public Client_Oreders_Adapter(Context context, List<DisplayServicesModel> Array) {
        this.context = context;
        this.Array = Array;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_client_response, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = Array.get(position);

        holder.lingrand.setTag(position);

        holder.service_name.setText(mmodel.getClient_service_name());
        holder.start_date  .setText(mmodel.getClient_service_date());
        holder.end_date    .setText(mmodel.getClient_service_end_date());
        holder.cost        .setText(mmodel.getClient_service_cost());
        holder.office_name .setText(mmodel.getOffice_name());
        holder.state       .setText(mmodel.getState_name());
        holder.rate        .setText(mmodel.getClient_evaluation_state());


    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView service_name,start_date,end_date,cost,office_name, state, rate;
        LinearLayout lingrand;

        public Holder(View itemView) {
            super(itemView);

            service_name = itemView.findViewById(R.id.txt_client_service_name);
            start_date   = itemView.findViewById(R.id.txt_client_service_date);
            end_date     = itemView.findViewById(R.id.txt_client_service_end_date);
            cost         = itemView.findViewById(R.id.txt_client_service_cost);
            office_name  = itemView.findViewById(R.id.txt_office_name);
            state        = itemView.findViewById(R.id.txt_state_name);
            rate         = itemView.findViewById(R.id.txt_client_evaluation_state);

            lingrand = itemView.findViewById(R.id.linear);
            lingrand.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();

            mmodel = Array.get(position);

            Intent intent=new Intent(context, Offers.class);
            intent.putExtra("client_service_id",mmodel.getClient_service_id());
            context.startActivity(intent);

           // Toast.makeText(context, mmodel.getOrderId() + cost, Toast.LENGTH_SHORT).show();


        }



    }
}
