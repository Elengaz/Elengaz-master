package com.SemiColon.Hmt.elengaz.API.Service;


import com.SemiColon.Hmt.elengaz.Model.AddServicesResponse;
import com.SemiColon.Hmt.elengaz.Model.Bank_Account_Model;
import com.SemiColon.Hmt.elengaz.Model.Client_Model;
import com.SemiColon.Hmt.elengaz.Model.ContactModel;
import com.SemiColon.Hmt.elengaz.Model.DisplayServicesModel;
import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.Model.OfficeDetailsModel1;
import com.SemiColon.Hmt.elengaz.Model.OfficeOfferModel;
import com.SemiColon.Hmt.elengaz.Model.Office_Service_Model;
import com.SemiColon.Hmt.elengaz.Model.Order_State_Model;
import com.SemiColon.Hmt.elengaz.Model.ProfileModel;
import com.SemiColon.Hmt.elengaz.Model.Register_Client_Model;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.Model.Services;
import com.SemiColon.Hmt.elengaz.Model.office_order_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by elashry on 2/10/2018.
 */

public interface ServicesApi {



    /*---------------------------------------- user login & register -------------------------------------------*/

    @FormUrlEncoded
    @POST("RegisterClient")
    Call<Register_Client_Model>userSignUp(

                         @Field("client_img") String photo,
                         @Field("client_user_name") String name,
                         @Field("client_password") String password,
                         @Field("client_email") String email,
                         @Field("client_phone") String mobile,
                         @Field("client_token_id")String client_token_id);

    @FormUrlEncoded
    @POST("LoginClient")
    Call<Register_Client_Model> userLogIn(@Field("client_user_name") String user_name,
                                          @Field("client_password") String password);

    /*---------------------------------------- office login & register -------------------------------------------*/

    @FormUrlEncoded
    @POST("RegisterOffice")
    Call<ProfileModel> officeSignUp(
                           @Field("office_logo") String photo,
                           @Field("office_user_name") String name,
                           @Field("office_password") String password,
                           @Field("office_email") String email,
                           @Field("office_phone") String mobile,
                           @Field("office_title") String title,
                           @Field("office_city") String city,
                           @Field("office_token_id")String office_token_id,
                           @Field("office_area")String office_area         );

    @FormUrlEncoded
    @POST("LoginOffice")
    Call<ProfileModel> officeLogIn(@Field("office_user_name") String user_name,
                        @Field("office_password") String password);


    /*---------------------------------------- display client data -------------------------------------------*/

    @GET("UpadateRegisterClient/{client_id}")
    Call <Client_Model> DisplayClientData(@Path("client_id") String client_id);
    /*---------------------------------------- display services -------------------------------------------*/

    @GET("Services")
    Call<List<Services>> getServicesData();

    @GET("AllOffice")
    Call<List<Officces>> getofficces();



    @FormUrlEncoded
    @POST("SelectService")
    Call<Services> sendservice(@Field("client_id") String client_id,
                          @Field("service_id") String service_id);




    @FormUrlEncoded
    @POST("OrdersToOffice")
    Call<Officces> sendoffices(@Field("office_id[]") ArrayList<String> office_id,
                               @Field("client_id") String client_id,
                               @Field("category_id")String category_id);
    @FormUrlEncoded
    @POST("NewService")
    Call <AddServicesResponse> AddAllServiceData(@Field("office_id[]") List<String> office_id,
                                          @Field("client_id") String client_id,
                                          @Field("category_id")String category_id,
                                          @Field("service_name")String service_name,
                                          @Field("service_details")String service_details,
                                          @Field("service_date")String service_date,
                                          @Field("phone_number")String phone_number,
                                          @Field("other_phone")String other_phone,
                                          @Field("email")String email,
                                          @Field("google_lat")String google_lat,
                                          @Field("google_lng")String google_lng);

