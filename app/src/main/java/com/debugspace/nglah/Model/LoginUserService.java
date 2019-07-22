package com.debugspace.nglah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginUserService {

    @SerializedName("success")
    private boolean success;

    @SerializedName("login")
    @Expose
    private List<User> userInfo;


    public boolean isSuccess() {
        return success;
    }

    public List<User> getUserInfo() {
        return userInfo;
    }
}
