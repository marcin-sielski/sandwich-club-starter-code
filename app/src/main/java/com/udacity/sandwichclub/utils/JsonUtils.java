package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject jsonName = jsonSandwich.getJSONObject("name");
            String mainName = jsonName.getString("mainName");
            JSONArray jsonAlsoKnownAs = jsonName.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int index = 0; index < jsonAlsoKnownAs.length(); index++) {
                alsoKnownAs.add(jsonAlsoKnownAs.getString(index));
            }
            String placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            String description = jsonSandwich.getString("description");
            String image = jsonSandwich.getString("image");
            JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int index = 0; index < jsonIngredients.length(); index ++) {
                ingredients.add(jsonIngredients.getString(index));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image,
                    ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
