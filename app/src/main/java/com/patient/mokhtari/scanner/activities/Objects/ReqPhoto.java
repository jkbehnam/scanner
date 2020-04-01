package com.patient.mokhtari.scanner.activities.Objects;

public class ReqPhoto {


    private int id;
    private String url;

    public ReqPhoto(int id, String url) {
        this.setId(id);
        this.setUrl(url);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
