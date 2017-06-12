package com.example.nick.countrypedia;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("name")
    private String mName;

    @SerializedName("capital")
    private String mCapital;

    @SerializedName("region")
    private String mRegion;

    public Country(String name, String capital, String region) {
        mName = name;
        mCapital = capital;
        mRegion = region;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCapital() {
        return mCapital;
    }

    public void setCapital(String capital) {
        mCapital = capital;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }
}
