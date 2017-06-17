package com.example.nick.countrypedia.model.search;

import com.example.nick.countrypedia.Country;
import com.example.nick.countrypedia.model.parameter.SearchParameter;

import java.util.ArrayList;
import java.util.HashSet;

public class ContextSearch implements Search {

    private HashSet<Search> mSearchList;
    private SearchCapital mSearchCapital;
    private SearchCountry mSearchCountry;

    public ContextSearch() {
        mSearchList = new HashSet<>();
        mSearchCapital = new SearchCapital();
        mSearchCountry = new SearchCountry();
    }

    public void addSearch(SearchParameter searchParameter) {
        mSearchList.add(getSearchInstance(searchParameter));
    }

    public void removeSearch(SearchParameter searchParameter) {
        mSearchList.remove(getSearchInstance(searchParameter));
    }

    private Search getSearchInstance(SearchParameter searchParameter) {
        switch (searchParameter) {
            case BY_CAPITAL:
                return mSearchCapital;
            case BY_COUNTRY:
                return mSearchCountry;
            default:
                return null;
        }
    }

    @Override
    public ArrayList<Country> search(ArrayList<Country> list, String searchQuery) {
        ArrayList<Country> countryList = new ArrayList<>();
        for (Search search :
                mSearchList) {
            countryList.addAll(search.search(list, searchQuery));
        }
        return countryList;
    }
}
