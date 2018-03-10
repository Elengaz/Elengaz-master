package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.Activities.AddRate;
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
        if (!mmodel.getClient_service_cost().equals("0")){
            holder.lingrand.setBackgroundColor(ContextCompat.getColor(context,R.color.white));

        }else {
            holder.lingrand.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));

        }
        holder.cost        .setText(mmodel.getClient_service_cost());
        holder.office_name .setText(mmodel.getOffice_name());
        holder.state       .setText(mmodel.getState_name());
        holder.rate        .setText(mmodel.getClient_evaluation_state());
        if (mmodel.getMy_order_state().equals("1")) {
            holder.order_state.setText("تم الرد ");

        }else {
            holder.order_state.setText("لم يتم الرد ");

        }
    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView service_name,start_date,end_date,cost,office_name, state, rate,order_state;
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
            order_state  =itemView.findViewById(R.id.txt_order_state);


            lingrand = itemView.findViewById(R.id.linear);
            lingrand.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();

            mmodel = Array.get(position);
            if (mmodel.getMy_order_state().equals("1") && mmodel.getState_name().equals("جارية")) {

                Intent intent = new Intent(context, Offers.class);
                intent.putExtra("client_service_id", mmodel.getClient_service_id());
                intent.putExtra("state",mmodel.getState_name());
                context.startActivity(intent);

                // Toast.makeText(context, mmodel.getOrderId() + cost, Toast.LENGTH_SHORT).show();
            }else if (mmodel.getMy_order_state().equals("1") && mmodel.getState_name().equals("لم تبدأ بعد"))
            {
                Intent intent = new Intent(context, Offers.class);
                intent.putExtra("client_service_id", mmodel.getClient_service_id());
                intent.putExtra("state",mmodel.getState_name());
                context.startActivity(intent);
            }

            else if (mmodel.getMy_order_state().equals("1") && mmodel.getState_name().equals("تمت الخدمة"))
            {

                Intent intent = new Intent(context, AddRate.class);
                intent.putExtra("client_service_id", mmodel.getClient_service_id());
                context.startActivity(intent);
            }else
                {
                    Toast.makeText(context, "لم يتم الرد", Toast.LENGTH_SHORT).show();

                }

        }



    }
}