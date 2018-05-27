package com.SemiColon.Hmt.elengaz.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.AddServicesResponse;
import com.SemiColon.Hmt.elengaz.Model.Client_Model;
import com.SemiColon.Hmt.elengaz.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddService extends AppCompatActivity {
    private ProgressDialog pDialog;
    private AlertDialog alertDialog;
    Button btnDatePicker, btnplace,add;
    EditText serviseName,detail,phone,otherPhone,email;
    String sDate,client_id,formattedDate;
    Double sLatitude,sLongitude;
    String service_id ,category_id;
    private ProgressDialog dialog;
    private List<String> offices_ids_list;
    ImageView back;
    String sName,sDetail,sPhone,sOtherPhone,sEmail;
    double lat,lng;
    public Client_Model client_model;
    private final int PLACE_REQ =1200;
    Date c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        alertDialog = new AlertDialog.Builder(this)
        .setTitle(getString(R.string.cong))
        .setMessage(R.string.sas)
        .setCancelable(true)
        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AddService.this,Main_Home.class);
                intent.putExtra("id",client_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                alertDialog.dismiss();
                startActivity(intent);

                finish();
            }
        }).create();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddService.this,OfficeWork.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        serviseName=findViewById(R.id.edt_service_name);
        detail=findViewById(R.id.edt_service_detail);
        phone=findViewById(R.id.edt_service_phone);
        otherPhone=findViewById(R.id.edt_service_other_phone);
        email=findViewById(R.id.edt_service_email);

        btnDatePicker=findViewById(R.id.btndate);
        btnplace=findViewById(R.id.btnplace);
        add= findViewById(R.id.btn_add_service);
        c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);

        getDataFromIntent();




        if (client_model!=null)
        {
            if (client_model.getClient_email()!=null|| TextUtils.isEmpty(client_model.getClient_email()))
            {
                email.setText(client_model.getClient_email());
            }

            if (client_model.getClient_phone()!=null|| TextUtils.isEmpty(client_model.getClient_phone()))
            {
                phone.setText(client_model.getClient_phone());
            }
        }



        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
               DateDialog();
            }

        });





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(AddService.this,Notification1.class);
//                startActivity(i);

                String sserviseName = serviseName.getText().toString();
                String sdetail = detail.getText().toString();
                String mobile = phone.getText().toString();
                String mobile2 = otherPhone.getText().toString();
                String uemail = email.getText().toString();

                if (TextUtils.isEmpty(sserviseName))
                {
                    serviseName.setError(getString(R.string.esn));
                }else if (TextUtils.isEmpty(sdetail))
                {
                    serviseName.setError(null);
                    detail.setError(getString(R.string.esd));
                }else if (TextUtils.isEmpty(mobile))
                {
                    detail.setError(null);
                    phone.setError(getString(R.string.ep));
                }else if (!Patterns.PHONE.matcher(mobile).matches())
                {
                    detail.setError(null);
                    phone.setError(getString(R.string.inv_ph));

                }
                else if (TextUtils.isEmpty(mobile2))
                {
                    phone.setError(null);
                    otherPhone.setError(getString(R.string.eap));
                }else if (!Patterns.PHONE.matcher(mobile2).matches())
                {
                    phone.setError(null);
                    otherPhone.setError(getString(R.string.inv_ph));

                }else if (TextUtils.isEmpty(uemail))
                {
                    otherPhone.setError(null);
                    email.setError(getString(R.string.ee));

                }else if (!Patterns.EMAIL_ADDRESS.matcher(uemail).matches())
                {
                    otherPhone.setError(null);
                    email.setError(getString(R.string.inv_e));

                }else if (TextUtils.isEmpty(sDate))
                {
                    Toast.makeText(AddService.this,R.string.ed, Toast.LENGTH_LONG).show();
                }else if (lat==0.0 && lng==0.0)
                {
                    Toast.makeText(AddService.this, R.string.sl, Toast.LENGTH_LONG).show();

                }else
                    {
                        sendService();

                    }





            }
        });

       btnplace.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(AddService.this,AddPlace.class);
            startActivityForResult(i,PLACE_REQ);
        }
    });}

    private void getDataFromIntent() {
        Intent intent=getIntent();
        if (intent!=null)
        {
            offices_ids_list = (List<String>) intent.getSerializableExtra("offices_ids_list");
            category_id= intent.getStringExtra("category_id");
            client_id = intent.getStringExtra("client_id");
            client_model = (Client_Model) intent.getSerializableExtra("client_data");
        }

        //sLatitude= intent.getDoubleExtra("latitude",1.1);
        //sLongitude= intent.getDoubleExtra("longitude",1.1);

    }

    private void sendService() {
        pDialog = new ProgressDialog(AddService.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage(getString(com.SemiColon.Hmt.elengaz.R.string.adding_serv));
        pDialog.setCancelable(false);
        showpDialog();

        sName = serviseName.getText().toString();
        sDetail = detail.getText().toString();
        sPhone = phone.getText().toString();
        sOtherPhone = otherPhone.getText().toString();
        sEmail = email.getText().toString();
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);
        Call<AddServicesResponse> call = service.AddAllServiceData(offices_ids_list, client_id, category_id, sName, sDetail, sDate, sPhone, sOtherPhone, sEmail, String.valueOf(lat), String.valueOf(lng));

        call.enqueue(new Callback<AddServicesResponse>() {
            @Override
            public void onResponse(Call<AddServicesResponse> call, Response<AddServicesResponse> response) {

                if (response.isSuccessful())
                {
                    AddServicesResponse servicesResponse = response.body();

                    if (servicesResponse.getSuccess()==1)
                    {
                        alertDialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddServicesResponse> call, Throwable t) {
                Log.e("error",t.getMessage());
                Toast.makeText(AddService.this, getString(R.string.something_went_haywire), Toast.LENGTH_SHORT).show();
            }
        });

        Log.e("All Data",client_id+"\n"+category_id+"\n"+offices_ids_list.get(0)+"\n"+lat+"\n"+lng+"\n"+sName+"\n"+sDetail+"\n"+sPhone+"\n"+sOtherPhone+"\n"+sEmail+"\n"+sDate);
 /*


         sName = serviseName.getText().toString();
         sDetail = detail.getText().toString();
         sPhone = phone.getText().toString();
         sOtherPhone = otherPhone.getText().toString();
         sEmail = email.getText().toString();


        ServicesApi service = APIClient.getClient().create(ServicesApi.class);


        Call<Register_Client_Model> userCall = service.AddOneService(sName,sDetail,sPhone,sOtherPhone,sEmail,sLatitude.toString(),sLongitude.toString(),sDate,service_id);
        // startActivity(new Intent(Register.this, ListMarma.class));

        userCall.enqueue(new Callback<Register_Client_Model>() {
            @Override
            public void onResponse(Call<Register_Client_Model> call, Response<Register_Client_Model> response) {
                hidepDialog();
                //onSignupSuccess();
//                Log.d("onResponse", "" + response.body().getMessage());


                if (response.isSuccessful()) {
                  //  if (response.body().getSuccess()==1){
                    Intent intent1=new Intent(AddService.this, Main_Home.class);
                    intent1.putExtra("service_id",service_id);
                     startActivity(intent1);

                     //   Toast.makeText(AddService.this, ""+sDate, Toast.LENGTH_LONG).show();

                     //   Log.e("mmm",sName+sDetail+sPhone+sOtherPhone+sEmail+sLatitude.toString()+sLongitude.toString()+sDate+service_id+"");
                        finish();
                  //  }else {
                      //  Toast.makeText(AddService.this, "rrr", Toast.LENGTH_SHORT).show();
                   // }
                } else {
//                    Toast.makeText(AddService.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register_Client_Model> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });*/
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        String sserviseName = serviseName.getText().toString();
        String sdetail = detail.getText().toString();
        String mobile = phone.getText().toString();
        String mobile2 = otherPhone.getText().toString();
        String uemail = email.getText().toString();

        if (sserviseName.isEmpty() || sserviseName.length() < 3) {
            serviseName.setError("at least 3 characters");
            valid = false;
        } else {
            serviseName.setError(null);
        }

        if (sdetail.isEmpty() || sdetail.length() < 3) {
            detail.setError("at least 10 characters");
            valid = false;
        } else {
            detail.setError(null);
        }
        if (mobile.isEmpty() || mobile.length() < 8 || mobile.length() > 13) {
            phone.setError("phone Do not match");
            valid = false;
        } else {
            phone.setError(null);
        }
        if (mobile2.isEmpty() || mobile.length() < 8 || mobile2.length() > 13) {
            otherPhone.setError("phone Do not match");
            valid = false;
        } else {
            otherPhone.setError(null);
        }
        if (uemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(uemail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        return valid;
    }

    public void DateDialog() {

        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddService.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("ResourceAsColor")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                sDate = dateFormatter.format(newDate.getTime());

                Date dateSpecified = newDate.getTime();

                if (dateSpecified.before(c)) {
                    final AlertDialog.Builder alertadd = new AlertDialog.Builder(AddService.this);
                    LayoutInflater factory = LayoutInflater.from(AddService.this);
                    final View viewu = factory.inflate(R.layout.sample, null);
                    alertadd.setView(viewu);

                    alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                            DateDialog();


                        }
                    });
                    AlertDialog dialog = alertadd.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

                    dialog.show();

                    //dateall.setText("Choose date");

                } else {
                 //   dateall.setText(date);

                }

            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PLACE_REQ && resultCode == RESULT_OK && data!=null)
        {
            lat = data.getDoubleExtra("latitude",0.0);
            lng = data.getDoubleExtra("longitude",0.0);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,OfficeWork.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
