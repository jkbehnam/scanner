package com.patient.mokhtari.scanner.activities.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddImage {
    @SerializedName("photo")
    @Expose
    private String address="";

    public AddImage(String address){
        this.address=address;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
