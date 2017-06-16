package com.example.nick.countrypedia.model.search;

import com.example.nick.countrypedia.Country;

import java.util.ArrayList;

public class SearchCountry implements Search{

    public ArrayList<Country> search(ArrayList<Country> countriesList, String searchQuery) {
        int start = -1, z = 0;

        for (z = 0; z < countriesList.size(); z++) {
            Country country = countriesList.get(z);

            if(start == -1 && country.getName().startsWith(searchQuery)) {
                start = z;
            }
            if(start != -1 && !country.getName().startsWith(searchQuery)) {
                break;
            }
        }
        if(start == -1) {
            return new ArrayList<>();
        } else {
            return new ArrayList<Country>(countriesList.subList(start, z));
        }
    }
}
