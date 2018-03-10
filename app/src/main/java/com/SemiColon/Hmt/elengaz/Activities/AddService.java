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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Client_Model;
import com.SemiColon.Hmt.elengaz.Model.Register_Client_Model;
import com.SemiColon.Hmt.elengaz.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddService extends AppCompatActivity {
    private ProgressDialog pDialog;
    Button btnDatePicker, btnplace,add;
    EditText serviseName,detail,phone,otherPhone,email;
    String sDate,client_id,formattedDate;
    Double sLatitude,sLongitude;
    String service_id ;
    String lat,lng,sName,sDetail,sPhone,sOtherPhone,sEmail;
    public Client_Model client_model;
    private final int PLACE_REQ =1200;
    Date c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

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
        Intent intent=getIntent();
        service_id = intent.getStringExtra("service_id");
        sLatitude= intent.getDoubleExtra("latitude",1.1);
        sLongitude= intent.getDoubleExtra("longitude",1.1);
        client_id= intent.getStringExtra("client_id");
        client_model = (Client_Model) intent.getSerializableExtra("client_data");


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
                if (validate() == false) {
                    return;
                }else {

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

    private void sendService() {
        pDialog = new ProgressDialog(AddService.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Waiting for adding service data...");
        pDialog.setCancelable(false);

        showpDialog();

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
        });
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
            lat = data.getStringExtra("latitude");
            lng = data.getStringExtra("longitude");
        }

    }
}
