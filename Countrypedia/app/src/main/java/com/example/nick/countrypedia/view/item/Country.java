package com.example.nick.countrypedia.view.item;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Country extends ListItem {

    @SerializedName("name")
    private String mName;

    @SerializedName("capital")
    private String mCapital;

    @SerializedName("region")
    private String mRegion;

    private Bitmap mFlag;

    public Country(String name, String capital, String region) {
        mName = name;
        mCapital = capital;
        mRegion = region;
    }

    public Country(String name, String capital, String region, Bitmap flag) {
        this(name, capital, region);
        mFlag = flag;
    }


    public String getName() {
        return mName;
    }

    public String getCapital() {
        return mCapital;
    }

    public String getRegion() {
        return mRegion;
    }

    public Bitmap getFlag() {
        return mFlag;
    }

    public void setFlag(Bitmap flag) {
        mFlag = flag;
    }

    @Override
    public int getType() {
        return TYPE_BODY;
    }


}
