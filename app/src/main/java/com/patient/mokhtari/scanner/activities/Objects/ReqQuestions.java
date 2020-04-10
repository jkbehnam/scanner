package com.patient.mokhtari.scanner.activities.Objects;

public class ReqQuestions extends Object {


    private int q_id;
    private String YNQ;
    private String Desc;

    public ReqQuestions(int q_id, String YNQ, String desc) {
        this.q_id = q_id;
        this.YNQ = YNQ;
        Desc = desc;
    }


    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public String isYNQ() {
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
}
