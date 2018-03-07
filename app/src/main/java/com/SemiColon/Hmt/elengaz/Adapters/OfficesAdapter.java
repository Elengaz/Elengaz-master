package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.R;
import com.SemiColon.Hmt.elengaz.Activities.OfficeWork;

import java.util.List;


public class OfficesAdapter extends RecyclerView.Adapter<OfficesAdapter.Holder> {
    Context context;
    Officces mmodel;
    //public  static ArrayList<String> ids;
    List<Officces> Array;

    OfficeWork officeWork;
    public OfficesAdapter(Context context, List<Officces> Array) {
        this.context = context;
        this.Array = Array;
        this.officeWork= (OfficeWork) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_office, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = Array.get(position);

     holder.check.setTag(position);

        holder.check.setText(mmodel.getOffice_title());



    }

    @Override
    public int getItemCount() {
        return Array.size();
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

           mmodel = Array.get(position);
       officeWork.setPosition(check,position);

           /* ids=new ArrayList<>();
            if (check.isChecked()){
                ids.add(Array.get(position).getOffice_id());


            }else {
                ids.remove(Array.get(position).getOffice_id());
            }
            for (int i=0;i<Array.size();i++){

                Toast.makeText(context, ""+ids, Toast.LENGTH_SHORT).show();

            }*/
        }


    }


}
