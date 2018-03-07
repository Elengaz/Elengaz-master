package com.SemiColon.Hmt.elengaz.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.office_order_model;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Office_Oreders_Adapter extends RecyclerView.Adapter<Office_Oreders_Adapter.Holder> {
    Context context;
    office_order_model mmodel;
    List<office_order_model> Array;

    String cost;
    public Office_Oreders_Adapter(Context context, List<office_order_model> Array) {
        this.context = context;
        this.Array = Array;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_officer_order, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = Array.get(position);

        holder.lingrand.setTag(position);

        holder.username.setText(mmodel.getClientUserName());
        holder.servicename.setText(mmodel.getClientServiceName());
        holder.detail.setText(mmodel.getClientServiceDetails());
        holder.phone.setText(mmodel.getClientPhoneNumber());


    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username, servicename, detail, phone;
        LinearLayout lingrand;

        public Holder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.client_name);
            servicename = itemView.findViewById(R.id.txt_service_name);
            detail = itemView.findViewById(R.id.txt_detail);
            phone = itemView.findViewById(R.id.txt_phone);
            lingrand = itemView.findViewById(R.id.linear);
            lingrand.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();

            mmodel = Array.get(position);


            add_cost();
           // Toast.makeText(context, mmodel.getOrderId() + cost, Toast.LENGTH_SHORT).show();


        }


        private void add_cost() {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("ادخل السعر");

// Set up the input
            final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cost = input.getText().toString();
                    ServicesApi service = APIClient.getClient().create(ServicesApi.class);


                    Call<office_order_model> userCall = service.AddOfficeOfferCost(cost,mmodel.getClientIdFk(),mmodel.getOrderId());
                    // startActivity(new Intent(Register.this, ListMarma.class));

                    userCall.enqueue(new Callback<office_order_model>() {
                        @Override
                        public void onResponse(Call<office_order_model> call, Response<office_order_model> response) {


                            if (response.isSuccessful()) {

                              //  Toast.makeText(context, ""+mmodel.getOrderId()+"  "+mmodel.getClientIdFk(), Toast.LENGTH_SHORT).show();
                            //    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            } else {
                             Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<office_order_model> call, Throwable t) {
                            Log.d("onFailure", t.toString());
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

    }
}
