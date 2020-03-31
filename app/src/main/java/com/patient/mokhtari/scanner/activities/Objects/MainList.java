package com.patient.mokhtari.scanner.activities.Objects;

public class MainList {
    private String title;
    private String img;
    private boolean state;

    public MainList(String title1,String img1,boolean state){
        title=title1;
        img=img1;
        this.setState(state);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
