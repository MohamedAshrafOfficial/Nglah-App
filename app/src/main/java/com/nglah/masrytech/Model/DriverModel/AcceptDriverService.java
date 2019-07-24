package com.nglah.masrytech.Model.DriverModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptDriverService {

    @SerializedName("driver_accepted")
    @Expose
    private List<Driver> driversAccepted;

    public List<Driver> getDriversAccepted() {
        return driversAccepted;
    }
}
