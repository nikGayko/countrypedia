package com.example.nick.countrypedia.model.restprovider;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;

import com.caverock.androidsvg.SVG;
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
    private final String COUNTRY = "https://restcountries.eu/rest/v2/name/";

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
        String url = COUNTRY + countryName.replaceAll(" ", "%20") + "?fullText=true";

        HttpClient httpClient = new HttpClient();
        httpClient.execute(url);
        try {
            String json = httpClient.get();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Country.class, new CountryDeserializer())
                    .create();
            Country country = parseCountryJSON(gson, new JSONArray(json).getJSONObject(0));
            return country;
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

    public void loadCountryFlag(final Country country, final Handler handler, final  int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageLoader imageLoader = new ImageLoader();
                try {
                    imageLoader.execute(country.getFlag());
                    SVG svg = imageLoader.get();
                    Drawable drawable = new PictureDrawable(svg.renderToPicture());
                    Bitmap bitmap = convertToBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    handler.obtainMessage(position, bitmap).sendToTarget();

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Country parseCountryJSON(Gson gson, JSONObject jsonObject) {
        String string = jsonObject.toString();
        return gson.fromJson(string, Country.class);
    }

    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }
}
