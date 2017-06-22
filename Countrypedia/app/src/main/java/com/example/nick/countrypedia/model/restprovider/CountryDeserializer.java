package com.example.nick.countrypedia.model.restprovider;

import com.example.nick.countrypedia.view.item.Country;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class CountryDeserializer implements JsonDeserializer<Country>{

    @Override
    public Country deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jCountry = json.getAsJsonObject();

        String name = jCountry.get("name").getAsString();
        String capital = jCountry.get("capital").getAsString();
        String region = jCountry.get("region").getAsString();
        String flag = jCountry.get("flag").getAsString();

        Country country = new Country(name, capital, region, flag);

        try {
            country.setSubRegion(jCountry.get("subregion").getAsString());
            country.setPopulation(jCountry.get("population").getAsLong());
            country.setArea(jCountry.get("area").getAsLong());
            country.setCurrency(jCountry.get("currencies").getAsJsonArray()
                    .get(0).getAsJsonObject().get("name").getAsString()
            );
            country.setLanguage(jCountry.get("languages").getAsJsonArray()
                    .get(0).getAsJsonObject().get("name").getAsString()
            );
        }catch (NullPointerException exc) {
            return country;
        }
        return country;
    }
}
