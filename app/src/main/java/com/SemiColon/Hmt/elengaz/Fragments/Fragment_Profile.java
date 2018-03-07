package com.SemiColon.Hmt.elengaz.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Activities.ServiceProvider_Home;
import com.SemiColon.Hmt.elengaz.Model.ProfileModel;
import com.SemiColon.Hmt.elengaz.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Profile extends Fragment {

    TextView name,email,phone,title,city;
    Button update,update_pass,logout;

    ServiceProvider_Home home;
    String oname,oemail,ophone,opass,ocity,otitle;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        // Toast.makeText(getActivity(), "" +id, Toast.LENGTH_SHORT).show();

        home= (ServiceProvider_Home) getActivity();
        name=view.findViewById(R.id.txt_Officcer_name);
        phone=view.findViewById(R.id.txt_Officcer_phone);
        email=view.findViewById(R.id.txt_email);
        title=view.findViewById(R.id.txt_title);
        city=view.findViewById(R.id.txt_city);
        update=view.findViewById(R.id.btnupdate);
        update_pass=view.findViewById(R.id.btnpass);

        logout=view.findViewById(R.id.btnlogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Preferense preferense=new Preferense(getContext());
                preferense.clear();
                Intent i=new Intent(getActivity(), Loogin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(i);
                getActivity().finish();*/

            }
        });
       // Toast.makeText(getContext(), ""+home.office_id, Toast.LENGTH_SHORT).show();

        ServicesApi services= APIClient.getClient().create(ServicesApi.class);
        Call<ProfileModel> call=services.Display_OfficeProfile(home.office_id);

        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
            //    Toast.makeText(getContext(), "in"+home.office_id, Toast.LENGTH_SHORT).show();
                name.setText(response.body().getOffice_user_name());
                phone.setText(response.body().getOffice_phone());
                email.setText(response.body().getOffice_email());
                city.setText(response.body().getOffice_city());
                title.setText(response.body().getOffice_title());

                // Toast.makeText(getContext(), ""+name+phone+email, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
             //   Toast.makeText(getContext(), "mmmmmm"+t, Toast.LENGTH_SHORT).show();
                Log.e( "mmmmm: ",t+"" );


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update_profile();
            }


        });

        update_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(" تعديل كلمة المرور");

// Set up the input
                final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input.setHint("ادخل كلمة المرور الجديده");
                builder.setView(input);


// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        opass = input.getText().toString();
                        ServicesApi service = APIClient.getClient().create(ServicesApi.class);


                        Call<ProfileModel> OfficcerCall = service.update_officer_pass(home.office_id, opass);
                        // startActivity(new Intent(Register.this, ListMarma.class));

                        OfficcerCall.enqueue(new Callback<ProfileModel>() {
                            @Override
                            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {


                                if (response.isSuccessful()) {

//                                    Toast.makeText(getContext(), "success"+opass
//                                            , Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ProfileModel> call, Throwable t) {
                                Log.d("onFailure", t.toString());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        return view;
    }

    public void update_profile(){





        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("تعديل الصفحه الشخصيه");


        Context context = builder.getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText cname = new EditText(getContext());
        final EditText cemail = new EditText(getContext());
        final EditText cphone = new EditText(getContext());
        final EditText ctitle = new EditText(getContext());
        final EditText ccity = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        cname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        cname.setText(name.getText().toString());
        layout.addView(cname);

        cemail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        cemail.setText(email.getText().toString());
        layout.addView(cemail);

        cphone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
        cphone.setText(phone.getText().toString());
        layout.addView(cphone);

        ctitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        ctitle.setText(title.getText().toString());
        layout.addView(ctitle);

        ccity.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        ccity.setText(city.getText().toString());
        layout.addView(ccity);

        builder.setView(layout);
// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   cost = input.getText().toString();
                name.setText(cname.getText().toString());
                email.setText(cemail.getText().toString());
                phone.setText(cphone.getText().toString());
                city.setText(ccity.getText().toString());
                title.setText(ctitle.getText().toString());
                oname=cname.getText().toString();
                oemail=cemail.getText().toString();
                ophone=cphone.getText().toString();
                ocity=ccity.getText().toString();
                otitle=ctitle.getText().toString();

                ServicesApi service=APIClient.getClient().create(ServicesApi.class);
                Call<ProfileModel> call1=service.update_officer_profile(home.office_id,oname,oemail,ophone,ocity,otitle);

                call1.enqueue(new Callback<ProfileModel>() {
                    @Override
                    public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {

                        if (response.isSuccessful()){
//                            Toast.makeText(getContext(), "success"+oname+oemail, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileModel> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
