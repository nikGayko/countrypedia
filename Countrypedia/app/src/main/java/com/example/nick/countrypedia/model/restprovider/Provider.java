package com.example.nick.countrypedia.model.restprovider;

import android.graphics.Bitmap;

import com.example.nick.countrypedia.model.Predicate;
import com.example.nick.countrypedia.view.item.Country;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Provider {

    private final String ALL = "https://restcountries.eu/rest/v2/all";

    public ArrayList<Country> getAllCountries(Predicate<Country> predicate, Field... fields) {
        ArrayList<Country> countries = null;

        HttpClient httpClient = new HttpClient();
        httpClient.execute(ALL + getFieldsRequest(fields));
        try {
            String json = httpClient.get();
            JSONArray jsonArray = new JSONArray(json);
            countries = parseCountryJSONArray(new Gson(), jsonArray, predicate);
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

    private ArrayList<Country> parseCountryJSONArray(Gson gson, JSONArray jsonArray, Predicate<Country> predicate) throws JSONException, ExecutionException, InterruptedException {
        ArrayList<Country> countries = new ArrayList<>();
        for (int z = 0; z < jsonArray.length(); z++) {
            JSONObject jCountry = jsonArray.getJSONObject(z);
            Country country = parseCountryJSON(gson, jCountry);

            if (predicate.apply(country)) {
                countries.add(country);
            } else {
                continue;
            }

            ImageLoader imageLoader = new ImageLoader();
            imageLoader.execute(jCountry.getString("flag"));
            Bitmap bitmap = imageLoader.get();
            country.setFlag(bitmap);
        }
        return countries;
    }

    private Country parseCountryJSON(Gson gson, JSONObject jsonObject) {
        String string = jsonObject.toString();
        return gson.fromJson(string, Country.class);
    }
}
