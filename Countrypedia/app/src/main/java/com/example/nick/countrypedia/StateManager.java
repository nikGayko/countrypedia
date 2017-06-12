package com.example.nick.countrypedia;

import android.os.Handler;
import android.os.Message;

import com.example.nick.countrypedia.restprovider.Field;
import com.example.nick.countrypedia.restprovider.Provider;

import java.util.ArrayList;

public class StateManager {

    private static StateManager mStateManager;

    private StateManager() {
        mProvider = new Provider();
    }

    public synchronized static StateManager getInstance() {
        if (mStateManager == null) {
            mStateManager = new StateManager();
        }
        return mStateManager;
    }

    private ArrayList<Country> mCountriesList;
    private Provider mProvider;

    public ArrayList<Country> getCountriesList() {
        return mCountriesList;
    }

    public void setCountriesList(ArrayList<Country> countriesList) {
        mCountriesList = countriesList;
    }

    public void updateCountryList(final Notify notify) {
        final Handler handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                ArrayList<Country> list = ((ArrayList<Country>) msg.obj);
                setCountriesList(list);
                notify.sendNotify();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Country> allCountries = mProvider.getAllCountries(Field.CAPITAL, Field.NAME, Field.REGION);
                Message message = handler.obtainMessage(1, allCountries);
                handler.sendMessage(message);
            }
        }).start();
    }

}
