package com.nglah.masrytech.Model.NglahModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NglahOrdersService {

    @SerializedName("nglah_order_inside")
    @Expose
    private List<Nglah> nglahOrdersInside;

    @SerializedName("nglah_order_outside")
    @Expose
    private List<Nglah> nglahOrdersOutside;

    public List<Nglah> getNglahOrdersInside() {
        return nglahOrdersInside;
    }

    public List<Nglah> getNglahOrdersOutside() {
        return nglahOrdersOutside;
    }
}
