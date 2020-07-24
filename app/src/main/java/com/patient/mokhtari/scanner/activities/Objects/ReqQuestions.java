package com.patient.mokhtari.scanner.activities.Objects;

import com.google.gson.annotations.SerializedName;

public class ReqQuestions {

    @SerializedName("q_id")
    private int q_id;
    @SerializedName("YNQ")
    private String YNQ;
    @SerializedName("Desc")
    private String Desc;

    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public String getYNQ() {
        return YNQ;
    }

    public void setYNQ(String YNQ) {
        this.YNQ = YNQ;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public ReqQuestions(int q_id, String YNQ, String desc) {
        this.q_id = q_id;
        this.YNQ = YNQ;
        Desc = desc;
    }
    public ReqQuestions( String YNQ, String desc) {
        this.q_id = q_id;
        this.YNQ = YNQ;
        Desc = desc;
    }


}
