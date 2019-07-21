package com.debugspace.nglah.Model.NglahModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginNglahService {

    @SerializedName("success")
    private boolean success;

    @SerializedName("login")
    @Expose
    private List<Nglah> nglahOwnerInfo;


    public boolean isSuccess() {
        return success;
    }

    public List<Nglah> getNglahOwnerInfo() {
        return nglahOwnerInfo;
    }
}
