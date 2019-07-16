package com.example.nglah.Model.hassan_now;

public class Driver_Model {
    private String driver_n_id;
    private String nationality;
    private String f_name;
    private String se_name;
    private String l_name;
    private String driver_l_id;
    private String phone;
    private String d_city;
    private String d_region;
    private String email;
    private String usernmae;
    private String password;

    public Driver_Model(String driver_n_id, String nationality, String f_name, String se_name, String l_name, String driver_l_id, String phone, String d_city, String d_region, String email, String usernmae, String password) {
        this.driver_n_id = driver_n_id;
        this.nationality = nationality;
        this.f_name = f_name;
        this.se_name = se_name;
        this.l_name = l_name;
        this.driver_l_id = driver_l_id;
        this.phone = phone;
        this.d_city = d_city;
        this.d_region = d_region;
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

    public String getD_region() {
        return d_region;
    }

    public void setD_region(String d_region) {
        this.d_region = d_region;
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

