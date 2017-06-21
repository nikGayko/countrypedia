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

    @SerializedName("subregion")
    private String mSubRegion;

    @SerializedName("population")
    private long mPopulation;

    @SerializedName("area")
    private long mArea;

    @SerializedName("currencies")
    private String mCurrency;

    @SerializedName("language")
    private String mLanguage;





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

    public void setName(String name) {
        mName = name;
    }

    public void setCapital(String capital) {
        mCapital = capital;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getSubRegion() {
        return mSubRegion;
    }

    public void setSubRegion(String subRegion) {
        mSubRegion = subRegion;
    }

    public long getPopulation() {
        return mPopulation;
    }

    public void setPopulation(long population) {
        mPopulation = population;
    }

    public long getArea() {
        return mArea;
    }

    public void setArea(long area) {
        mArea = area;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    @Override
    public int getType() {
        return TYPE_BODY;
    }


}
