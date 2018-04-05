package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.Model.DisplayServicesModel;
import com.SemiColon.Hmt.elengaz.R;

public class ServiceDetailsActivity extends AppCompatActivity {

    private ImageView back;
    private TextView service_state,service_name,service_details,service_sd,service_ed,service_cost,office_name;
    private Button choose_officeBtn;
    private DisplayServicesModel servicesModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        initView();
        getDataFromIntent();
    }
    private void initView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        service_state = findViewById(R.id.service_state);
        service_name = findViewById(R.id.service_name);
        service_details = findViewById(R.id.service_details);
        service_sd = findViewById(R.id.service_sd);
        service_ed = findViewById(R.id.service_ed);
        service_cost = findViewById(R.id.service_cost);
        office_name = findViewById(R.id.service_office_name);

        choose_officeBtn = findViewById(R.id.choose_officeBtn);
        choose_officeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckService();
            }
        });

    }

    private void CheckService() {

        // service no replay
        if (servicesModel.getMy_order_state()==0)
        {
            Toast.makeText(this, "لم يتم الرد", Toast.LENGTH_SHORT).show();
        }else
            {
                if (servicesModel.getClient_service_status().equals("0"))
                {
                     Intent intent = new Intent(ServiceDetailsActivity.this, Offers.class);
                     intent.putExtra("client_service_id", servicesModel.getClient_service_id());
                     intent.putExtra("state",servicesModel.getState_name());
                     startActivity(intent);
                }else if (servicesModel.getClient_service_status().equals("1"))
                {

                    if (servicesModel.getService_closed().equals("1"))
                    {
                        Toast.makeText(this, "الخدمة جارية", Toast.LENGTH_SHORT).show();

                    }else
                        {
                            Toast.makeText(this, "لم يتم التحويل سعر الخدمه", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ServiceDetailsActivity.this, Offers.class);
                            intent.putExtra("client_service_id", servicesModel.getClient_service_id());
                            intent.putExtra("state",servicesModel.getState_name());
                            startActivity(intent);
                        }
                }else if (servicesModel.getClient_service_status().equals("2"))
                {
                    if (servicesModel.getClient_evaluation().equals("0"))
                    {
                        Toast.makeText(ServiceDetailsActivity.this, "لم يتم التقييم", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ServiceDetailsActivity.this, AddRate.class);
                        intent.putExtra("client_service_id", servicesModel.getClient_service_id());
                        startActivity(intent);
                    }else
                        {
                            Toast.makeText(this, "تم إنجاز الخدمة", Toast.LENGTH_SHORT).show();
                        }
                }

            }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            servicesModel = (DisplayServicesModel) intent.getSerializableExtra("service_details");
            updateUI(servicesModel);
        }
    }

    private void updateUI(DisplayServicesModel servicesModel)
    {
        if (servicesModel.getMy_order_state()==0)
        {
            service_state.setText("لم يتم الرد");

        }else {
            if (servicesModel.getClient_service_status().equals("0")) {
                service_state.setText("الخدمة لم تبدأ بعد");


            } else if (servicesModel.getClient_service_status().equals("1")) {
                service_state.setText("الخدمة جاريه");


            } else if (servicesModel.getClient_service_status().equals("2")) {
                service_state.setText("تم انهاء الخدمه");
                service_state.setVisibility(View.GONE);


            }


        }

        service_name.setText(servicesModel.getClient_service_name());
        service_sd.setText(servicesModel.getClient_service_date());
        service_ed.setText(servicesModel.getClient_service_end_date());
        service_details.setText(servicesModel.getClient_service_details());
        service_cost.setText(servicesModel.getClient_service_cost());
        office_name.setText(servicesModel.getOffice_name());
    }


}
