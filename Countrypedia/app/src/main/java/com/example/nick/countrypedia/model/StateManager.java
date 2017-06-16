package com.example.nick.countrypedia.model;

import android.os.Handler;
import android.os.Message;

import com.example.nick.countrypedia.Country;
import com.example.nick.countrypedia.Notify;
import com.example.nick.countrypedia.model.cacheprovider.CacheProvider;
import com.example.nick.countrypedia.model.restprovider.Field;
import com.example.nick.countrypedia.model.restprovider.Provider;
import com.example.nick.countrypedia.model.search.ContextSearch;
import com.example.nick.countrypedia.model.search.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StateManager implements Search {

    private static StateManager mStateManager;

    private StateManager() {
        mProvider = new Provider();
        mContextSearch = new ContextSearch();
    }

    public synchronized static StateManager getInstance() {
        if (mStateManager == null) {
            mStateManager = new StateManager();
        }
        return mStateManager;
    }

    private ArrayList<Country> mCountriesList;
    private Provider mProvider;
    private ContextSearch mContextSearch;

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
                Message message;
                try {
                    ArrayList<Country> allCountries = mProvider.getAllCountries(Field.CAPITAL, Field.NAME, Field.REGION);
                    message = handler.obtainMessage(1, allCountries);
                } catch (IllegalStateException exc) {
                    message = handler.obtainMessage(1, mCountriesList);
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    public void sortByName() {
        Collections.sort(mCountriesList, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void addSearchParameter(SearchParameter searchParameter) {
        mContextSearch.addSearch(searchParameter);
    }

    public void removeSearchParameter(SearchParameter searchParameter) {
        mContextSearch.removeSearch(searchParameter);
    }

    @Override
    public ArrayList<Country> search(ArrayList<Country> list, String searchQuery) {
        return mContextSearch.search(list, searchQuery);
    }


}
