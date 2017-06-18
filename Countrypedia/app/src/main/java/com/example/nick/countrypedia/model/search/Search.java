package com.example.nick.countrypedia.model.search;

import com.example.nick.countrypedia.Country;

import java.util.ArrayList;
import java.util.HashSet;

public interface Search {
    public ArrayList<Country> search(ArrayList<Country> list, String searchQuery);
}
