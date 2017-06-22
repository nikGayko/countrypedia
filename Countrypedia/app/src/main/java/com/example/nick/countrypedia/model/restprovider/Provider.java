package com.example.nick.countrypedia.model.restprovider;

import com.example.nick.countrypedia.model.Predicate;
import com.example.nick.countrypedia.view.item.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Provider {

    private final String ALL = "https://restcountries.eu/rest/v2/all";
    private final String COUNTRY_NAME = "https://restcountries.eu/rest/v2/name/";
    private final String COUNTRY_CODE = "https://restcountries.eu/rest/v2/alpha/";

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

    public Country getCountry(String countryName) {
        String url = COUNTRY_NAME + countryName.replaceAll(" ", "%20") + "?fullText=true";

        HttpClient httpClient = new HttpClient();
        httpClient.execute(url);
        try {
            String json = httpClient.get();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Country.class, new CountryDeserializer())
                    .create();
            return parseCountryJSON(gson, new JSONArray(json).getJSONObject(0));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Country getCountry(String countryCode, Field... fields) {
        String url = COUNTRY_CODE + countryCode + getFieldsRequest(fields);

        HttpClient httpClient = new HttpClient();
        httpClient.execute(url);
        try {
            String json = httpClient.get();
            return parseCountryJSON(new Gson(), new JSONObject(json));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Country load(String url, GsonBuilder gsonBuilder) {
        HttpClient httpClient = new HttpClient();
        httpClient.execute(url);
        try {
            String json = httpClient.get();
            Gson gson = gsonBuilder.create();
            return parseCountryJSON(gson, new JSONArray(json).getJSONObject(0));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
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

    private ArrayList<Country> parseCountryJSONArray(final Gson gson, final JSONArray jsonArray, final Predicate<Country> predicate)
            throws JSONException, ExecutionException, InterruptedException {
        ArrayList<Country> countries = new ArrayList<>();
        for (int z = 0; z < jsonArray.length(); z++) {
            final JSONObject jCountry = jsonArray.getJSONObject(z);
            final Country country = parseCountryJSON(gson, jCountry);

            if (predicate.apply(country)) {
                countries.add(country);
            }

        }
        return countries;
    }

    private Country parseCountryJSON(Gson gson, JSONObject jsonObject) {
        String string = jsonObject.toString();
        return gson.fromJson(string, Country.class);
    }
}
