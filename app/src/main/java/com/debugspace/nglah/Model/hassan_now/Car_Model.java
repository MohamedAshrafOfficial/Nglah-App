package com.debugspace.nglah.Model.hassan_now;

import com.google.gson.annotations.SerializedName;

public class Car_Model {

    @SerializedName("car_type")
    private String car_type;
    @SerializedName("car_image")
    private String car_image;
    @SerializedName("panel_id")
    private String panal_id;
    @SerializedName("allowed_weight")
    private String allowed_weight;
    private String driver_car_name;
    @SerializedName("driver_city")
    private String driver_car_city;
    @SerializedName("driver_region")
    private String driver_car_region;

    public Car_Model(String car_type, String car_image, String panal_id, String allowed_weight, String driver_car_name, String driver_car_city, String driver_car_region) {
        this.car_type = car_type;
        this.car_image = car_image;
        this.panal_id = panal_id;
        this.allowed_weight = allowed_weight;
        this.driver_car_name = driver_car_name;
        this.driver_car_city = driver_car_city;
        this.driver_car_region = driver_car_region;
    }

    public Car_Model() {
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getPanal_id() {
        return panal_id;
    }

    public void setPanal_id(String panal_id) {
        this.panal_id = panal_id;
    }

    public String getAllowed_weight() {
        return allowed_weight;
    }

    public void setAllowed_weight(String allowed_weight) {
        this.allowed_weight = allowed_weight;
    }

    public String getDriver_car_name() {
        return driver_car_name;
    }

    public void setDriver_car_name(String driver_car_name) {
        this.driver_car_name = driver_car_name;
    }

    public String getDriver_car_city() {
        return driver_car_city;
    }

    public void setDriver_car_city(String driver_car_city) {
        this.driver_car_city = driver_car_city;
    }

    public String getDriver_car_region() {
        return driver_car_region;
    }

    public void setDriver_car_region(String driver_car_region) {
        this.driver_car_region = driver_car_region;
    }
}
