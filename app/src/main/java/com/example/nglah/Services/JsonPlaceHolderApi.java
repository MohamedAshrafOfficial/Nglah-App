package com.example.nglah.Services;

import com.example.nglah.Model.DriverModel.AcceptDriverService;
import com.example.nglah.Model.DriverModel.LoginDriverService;
import com.example.nglah.Model.NglahModel.LoginNglahService;
import com.example.nglah.Model.NglahModel.NglahOrdersService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.POST;
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
}

