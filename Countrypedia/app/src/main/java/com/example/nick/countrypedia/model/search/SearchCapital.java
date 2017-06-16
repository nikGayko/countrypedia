package com.example.nick.countrypedia.model.search;

import com.example.nick.countrypedia.Country;

import java.util.ArrayList;

public class SearchCapital implements Search {

    @Override
    public ArrayList<Country> search(ArrayList<Country> countriesList, String searchQuery) {
        int start = -1, z = 0;
        ArrayList<Country> countries = new ArrayList<>();

        for (z = 0; z < countriesList.size(); z++) {
            Country country = countriesList.get(z);

            if (country.getCapital().startsWith(searchQuery)) {
                countries.add(country);
            }
        }

        return countries;
    }
}
