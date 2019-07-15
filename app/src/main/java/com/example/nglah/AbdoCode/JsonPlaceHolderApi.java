package com.example.nglah.AbdoCode;

import com.example.nglah.AbdoCode.Model.DriverModel.LoginDriverService;
import com.example.nglah.AbdoCode.Model.NglahModel.LoginNglahService;

import retrofit2.Call;
import retrofit2.http.GET;
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

