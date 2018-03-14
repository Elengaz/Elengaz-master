package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.Model.DisplayServicesModel;
import com.SemiColon.Hmt.elengaz.R;
import com.SemiColon.Hmt.elengaz.ServiceDetailsActivity;

import java.util.List;


public class Client_Oreders_Adapter extends RecyclerView.Adapter<Client_Oreders_Adapter.Holder> {
    Context context;
    DisplayServicesModel mmodel;
    List<DisplayServicesModel> Array;
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
    public void onBindViewHolder(final Holder holder, int position) {
        mmodel = Array.get(position);

        holder.service_name.setText(mmodel.getClient_service_name());
        holder.start_date  .setText(mmodel.getClient_service_date());

        if (mmodel.getMy_order_state()==0)
        {
            holder.order_state.setText("لم يتم الرد");
            holder.not_office_counts.setVisibility(View.GONE);

        }else
            {
                if (mmodel.getClient_service_status().equals("0"))
                {
                    holder.order_state.setText("الخدمة لم تبدأ بعد");
                    holder.not_office_counts.setText(mmodel.getTotal_send());
                    holder.not_office_counts.setVisibility(View.VISIBLE);


                }else if (mmodel.getClient_service_status().equals("1"))
                {
                    holder.order_state.setText("الخدمة جارية");

                }else if (mmodel.getClient_service_status().equals("2"))
                {
                    holder.order_state.setText("تم إنجاز الخدمة");

                }
            }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                DisplayServicesModel servicesModel = Array.get(position);

                Intent intent =new Intent(context, ServiceDetailsActivity.class);
                intent.putExtra("service_details",servicesModel);
                context.startActivity(intent);

                /*if (mmodel.getMy_order_state()==0)
                {
                    holder.order_state.setText("لم يتم الرد");
                    Toast.makeText(context, "لم يتم الرد", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(context, ServiceDetailsActivity.class);
                    intent.putExtra("service_details",mmodel);
                    context.startActivity(intent);

                }else
                {
                    if (mmodel.getClient_service_status().equals("0"))
                    {
                        holder.order_state.setText("الخدمة لم تبدأ بعد");
                        Toast.makeText(context, "اختار مكتب مناسب", Toast.LENGTH_SHORT).show();

                        Intent intent =new Intent(context, ServiceDetailsActivity.class);
                        intent.putExtra("service_details",mmodel);
                        context.startActivity(intent);


                    }else if (mmodel.getClient_service_status().equals("1"))
                    {
                        holder.order_state.setText("الخدمة جاريه");

                        if (mmodel.getTransfer_status().equals("0"))
                        {
                            Toast.makeText(context, "اكمل الخدمه", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(context, ServiceDetailsActivity.class);
                            intent.putExtra("service_details",mmodel);
                            context.startActivity(intent);
                        }else if (mmodel.getTransfer_status().equals("1"))
                        {
                            if (mmodel.getClient_service_status().equals("2"))
                            {
                                if (mmodel.getClient_evaluation().equals("0"))
                                {
                                Toast.makeText(context, "لم يتم التقييم", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, AddRate.class);
                                intent.putExtra("client_service_id", mmodel.getClient_service_id());
                                context.startActivity(intent);
                                }else
                                {
                                    Toast.makeText(context, "تم انهاء الخدمه", Toast.LENGTH_SHORT).show();

                                }

                            }else if (mmodel.getClient_service_status().equals("1"))
                            {
                                Toast.makeText(context, "الخدمة جارية", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(context, ServiceDetailsActivity.class);
                                intent.putExtra("service_details",mmodel);
                                context.startActivity(intent);
                            }


                        }

                    }else if (mmodel.getClient_service_status().equals("2"))
                    {
                        holder.order_state.setText("تم انهاء الخدمه");

                    }
                }
*/


           /* if (mmodel.getMy_order_state().equals("1") && mmodel.getState_name().equals("جارية")) {

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

                }*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView service_name,start_date,order_state,not_office_counts;

        public Holder(View itemView) {
            super(itemView);

            service_name = itemView.findViewById(R.id.service_name);
            start_date   = itemView.findViewById(R.id.service_startDate);
            order_state  =itemView.findViewById(R.id.service_state);
            not_office_counts  =itemView.findViewById(R.id.not_office_counts);




        }

        @Override
        public void onClick(View view) {


        }



    }
}