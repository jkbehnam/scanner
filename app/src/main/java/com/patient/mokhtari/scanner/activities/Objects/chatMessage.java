package com.patient.mokhtari.scanner.activities.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.objectbox.annotation.Id;
import io.objectbox.annotation.Entity;

@Entity
public class chatMessage implements Serializable {

    @Id
     Long cid;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("e_id")
    @Expose
    private String e_id;
    @SerializedName("g_id")
    @Expose
    private String g_id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;




    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
