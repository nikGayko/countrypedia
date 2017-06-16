package com.example.nick.countrypedia.model.cacheprovider;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nick.countrypedia.model.DisplayParameter;

public class CacheProvider {

    boolean mSearchCountry;
    boolean mSearchCapital;
    DisplayParameter mDisplayParameter;

    public void Initialization(Context context) {
        SharedPreferences init = context.getSharedPreferences("init", Context.MODE_PRIVATE);
    }

}
