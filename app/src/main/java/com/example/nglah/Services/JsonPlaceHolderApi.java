package com.example.nglah.Services;

import com.example.nglah.Model.DriverModel.LoginDriverService;
import com.example.nglah.Model.NglahModel.LoginNglahService;
import com.example.nglah.Model.hassan_now.Driver_Model;
import com.example.nglah.Model.hassan_now.User_Model;
import com.example.nglah.Model.hassan_now.Verification_model;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

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

///////////////////////////////////////////////////////////////////////////////

    // hassan


    @FormUrlEncoded
    @POST("nglah/nglah_owner/nglah_owner_registration.php")
    Call<User_Model>CreateUser(@FieldMap Map<String,String> fields);
    @FormUrlEncoded
    @POST("nglah/car_owner/car_owner_registration.php")
    Call<Driver_Model>CreateDriver(@FieldMap Map<String,String> fields);

    @GET("nglah/car_owner/{user_email}/Verification.php")
    Call<List<Verification_model>>getVerifiCode(@Path("user_email") String user_email);

    @FormUrlEncoded
    @POST("verification.php")
    Call<Verification_model>verifi(@Field("user_email") String email);

}

