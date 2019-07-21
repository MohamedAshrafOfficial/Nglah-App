package com.debugspace.nglah.Model.hassan_now;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class user_service {
    @SerializedName("nglah_owner_info")
    @Expose
    private List<User_Model> user_model;

    @SerializedName("car_owner_info")
    @Expose
    private List<Driver_Model>driver_model;

    @SerializedName("car_info")
    @Expose
    private List<Car_Model>car_model;

    public List<Car_Model> getCar_model() {
        return car_model;
    }

    public List<Driver_Model> getDriver_model() {
        return driver_model;
    }

    public List<User_Model> getUser_model() {
        return user_model;
    }
}
