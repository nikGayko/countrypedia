package com.example.nick.countrypedia.view.item;

import com.google.gson.annotations.SerializedName;

public class Country extends ListItem {

    @SerializedName("name")
    private String mName;

    @SerializedName("capital")
    private String mCapital;

    @SerializedName("region")
    private String mRegion;

    @SerializedName("flag")
    private String mFlag;

    public Country(String name, String capital, String region) {
        mName = name;
        mCapital = capital;
        mRegion = region;
    }

    public Country(String name, String capital, String region, String flag) {
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

    public String getFlag() {
        return mFlag;
    }

    public void setFlag(String flag) {
        mFlag = flag;
    }

    @Override
    public int getType() {
        return TYPE_BODY;
    }


}
