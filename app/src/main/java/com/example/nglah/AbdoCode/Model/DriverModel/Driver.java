package com.example.nglah.AbdoCode.Model.DriverModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("driver_national_id")
    @Expose
    private int driverId;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;


    public Driver(int driverId, String firstName, String lastName, String email) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

