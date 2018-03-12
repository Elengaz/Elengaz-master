package com.SemiColon.Hmt.elengaz.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Office_Service_Model;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Office_Services_Adapter extends RecyclerView.Adapter<Office_Services_Adapter.Holder> {
    Context context;
    Office_Service_Model mmodel;
    List<Office_Service_Model> Array;

    String cost;
    public Office_Services_Adapter(Context context, List<Office_Service_Model> Array) {
        this.context = context;
        this.Array = Array;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_officer_service, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        mmodel = Array.get(position);

        if (mmodel.getClient_service_status().equals("2"))
        {
            holder.txt.setText("تمت الخدمة");
        }else
            {
                holder.txt.setText("إنهاء الخدمة");
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.card));
            }

        //holder.state.setTag(position);


        holder.servicename.setText(mmodel.getClient_service_name());
        holder.detail.setText(mmodel.getClient_service_details());
        holder.phone.setText(mmodel.getClient_phone_number());
        holder.cost.setText(mmodel.getClient_service_cost());
        //holder.state.setText(mmodel.getClient_service_status());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Office_Service_Model office_service_model = Array.get(holder.getAdapterPosition());

                if (office_service_model.getClient_service_status().equals("2"))
                {
                    Toast.makeText(context, "تمت الخدمة", Toast.LENGTH_SHORT).show();
                }else
                    {
                        CreateDialog(holder);

                    }


            }
        });


    }

    private void CreateDialog(final Holder holder) {
        Log.e("holder",""+holder.getAdapterPosition());

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setCancelable(true);
        dialog.setMessage(context.getString(R.string.end_serv));
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog dialog1 = dialog.create();
                dialog1.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Office_Service_Model office_service_model = Array.get(holder.getAdapterPosition());
                ServicesApi service= APIClient.getClient().create(ServicesApi.class);
                Call<Office_Service_Model> call=service.EndService(office_service_model.getClient_service_id());
                call.enqueue(new Callback<Office_Service_Model>() {
                    @Override
                    public void onResponse(Call<Office_Service_Model> call, Response<Office_Service_Model> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, " service ended", Toast.LENGTH_SHORT).show();
                            AlertDialog dialog1 = dialog.create();
                            dialog1.dismiss();
                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<Office_Service_Model> call, Throwable t) {

                    }
                });

            }
        });
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView  servicename, detail, phone,cost,txt;
        CardView card;

        public Holder(View itemView) {
            super(itemView);

            servicename = itemView.findViewById(R.id.txt_service_name);
            detail = itemView.findViewById(R.id.txt_detail);
            phone = itemView.findViewById(R.id.txt_phone);
            cost=itemView.findViewById(R.id.txt_cost);
            txt=itemView.findViewById(R.id.txt);
            card = itemView.findViewById(R.id.card);


        }



    }
}
