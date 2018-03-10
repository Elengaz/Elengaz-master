package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SemiColon.Hmt.elengaz.Activities.TransAccount;
import com.SemiColon.Hmt.elengaz.Model.Bank_Account_Model;

import com.SemiColon.Hmt.elengaz.Activities.Pay;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

public class Bank_Account_Adapter extends RecyclerView.Adapter<Bank_Account_Adapter.Holder> {
    Context context;
    Bank_Account_Model mmodel;
    List<Bank_Account_Model> bankAccount;
    TransAccount transAccount;

    public Bank_Account_Adapter(Context context, List<Bank_Account_Model> bankAccount ) {
        this.context = context;
        this.bankAccount = bankAccount;
        this.transAccount= (TransAccount) context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transmit, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = bankAccount.get(position);
        holder.pay.setTag(position);
        holder.txt_account_name.setText(mmodel.getAccount_name());
        holder.txt_account_bank_name.setText(mmodel.getAccount_bank_name());
        holder.txt_account_number.setText(mmodel.getAccount_number());
        holder.txt_account_IBAN.setText(mmodel.getAccount_IBAN());

    }

    @Override
    public int getItemCount() {
        return bankAccount.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_account_name,txt_account_bank_name,txt_account_number,txt_account_IBAN;

LinearLayout pay;
         Holder(View itemView) {
            super(itemView);

            txt_account_name = itemView.findViewById(R.id.txt_account_name);
            txt_account_bank_name=itemView.findViewById(R.id.txt_account_bank_name);
            txt_account_number = itemView.findViewById(R.id.txt_account_number);
            txt_account_IBAN=itemView.findViewById(R.id.txt_account_IBAN);
             pay=itemView.findViewById(R.id.pay);

             pay.setOnClickListener(this);

         }

        @Override
        public void onClick(View view) {

            int position =getAdapterPosition();
            mmodel = bankAccount.get(position);
            Intent i = new Intent(context,Pay.class);
            i.putExtra("client_service_id", transAccount.service_id);
            i.putExtra("state",transAccount.state);
            context.startActivity(i);


        }


    }





}
