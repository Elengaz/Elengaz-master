package com.SemiColon.Hmt.elengaz.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.Register_Client_Model;
import com.SemiColon.Hmt.elengaz.R;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pay extends AppCompatActivity {

    Button trans;
    EditText name,date,cost;
    Button upload;
    String pname,pdate,pcost, picturePath,service_id,state;
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
        cost=findViewById(R.id.edt_cost);
        upload=findViewById(R.id.btn_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            //  img=findViewById(R.id.img);

            //  img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            // Toast.makeText(this, ""+picturePath, Toast.LENGTH_SHORT).show();

        }
    }

  private void show_dialog(){



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
    public boolean validate() {
        boolean valid = true;
        String cname = name.getText().toString();
        String cdate = date.getText().toString();
        String ccost = cost.getText().toString();



        if (cname.isEmpty() || cname.length()<4) {
            name.setError("enter a valid email address");
            valid = false;
        } else {
            name.setError(null);
        }


        if (cdate.isEmpty() ) {
            date.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            date.setError(null);
        }

        if (ccost.isEmpty() || ccost.length() < 2) {
            cost.setError("phone Do not match");
            valid = false;
        } else {
            cost.setError(null);
        }

        return valid;
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
