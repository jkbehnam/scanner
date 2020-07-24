package com.patient.mokhtari.scanner.activities.questioner;


public class CardItem {

    private final int mTextResource;
    private final int mTitleResource;
    private int id;
    private boolean yesNo;

    public CardItem(int title, int text, int id, boolean yesNo) {
        mTitleResource = title;
        mTextResource = text;
        this.id = id;
        this.yesNo = yesNo;
    }

    public int getId() {
        return id;
    }

    public boolean isYesNo() {
        return yesNo;
    }

    public void setYesNo(boolean yesNo) {
        this.yesNo = yesNo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
