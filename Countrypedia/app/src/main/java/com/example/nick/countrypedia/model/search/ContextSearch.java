package com.example.nick.countrypedia.model.search;

import com.example.nick.countrypedia.view.item.Country;
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
        if (mSearchList.size() > 1) {
            ArrayList<Country> arrayList = new ArrayList<>();
            for (Search search :
                    mSearchList) {
                ArrayList<Country> searchResult = search.search(list, searchQuery);
                for (Country country :
                        searchResult) {
                    if (!arrayList.contains(country)) {
                        arrayList.add(country);
                    }
                }
            }
            return arrayList;
        } else {
            return mSearchList.iterator().next().search(list, searchQuery);
        }
    }
    
}
