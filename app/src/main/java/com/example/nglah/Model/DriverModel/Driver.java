package com.example.nglah.Model.DriverModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("driver_national_id")
    @Expose
    private String driverId;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("rate")
    @Expose
    private String rate;

    @SerializedName("nglah_price")
    @Expose
    private String price;


    public Driver(String driverId, String firstName, String lastName, String email) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public String getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getRate() {
        return rate;
    }

    public String getPrice() {
        return price;
    }
}

