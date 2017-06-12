package com.example.nick.countrypedia.restprovider;

import android.util.Log;

import com.example.nick.countrypedia.Country;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Provider {

    private final String ALL = "https://restcountries.eu/rest/v2/all";

    private HttpClient mHttpClient;

    public Provider() {
        mHttpClient = new HttpClient();
    }

    public ArrayList<Country> getAllCountries(Field... fields) {
        ArrayList<Country> countries = null;

        mHttpClient.execute(ALL + getFieldsRequest(fields));
        try {
            String json = mHttpClient.get();
            JSONArray jsonArray = new JSONArray(json);
            countries = parseCountryJSONArray(new Gson(), jsonArray);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return countries;
    }

    private String getFieldsRequest(Field fields[]) {
        String fieldsRequest = "?fields=";
        for (Field field :
                fields) {
            fieldsRequest += field.getValue() + ";";
        }
        if (fieldsRequest.equals("?fields=")) {
            return "";
        } else {
            return fieldsRequest;
        }
    }

    private ArrayList<Country> parseCountryJSONArray(Gson gson, JSONArray jsonArray) throws JSONException {
        ArrayList<Country> countries = new ArrayList<>();
        for (int z = 0; z < jsonArray.length(); z++) {
            Country country = parseCountryJSON(gson, jsonArray.getJSONObject(z));
            countries.add(country);
        }
        return countries;
    }

    private Country parseCountryJSON(Gson gson, JSONObject jsonObject) {
        String string = jsonObject.toString();
        return gson.fromJson(string, Country.class);
    }
}
