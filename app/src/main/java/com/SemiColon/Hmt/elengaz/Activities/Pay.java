package com.SemiColon.Hmt.elengaz.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Register_Client_Model;
import com.SemiColon.Hmt.elengaz.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pay extends AppCompatActivity {

    EditText name,cost;
    TextView date;
    ImageView trans_photo;
    Button upload,trans,add_date;
    String pname,pdate,pcost, picturePath,service_id,state;
    private Bitmap bitmap;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        Intent intent=getIntent();

      service_id=  intent.getStringExtra("client_service_id");
      state=intent.getStringExtra("state");
      //  Toast.makeText(this, ""+service_id, Toast.LENGTH_SHORT).show();
        trans =findViewById(R.id.trans);
        name=findViewById(R.id.edt_trans_name);
        date=findViewById(R.id.edt_date);
        add_date = findViewById(R.id.add_date);
        cost=findViewById(R.id.edt_cost);
        trans_photo = findViewById(R.id.trans_photo);
        upload=findViewById(R.id.btn_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1.createChooser(intent1,getString(R.string.choose_photo)),RESULT_LOAD_IMAGE);
                         }
        });

        trans.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (validate() == false) {
                    Toast.makeText(getBaseContext(), "transfer failed", Toast.LENGTH_LONG).show();
                    return;
                }else {

                    pname = name.getText().toString();
                    pdate = date.getText().toString();
                    pcost = cost.getText().toString();


                    ServicesApi servicesApi = APIClient.getClient().create(ServicesApi.class);
                    Call<Register_Client_Model> call = servicesApi.sendPayment(service_id, pname, pcost, pdate, picturePath);
                    call.enqueue(new Callback<Register_Client_Model>() {
                        @Override
                        public void onResponse(Call<Register_Client_Model> call, Response<Register_Client_Model> response) {
                            if (response.isSuccessful()) {
                                show_dialog();
                               // Toast.makeText(Pay.this, "" + service_id, Toast.LENGTH_SHORT).show();

                            } else {
                            //    Toast.makeText(Pay.this, "rrr", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register_Client_Model> call, Throwable t) {

                        }
                    });

                }


            }
        });

        add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog();
            }
        });


    }
    public void DateDialog() {

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Pay.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("ResourceAsColor")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String sDate = dateFormatter.format(newDate.getTime());

                date.setText(sDate+"");
                Date dateSpecified = newDate.getTime();
                Date c = Calendar.getInstance().getTime();
                if (dateSpecified.before(c)) {
                    final AlertDialog.Builder alertadd = new AlertDialog.Builder(Pay.this);
                    LayoutInflater factory = LayoutInflater.from(Pay.this);
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
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null) {
            Uri selectedImage = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                trans_photo.setImageBitmap(bitmap);
                picturePath = encodeImage(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //  img=findViewById(R.id.img);

            //  img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            // Toast.makeText(this, ""+picturePath, Toast.LENGTH_SHORT).show();

        }
    }

    private String encodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);

        byte [] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }
    private void show_dialog()
    {



      AlertDialog.Builder builder = new AlertDialog.Builder(Pay.this, AlertDialog.THEME_HOLO_LIGHT);


      String titleText = "تهانينا";

      // Initialize a new foreground color span instance
      ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);

      // Initialize a new spannable string builder instance
      SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

      // Apply the text color span
      ssBuilder.setSpan(
              foregroundColorSpan,
              0,
              titleText.length(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
      );

      // Set the alert dialog title using spannable string builder
      builder.setTitle(ssBuilder);

      // Show a message on alert dialog
      builder.setMessage("لقد تم التحويل بنجاح نشكرك على ثقتك فى خدمات");
      // Set the positive button
      builder.setPositiveButton("تم",new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {

              Intent i=new Intent(Pay.this,OrderState.class);
              i.putExtra("client_service_id",service_id);
              i.putExtra("state",state);

              startActivity(i);
          }
      });


      // Create the alert dialog
      AlertDialog dialog = builder.create();

      // Finally, display the alert dialog
      dialog.show();
  }
    public boolean validate()
    {
        boolean valid = true;
        String cname = name.getText().toString();
        String cdate = date.getText().toString();
        String ccost = cost.getText().toString();



        if (cname.isEmpty() || cname.length()<4) {
            name.setError(getString(R.string.trans_name));
            valid = false;
        } else {
            name.setError(null);
        }


        if (cdate.isEmpty() ) {
            date.setError(getString(R.string.enter_date));
            valid = false;
        } else {
            date.setError(null);
        }

        if (ccost.isEmpty() || ccost.length() < 2) {
            cost.setError(getString(R.string.enter_cost));

            valid = false;
        } else {
            cost.setError(null);
        }

        return valid;
    }
    @Override
    public void onBackPressed()
    {
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
