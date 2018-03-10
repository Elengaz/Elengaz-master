package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.API.Service.Tags;
import com.SemiColon.Hmt.elengaz.Model.Client_Model;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Client_Profile_Activity extends AppCompatActivity implements View.OnClickListener{
    private final int IMAGE_REQ=201;
    private CircleImageView user_photo;
    private EditText user_email,user_phone;
    private Client_Model client_model;
    private ImageView back,doneBtn;
    private Button updateBtn;
    private Target target;
    private Bitmap old_bitmap_img,new_bitmap_img;

    private String old_enCodedPhoto="";
    private String old_email="";
    private String old_phone="";

    private String new_enCodedPhoto ="";
    private String new_email="";
    private String new_phone="";
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ptofile);
        initView();
        getDataFromIntent();
    }



    private void initView() {
        user_photo = findViewById(R.id.user_photo);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        back       = findViewById(R.id.back);
        doneBtn    = findViewById(R.id.doneBtn);
        updateBtn  = findViewById(R.id.updateBtn);

        user_photo.setEnabled(false);
        user_photo.setOnClickListener(this);
        back.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);


    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("client_model"))
            {
                client_model = (Client_Model) intent.getSerializableExtra("client_model");
                id = intent.getStringExtra("client_id");
                Update_UI(client_model);
            }
        }
    }

    private void Update_UI(Client_Model client_model) {
        user_email.setText(client_model.getClient_email());
        user_phone.setText(client_model.getClient_phone());

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                old_bitmap_img = bitmap;
                user_photo.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        if (client_model.getClient_img()!=null||!TextUtils.isEmpty(client_model.getClient_img()))
        {
            Picasso.with(Client_Profile_Activity.this).load(Tags.Image_Path+client_model.getClient_img()).into(target);
        }

        if (old_bitmap_img!=null)
        {
            old_enCodedPhoto = enCodeImage(old_bitmap_img);

        }
        old_email = user_email.getText().toString();
        old_phone = user_phone.getText().toString();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(target);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.user_photo:
                selectPhoto();
                break;
            case R.id.back:

                finish();
                break;
            case R.id.doneBtn:
                doneBtn.setVisibility(View.INVISIBLE);
                updateBtn.setVisibility(View.VISIBLE);
                user_photo.setEnabled(false);
                user_email.setEnabled(false);
                user_phone.setEnabled(false);
                UpdateData();


                break;
            case R.id.updateBtn:
                doneBtn.setVisibility(View.VISIBLE);
                user_photo.setEnabled(true);
                user_email.setEnabled(true);
                user_phone.setEnabled(true);
                updateBtn.setVisibility(View.INVISIBLE);


                break;
        }
    }

    private void UpdateData() {

        if (new_bitmap_img!=null)
        {
            new_enCodedPhoto = enCodeImage(new_bitmap_img);

        }else
            {
                new_enCodedPhoto = enCodeImage(old_bitmap_img);

            }
       new_email = user_email.getText().toString();
       new_phone = user_phone.getText().toString();




       if (old_enCodedPhoto.equals(new_enCodedPhoto)&& old_phone.equals(new_phone)&&old_email.equals(new_email))
       {
           Toast.makeText(this, R.string.nochange, Toast.LENGTH_SHORT).show();
       }else
           {

               ServicesApi servicesApi= APIClient.getClient().create(ServicesApi.class);
               Call<ResponseModel> call = servicesApi.update_client_profile(id, new_email, new_phone, new_enCodedPhoto);
               call.enqueue(new Callback<ResponseModel>() {
                   @Override
                   public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                       if (response.isSuccessful())
                       {
                           if (response.body().getSuccess()==1)
                           {
                               Toast.makeText(Client_Profile_Activity.this, "تم تحديث الملف الشخصي بنجاح", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }

                   @Override
                   public void onFailure(Call<ResponseModel> call, Throwable t) {
                       Log.e("error",t.getMessage());
                   }
               });
               new_bitmap_img = old_bitmap_img;
               old_email = user_email.getText().toString();
               old_phone = user_phone.getText().toString();

           }


    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,getString(R.string.sel_per_photo)),IMAGE_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode == IMAGE_REQ && data!=null)
        {
            Uri uri = data.getData();
            try {
                new_bitmap_img = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                user_photo.setImageBitmap(new_bitmap_img);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String enCodeImage(Bitmap bitmap)
    {
        if (bitmap!=null)
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);

            byte [] bytes = outputStream.toByteArray();

            return Base64.encodeToString(bytes,Base64.DEFAULT);
        }
        return null;
    }
}
