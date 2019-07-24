package com.debugspace.nglah.Model.hassan_now;

import com.google.gson.annotations.SerializedName;

public class Driver_Model {
    @SerializedName("driver_national_id")
    private String driver_n_id;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("owner_name")
    private String owner_name;
    @SerializedName("first_name")
    private String f_name;
    @SerializedName("second_name")
    private String se_name;
    @SerializedName("last_name")
    private String l_name;
    @SerializedName("driver_license_id")
    private String driver_l_id;
    @SerializedName("phone")
    private String phone;
    @SerializedName("owner_city")
    private String d_city;
    @SerializedName("email")
    private String email;
    @SerializedName("user_name")
    private String usernmae;
    @SerializedName("password")
    private String password;


    private boolean checkEmail;

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public boolean isCheckEmail() {
        return checkEmail;
    }

    public void setCheckEmail(boolean checkEmail) {
        this.checkEmail = checkEmail;
    }

    public Driver_Model(String driver_n_id, String nationality, String f_name, String se_name, String l_name, String driver_l_id, String phone, String d_city, String email, String usernmae, String password) {
        this.driver_n_id = driver_n_id;
        this.nationality = nationality;
        this.f_name = f_name;
        this.se_name = se_name;
        this.l_name = l_name;
        this.driver_l_id = driver_l_id;
        this.phone = phone;
        this.d_city = d_city;
        this.email = email;
        this.usernmae = usernmae;
        this.password = password;
    }

    public Driver_Model() {
    }

    public String getDriver_n_id() {
        return driver_n_id;
    }

    public void setDriver_n_id(String driver_n_id) {
        this.driver_n_id = driver_n_id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getSe_name() {
        return se_name;
    }

    public void setSe_name(String se_name) {
        this.se_name = se_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getDriver_l_id() {
        return driver_l_id;
    }

    public void setDriver_l_id(String driver_l_id) {
        this.driver_l_id = driver_l_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getD_city() {
        return d_city;
    }

    public void setD_city(String d_city) {
        this.d_city = d_city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsernmae() {
        return usernmae;
    }

    public void setUsernmae(String usernmae) {
        this.usernmae = usernmae;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

