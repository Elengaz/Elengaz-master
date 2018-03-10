package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.Model.Bank_Account_Model;
import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Adapters.Bank_Account_Adapter;
import com.SemiColon.Hmt.elengaz.R;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransAccount extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Bank_Account_Model> Model;
    Bank_Account_Adapter adapter;
   public String service_id,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_account);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        Intent intent=getIntent();
        service_id=  intent.getStringExtra("client_service_id");
        state=intent.getStringExtra("state");
        recyclerView = findViewById(R.id.recyc_transmit);

        Model=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(TransAccount.this));
        recyclerView.setHasFixedSize(true);

        adapter=new Bank_Account_Adapter(TransAccount.this,Model);
        recyclerView.setAdapter(adapter);
        getData();


      /*  l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TransAccount.this,Pay.class);
                startActivity(i);
            }
        });
*/
    }

    private void getData() {


        ServicesApi servicesApi= APIClient.getClient().create(ServicesApi.class);
        Call<List<Bank_Account_Model>> call=servicesApi.getBankAccounts();

        call.enqueue(new Callback<List<Bank_Account_Model>>() {
            @Override
            public void onResponse(Call<List<Bank_Account_Model>> call, Response<List<Bank_Account_Model>> response) {
                if (response.isSuccessful()){
                    Model.clear();
                    Model.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TransAccount.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Bank_Account_Model>> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);
        String user_type = pref.getString("user_type","");

        if (!TextUtils.isEmpty(user_type))
        {
            if (user_type.equals("client"))
            {
                String id = pref.getString("id","");

                if (!TextUtils.isEmpty(id))
                {
                    Intent intent = new Intent(this,Client_Response_Orders.class);
                    intent.putExtra("client_id",id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }
    }
}
