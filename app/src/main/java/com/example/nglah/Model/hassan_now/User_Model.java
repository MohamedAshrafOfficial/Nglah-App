package com.example.nglah.Model.hassan_now;

public class User_Model {
    private String f_name;
    private String l_name;
    private String phone;
    private String email;
    private String username;
    private String password;

    public User_Model() {
    }

    public User_Model(String f_name, String l_name, String phone, String email, String username, String password) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
