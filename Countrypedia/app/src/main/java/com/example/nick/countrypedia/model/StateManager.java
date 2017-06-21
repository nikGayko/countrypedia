package com.example.nick.countrypedia.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nick.countrypedia.NotifyList;
import com.example.nick.countrypedia.model.parameter.DisplayParameter;
import com.example.nick.countrypedia.model.parameter.SearchParameter;
import com.example.nick.countrypedia.model.restprovider.Field;
import com.example.nick.countrypedia.model.restprovider.Provider;
import com.example.nick.countrypedia.model.search.ContextSearch;
import com.example.nick.countrypedia.model.search.Search;
import com.example.nick.countrypedia.view.item.Country;
import com.example.nick.countrypedia.view.item.Group;
import com.example.nick.countrypedia.view.item.ListItem;

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
    private ArrayList<ListItem> mAlphabetList;
    private ArrayList<ListItem> mRegionList;

    private Provider mProvider;
    private ContextSearch mContextSearch;
    private NotifyList mListUpdate;

    private Predicate<Country> mCountryPredicate = new Predicate<Country>() {
        @Override
        public boolean apply(Country t1) {
            return !t1.getCapital().equals("");
        }
    };

    public ArrayList<Country> getCountriesList() {
        return mCountriesList;
    }

    public void setCountriesList(ArrayList<Country> countriesList) {
        mCountriesList = countriesList;
    }


    public void updateCountryList(final NotifyList notifyList) {
        mListUpdate = notifyList;
        final Handler handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                ArrayList<Country> list = ((ArrayList<Country>) msg.obj);
                sortByName(list);
                setCountriesList(list);
                displayListUpdated(SettingsManager.getInstance().getDisplayParameter());
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                try {
                    ArrayList<Country> allCountries = mProvider.getAllCountries(
                            mCountryPredicate, Field.CAPITAL, Field.NAME, Field.REGION, Field.FLAG);
                    message = handler.obtainMessage(1, allCountries);
                } catch (IllegalStateException exc) {
                    message = handler.obtainMessage(1, mCountriesList);
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    public void sortByName(ArrayList<Country> countries) {
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void displayListUpdated(DisplayParameter displayParameter) {
        switch (displayParameter) {
            case ALPHABET:
                mListUpdate.sendNotify(getAlphabetList());
                break;
            case REGION:
                mListUpdate.sendNotify(getRegionList());
                break;
            default:
                mListUpdate.sendNotify(new ArrayList<ListItem>());
                break;
        }
    }

    private ArrayList<ListItem> getAlphabetList() {
        if (mAlphabetList == null) {
            mAlphabetList = new ArrayList<>();
            String currentGroupName = null;
            for (Country country :
                    mCountriesList) {

                String alphabetGroup = country.getName().substring(0, 1);

                if (currentGroupName == null ||
                        !currentGroupName.equals(alphabetGroup)) {
                    currentGroupName = alphabetGroup;
                    mAlphabetList.add(new Group(alphabetGroup));
                }

                mAlphabetList.add(country);
            }
        }
        return mAlphabetList;
    }

    private ArrayList<ListItem> getRegionList() {
        if (mRegionList == null) {
            mRegionList = new ArrayList<>();

            mRegionList.add(new Group("Africa"));
            mRegionList.add(new Group("America"));
            mRegionList.add(new Group("Asia"));
            mRegionList.add(new Group("Europe"));
            mRegionList.add(new Group("Oceania"));

            int africa = 1, america = 1, asia = 1, europe = 1;
            for (int z = 0; z < mCountriesList.size(); z++) {
                Country country = mCountriesList.get(z);
                switch (country.getRegion()) {
                    case "Africa":
                        mRegionList.add(africa, country);
                        africa++;
                        break;
                    case "Americas":
                        mRegionList.add(africa + america, country);
                        america++;
                        break;
                    case "Asia":
                        mRegionList.add(africa + america + asia, country);
                        asia++;
                        break;
                    case "Europe":
                        mRegionList.add(africa + america + asia + europe, country);
                        europe++;
                        break;
                    case "Oceania":
                        mRegionList.add(country);
                        break;
                    default:
                        Log.d("TAG", "wtf?");
                }
            }
        }
        return mRegionList;
    }

    void addSearchParameter(SearchParameter searchParameter) {
        mContextSearch.addSearch(searchParameter);
    }

    void removeSearchParameter(SearchParameter searchParameter) {
        mContextSearch.removeSearch(searchParameter);
    }

    @Override
    public ArrayList<Country> search(ArrayList<Country> list, String searchQuery) {
        return mContextSearch.search(list, searchQuery);
    }


}
