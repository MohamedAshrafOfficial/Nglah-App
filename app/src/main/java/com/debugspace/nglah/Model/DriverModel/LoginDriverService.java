package com.debugspace.nglah.Model.DriverModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginDriverService {

    @SerializedName("success")
    private boolean success;

    @SerializedName("login")
    @Expose
    private List<Driver> driverInfo;


    public boolean isSuccess() {
        return success;
    }

    public List<Driver> getDriverInfo() {
        return driverInfo;
    }
}

