package com.patient.mokhtari.scanner.activities.BodyPart.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyPointMain {
    @SerializedName("fx")
    @Expose
   public float fx;
    @SerializedName("fy")
    @Expose
    public  float fy;
    @SerializedName("show_back")
    @Expose
    public boolean mShowingBack;
    BodyPointMain(float fx, float fy,Boolean isFront){
        this.fx=fx;
        this.fy=fy;
        this.mShowingBack =isFront;
    }
}
