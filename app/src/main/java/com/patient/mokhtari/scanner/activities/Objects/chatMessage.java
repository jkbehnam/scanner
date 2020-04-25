package com.patient.mokhtari.scanner.activities.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;




public class chatMessage implements Serializable {


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("request_key")
    @Expose
    private String request_key;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("doc_id")
    @Expose
    private String doc_id;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("time")
    @Expose
    private String time;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest_key() {
        return request_key;
    }

    public void setRequest_key(String request_key) {
        this.request_key = request_key;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }





}