    @FormUrlEncoded
    @POST("AddOneService")
    Call<Register_Client_Model> AddOneService(@Field("service_name") String service_name,
                                              @Field("service_details") String service_details,
                                              @Field("phone_number") String phone_number,
                                              @Field("other_phone") String other_phone,
                                              @Field("email") String email,
                                              @Field("google_lng") String google_lng,
                                              @Field("google_lat") String google_lat,
                                              @Field("service_date") String service_date,
                                              @Field("client_service_id") String client_service_id
    );


    @FormUrlEncoded
    @POST("SearchService")
    Call<List<Services>> searchservice(@Field("search_title_service")String search_title_service);


    @FormUrlEncoded
    @POST("SearchAllOffices")
    Call<List<Officces>> searchByRate(@FieldMap Map<String,String> map);


    @GET("BankAccounts")
    Call<List<Bank_Account_Model>> getBankAccounts();

    @FormUrlEncoded
    @POST("ConfirmTransfer")
    Call<Register_Client_Model> sendPayment(@Field("client_service_id") String client_service_id,
                                            @Field("transfer_person") String transfer_person,
                                            @Field("transfer_amount") String transfer_amount,
                                            @Field("transfer_date") String transfer_date,
                                            @Field("transfer_image") String transfer_image);

    @GET("OfficeOffers/{client_service_id}")
    Call<List<OfficeOfferModel>> DisplayAll_OfficesOffers(@Path("client_service_id") String client_service_id);


        @FormUrlEncoded
        @POST("OfficeOffers/{client_service_id}")
        Call<ResponseModel> Send_OfficesOffersDone(@Path("client_service_id") String client_service_id, @Field("office_id_fk") String office_id_fk);

        @FormUrlEncoded
        @POST("AddEvaluation")
        Call<ResponseModel> AddRate(@FieldMap Map <String ,String> map);


    @GET("OfficeProfile/{office_id}")
    Call<ProfileModel> Display_OfficeProfile(@Path("office_id") String office_id);

    @FormUrlEncoded
    @POST("UpdateRegisterOffice/{id}")
    Call<ProfileModel> update_officer_profile(@Path("id") String id,
                      @Field("office_user_name") String name,
                      @Field("office_email") String email,
                      @Field("office_phone") String mobile,
                      @Field("office_city") String city,
                      @Field("office_title") String title     );

    @FormUrlEncoded
    @POST("UpdateOfficePassWord")
    Call<ProfileModel> update_officer_pass(@Field("office_id") String id,
                           @Field("office_password") String password);


    @GET("AllOfficeOrder/{office_id}")
    Call<List<office_order_model>> Display_AllOfficeOrder(@Path("office_id") String office_id);


    @FormUrlEncoded
    @POST("AddOfficeOfferCost")
    Call<office_order_model> AddOfficeOfferCost(@Field("office_service_cost") String office_service_cost,
                                                @Field("client_id_fk") String client_id_fk,
                                                @Field("order_id") String order_id  );

    @GET("MyService/{client_id}")
    Call<List<DisplayServicesModel>> Display_AllServiceOrder(@Path("client_id") String client_id);

    @GET("OfficeDetails/{office_id}")
    Call <OfficeDetailsModel1> getAllOfficeDetails(@Path("office_id") String office_id);

    @FormUrlEncoded
    @POST("ContactUs")
    Call<ContactModel> ContactUs(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("subject") String subject,
                                 @Field("message") String message);


    @FormUrlEncoded
    @POST("UpdateToken")
    Call<ResponseModel> UpdateToken(@FieldMap Map<String,String> map);

    @GET("OfficeService/{office_id}")
    Call<List<Office_Service_Model>> Display_OfficeService(@Path("office_id") String office_id);


    @FormUrlEncoded
    @POST("EndService")
    Call<Office_Service_Model> EndService(@Field("client_service_id") String client_service_id);


    @FormUrlEncoded
    @POST("UpadateRegisterClient/{client_id}")
    Call<ResponseModel> update_client_profile(@Path("client_id") String id,
                                              @Field("client_email") String client_email,
                                              @Field("client_phone") String client_phone ,
                                              @Field("client_img")String client_img);

    @GET("ViewServiceState/{client_service_id}")
    Call<Order_State_Model> ViewServiceState(@Path("client_service_id") String client_service_id);

}


