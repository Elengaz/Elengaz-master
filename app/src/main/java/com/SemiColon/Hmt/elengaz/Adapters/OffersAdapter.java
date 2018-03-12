package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.Activities.Offers;
import com.SemiColon.Hmt.elengaz.Activities.OfficeWork;
import com.SemiColon.Hmt.elengaz.Model.OfficeOfferModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;


public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.Holder> {
    Context context;
    List<OfficeOfferModel> officeOfferModelList;
    private int lastSelectedPos= -1;
    private Offers offers;
   public static String offer_cost;

    OfficeWork officeWork;
    public OffersAdapter(Context context, List<OfficeOfferModel> officeOfferModelList) {
        this.context = context;
        this.offers = (Offers) context;
        this.officeOfferModelList = officeOfferModelList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        OfficeOfferModel officeOfferModel = officeOfferModelList.get(position);

        holder.BindData(officeOfferModel);
        holder.title.setChecked(lastSelectedPos==position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedPos = holder.getAdapterPosition();
               // Toast.makeText(context, "pos"+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                offers.setPos(holder.getAdapterPosition());


                notifyDataSetChanged();


            }
        });

    }

    @Override
    public int getItemCount() {
        return officeOfferModelList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cost;
        RadioButton title;
        public Holder(View itemView) {
            super(itemView);
            cost = itemView.findViewById(R.id.cost);
           title = itemView.findViewById(R.id.rb);

            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            lastSelectedPos = getAdapterPosition();
        //    Toast.makeText(context, "pos"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
            offers.setPos(getAdapterPosition());




            notifyDataSetChanged();
        }

        public void BindData(OfficeOfferModel officeOfferModel)
        {
            cost.setText(officeOfferModel.getOffice_service_cost()+" "+"ريال");
            offer_cost=cost.getText().toString();
            title.setText(officeOfferModel.getOffice_title());
            Log.e("sssssssss","ssssssssssss");
        }


    }


}
