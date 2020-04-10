package com.patient.mokhtari.scanner.activities.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor extends Object {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("expert")
    @Expose
    private String expert;
    @SerializedName("photo")
    @Expose
    private String img;
    @SerializedName("api_key")
    @Expose
    private String id;

   public Doctor (String name,String expert,String img,String id){
       this.name=name;
       this.expert=expert;
       this.img=img;
       this.id=id;
   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
