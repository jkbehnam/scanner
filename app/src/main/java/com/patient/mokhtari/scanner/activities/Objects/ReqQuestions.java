package com.patient.mokhtari.scanner.activities.Objects;

public class ReqQuestions {


    private int q_id;
    private boolean YNQ;
    private String Desc;

    public ReqQuestions(int q_id, boolean YNQ, String desc) {
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

    public boolean isYNQ() {
        return YNQ;
    }

    public void setYNQ(boolean YNQ) {
        this.YNQ = YNQ;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
