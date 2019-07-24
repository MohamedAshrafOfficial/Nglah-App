package com.debugspace.nglah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    /////////////////////// driver /////////////////////////

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

    //////////////////////// nglah /////////////////////

    @SerializedName("nglah_id")
    @Expose
    private int nglahId;

    @SerializedName("nglah_type")
    @Expose
    private String nglahType;

    @SerializedName("thing_type")
    @Expose
    private String thingType;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("details")
    @Expose
    private String details;


    public String getDriverId() {
        return driverId;
    }

    public int getNglahId() {
        return nglahId;
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

    public String getNglahType() {
        return nglahType;
    }

    public String getThingType() {
        return thingType;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDetails() {
        return details;
    }
}
