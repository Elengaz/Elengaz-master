package com.SemiColon.Hmt.elengaz.Fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Activities.ServiceProvider_Home;
import com.SemiColon.Hmt.elengaz.Adapters.Comments_Adapter;
import com.SemiColon.Hmt.elengaz.Model.Comments;
import com.SemiColon.Hmt.elengaz.Model.OfficeDetailsModel;
import com.SemiColon.Hmt.elengaz.Model.OfficeDetailsModel1;
import com.SemiColon.Hmt.elengaz.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Delta on 02/03/2018.
 */

public class Fragment_Details extends Fragment {
    private ProgressBar progressBar;
    private LinearLayout container_data;
    private TextView office_name,client_name,client_rate,country,total_service,finished_service,eval;
    private ProgressBar pb_r1,pb_r2,pb_r3,pb_r4,pb_r5;
    private RecyclerView recView_comments;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<Comments> commentsList;

ServiceProvider_Home home;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        progressBar = view.findViewById(R.id.progressBar);
        container_data = view.findViewById(R.id.container_data);
        container_data.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        home= (ServiceProvider_Home) getActivity();
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        pb_r1 = view.findViewById(R.id.pb_r1);
        pb_r2 = view.findViewById(R.id.pb_r2);
        pb_r3 = view.findViewById(R.id.pb_r3);
        pb_r4 = view.findViewById(R.id.pb_r4);
        pb_r5 = view.findViewById(R.id.pb_r5);


        eval             = view.findViewById(R.id.eval);
        office_name      = view.findViewById(R.id.office_name);
        client_rate      = view.findViewById(R.id.client_rate);
        client_name      = view.findViewById(R.id.user_name);
        country          = view.findViewById(R.id.country);
        total_service    = view.findViewById(R.id.total_service);
        finished_service = view.findViewById(R.id.finished_service);

        commentsList = new ArrayList<>();
        recView_comments = view.findViewById(R.id.recView_comments);
        manager          = new LinearLayoutManager(getActivity());
        recView_comments.setLayoutManager(manager);
        recView_comments.setHasFixedSize(true);

        adapter = new Comments_Adapter(getActivity(),commentsList);
        recView_comments.setAdapter(adapter);


    }

    private void getAllOffice_Details()
    {
        Retrofit retrofit = APIClient.getClient();
        ServicesApi services = retrofit.create(ServicesApi.class);
        Call<OfficeDetailsModel1> call =services.getAllOfficeDetails(home.office_id);
        call.enqueue(new Callback<OfficeDetailsModel1>() {
            @Override
            public void onResponse(Call<OfficeDetailsModel1> call, Response<OfficeDetailsModel1> response) {
                if (response.isSuccessful())
                {
                 //   if (response.body().getMessage().equals("no service"))
                 //   {
                      //  Toast.makeText(getContext(), "no service", Toast.LENGTH_SHORT).show();
                   // }else {
                        OfficeDetailsModel1 officeDetailsModel1= response.body();
                        List<OfficeDetailsModel> officeDetailsModelList = officeDetailsModel1.getOfficeDetailsModelList();
                        OfficeDetailsModel officeDetailsModel = officeDetailsModelList.get(0);
                        UpdateUI(officeDetailsModel);
                  //  }

                }
            }

            @Override
            public void onFailure(Call<OfficeDetailsModel1> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateUI(OfficeDetailsModel officeDetailsModel) {
        client_rate.setText(officeDetailsModel.getOffice_clients_count().toString());
        eval.setText(String.valueOf(officeDetailsModel.getRate_evaluation())+"تقيم ");
        office_name.setText(officeDetailsModel.getOffice_title());
        client_name.setText(officeDetailsModel.getOffice_user_name());
        country.setText(officeDetailsModel.getOffice_city());
        total_service.setText(String.valueOf(officeDetailsModel.getTotal_service()));
        finished_service.setText(String.valueOf(officeDetailsModel.getFinished_service()));

        int total_eval = Integer.parseInt(officeDetailsModel.getOffice_evaluation_count());
        int r1 = officeDetailsModel.getStar_1_count();
        int r2 = officeDetailsModel.getStar_2_count();
        int r3 = officeDetailsModel.getStar_3_count();
        int r4 = officeDetailsModel.getStar_4_count();
        int r5 = officeDetailsModel.getStar_5_count();

        if (total_eval!=0)
        {
            int div1 = (r1/total_eval)*100;
            int div2 = (r2/total_eval)*100;
            int div3 = (r3/total_eval)*100;
            int div4 = (r4/total_eval)*100;
            int div5 = (r5/total_eval)*100;

            pb_r5.setProgress(div1);
            pb_r4.setProgress(div2);
            pb_r3.setProgress(div3);
            pb_r2.setProgress(div4);
            pb_r1.setProgress(div5);
        }






        commentsList.clear();
        this.commentsList.addAll(officeDetailsModel.getCommentsList());
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        container_data.setVisibility(View.VISIBLE);

    }

    @Override
    public void onStart() {
        super.onStart();
        getAllOffice_Details();
    }
}
