package com.nglah.masrytech.Services;

import com.nglah.masrytech.Model.DriverModel.AcceptDriverService;
import com.nglah.masrytech.Model.DriverModel.LoginDriverService;
import com.nglah.masrytech.Model.LoginUserService;
import com.nglah.masrytech.Model.NglahModel.LoginNglahService;
import com.nglah.masrytech.Model.NglahModel.NglahOrdersService;
import com.nglah.masrytech.Model.hassan_now.Verification_model;
import com.nglah.masrytech.Model.hassan_now.user_service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    // Abdo Api

//    @FormUrlEncoded
//    @POST("car_owner/car_owner_login.php")
//    Call<Driver> getDriverInfo(
//            @Field("email") String email,
//            @Field("password") String password
//    );


    @GET("car_owner/car_owner_login.php")
    Call<LoginDriverService> getDriverInfo(
            @Query("email") String email,
            @Query("password") String password
    );

//    @FormUrlEncoded
//    @POST("nglah_owner/nglah_owner_login.php")
//    Call<String> getNglahInfo(
//            @Field("email") String email,
//            @Field("password") String password
//    );

    @GET("nglah_owner/nglah_owner_login.php")
    Call<LoginNglahService> getNglahInfo(
            @Query("email") String email,
            @Query("password") String password
    );


    @GET("login.php")
    Call<LoginUserService> getUserInfo(
            @Query("email") String email,
            @Query("password") String password
    );


    @GET("car_owner/drivers_accepted_request.php")
    Call<AcceptDriverService> getDriversAccepted(
            @Query("nglah_id") int nglah_id
    );

    @GET("nglah_owner/show_nglah_order.php")
    Call<NglahOrdersService> getNglahOrders(
            @Query("driver_national_id") String driver_national_id
    );

    @FormUrlEncoded
    @POST("car_owner/driver_ok_nglah.php")
    Call<String> sendNglahRequest(
            @Field("driver_national_id") String driver_national_id,
            @Field("nglah_id") int nglah_id,
            @Field("nglah_price") double nglah_price
    );


    @FormUrlEncoded
    @POST("notification_services/index.php")
    Call<String> sendNotificationToNglah(
            @Field("noti_code") String noti_code,
            @Field("noti_message") String noti_message
    );

///////////////////////////////////////////////////////////////////////////////

    // hassan


    @FormUrlEncoded
    @POST("nglah/nglah_owner/nglah_owner_registration.php")
    Call<LoginNglahService>CreateUser(@FieldMap Map<String,String> fields);

    @FormUrlEncoded
    @POST("nglah/nglah_owner/update_nglah_owner.php")
    Call<LoginNglahService>Update_User(@FieldMap Map<String,String> fields);

    @FormUrlEncoded
    @POST("nglah/car_owner/car_owner_registration.php")
    Call<LoginDriverService>CreateDriver(@FieldMap Map<String,String> fields);

    @FormUrlEncoded
    @POST("nglah/car_owner/update_car_owner.php")
    Call<LoginDriverService>Update_Driver(@FieldMap Map<String,String> fields);


    @FormUrlEncoded
    @POST("verification.php")
    Call<Verification_model>verifi(@Field("user_email") String email);

    @FormUrlEncoded
    @POST("nglah/check_email.php")
    Call<Verification_model>GetEmail(@Field("email") String Email);

    @FormUrlEncoded
    @POST("nglah/nglah_owner/user_order_inside.php")
    Call<LoginNglahService>CreateUser__Inside_Order(@FieldMap Map<String,String> fields);

    @FormUrlEncoded
    @POST("nglah/nglah_owner/user_order_outside.php")
    Call<LoginNglahService>CreateUser__Outside_Order(@FieldMap Map<String,String> fields);

//    @FormUrlEncoded
//    @POST("nglah/nglah_owner/select_nglah_owner_info.php")
//    Call<User_Model>GetData(@Field("email") String email);

    @GET("nglah/nglah_owner/select_nglah_owner_info.php")
    Call<user_service> GetData(
            @Query("email") String email
    );

    @GET("nglah/car_owner/select_car_owner_info.php")
    Call<user_service> GetCar_Owner_Data(
            @Query("email") String email
    );
    @GET("nglah/car_owner/select_car_data.php")
    Call<user_service> GetCar__Data(
            @Query("driver_national_id") String driver_national_id);
}