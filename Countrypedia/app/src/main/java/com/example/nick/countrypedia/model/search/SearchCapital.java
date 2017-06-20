package com.example.nick.countrypedia.model.search;

import com.example.nick.countrypedia.view.item.Country;

import java.util.ArrayList;

public class SearchCapital implements Search {

    @Override
    public ArrayList<Country> search(ArrayList<Country> countriesList, String searchQuery) {
        ArrayList<Country> countries = new ArrayList<>();

        for (int z = 0; z < countriesList.size(); z++) {
            Country country = countriesList.get(z);

            if (country.getCapital().startsWith(searchQuery)) {
                countries.add(country);
            }
        }

        return countries;
    }
}
