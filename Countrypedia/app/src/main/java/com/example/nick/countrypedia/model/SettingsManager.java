package com.example.nick.countrypedia.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nick.countrypedia.model.parameter.DisplayParameter;
import com.example.nick.countrypedia.model.parameter.SearchParameter;

public class SettingsManager {

    private static SettingsManager mSettingsManager;

    private SettingsManager() {
        mStateManager = StateManager.getInstance();
    }

    public synchronized static SettingsManager getInstance() {
        if (mSettingsManager == null) {
            mSettingsManager = new SettingsManager();
        }
        return mSettingsManager;
    }

    private StateManager mStateManager;

    private boolean mSearchCountry;
    private boolean mSearchCapital;
    private DisplayParameter mDisplayParameter;

    private final String COUNTRY = "country";
    private final String CAPITAL = "capital";
    private final String DISPLAY = "display";

    public void initialization(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        setSearchCountry(preferences.getBoolean(COUNTRY, true));
        setSearchCapital(preferences.getBoolean(CAPITAL, false));
        mDisplayParameter = DisplayParameter.getByValue(
                preferences.getInt(DISPLAY, DisplayParameter.ALPHABET.getValue())
        );
    }

    public boolean isSearchCountry() {
        return mSearchCountry;
    }

    public void setSearchCountry(boolean searchCountry, Context context) {
        writeBooleanChange(COUNTRY, searchCountry, context);
        setSearchCountry(searchCountry);

    }

    public void setSearchCapital(boolean searchCapital, Context context) {
        writeBooleanChange(CAPITAL, searchCapital, context);
        setSearchCapital(searchCapital);
    }

    private void setSearchCountry(boolean searchCountry) {
        mSearchCountry = searchCountry;
        if (searchCountry) {
            mStateManager.addSearchParameter(SearchParameter.BY_COUNTRY);
        } else {
            mStateManager.removeSearchParameter(SearchParameter.BY_COUNTRY);
        }
    }

    private void setSearchCapital(boolean searchCapital) {
        mSearchCapital = searchCapital;
        if (searchCapital) {
            mStateManager.addSearchParameter(SearchParameter.BY_CAPITAL);
        } else {
            mStateManager.removeSearchParameter(SearchParameter.BY_CAPITAL);
        }
    }

    public boolean isSearchCapital() {
        return mSearchCapital;
    }

    public DisplayParameter getDisplayParameter() {
        return mDisplayParameter;
    }

    public void setDisplayParameter(DisplayParameter displayParameter, Context context) {
        mDisplayParameter = displayParameter;
        writeIntegerChange(DISPLAY, displayParameter.getValue(), context);
    }

    private void writeBooleanChange(String key, boolean value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    private void writeIntegerChange(String key, int value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }
}
