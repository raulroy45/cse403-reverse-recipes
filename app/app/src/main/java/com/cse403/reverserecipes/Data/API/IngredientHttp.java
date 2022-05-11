package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.IngredientCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class IngredientHttp implements IngredientApi {

    private static final String INGREDIENT_URL = "https://reverserecipes.azurewebsites.net/ingredients/";
    
    @Override
    public List<Ingredient> fetchIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>();

        try {
            URL url = new URL(INGREDIENT_URL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject ingredientListObject = new JSONObject(result.toString());
                JSONArray ingredientListArray = ingredientListObject.getJSONArray("ingredients");
                for (int i = 0; i < ingredientListArray.length(); i++) {
                    JSONObject ingredientObject = ingredientListArray.getJSONObject(i);
                    String ingredientCategory = ingredientObject.getString("category");
                    String ingredientName = ingredientObject.getString("name");
                    IngredientCategory category = IngredientCategory.FRUIT;
                    switch (i % 6) {
                        case 0:
                            category = IngredientCategory.FRUIT;
                            break;
                        case 1:
                            category = IngredientCategory.VEGETABLE;
                            break;
                        case 2:
                            category = IngredientCategory.PROTEIN;
                            break;
                        case 3:
                            category = IngredientCategory.DAIRY;
                            break;
                        case 4:
                            category = IngredientCategory.GRAIN;
                            break;
                        case 5:
                            category = IngredientCategory.LEGUME;
                            break;
                    }
                    ingredientList.add(new Ingredient(i, ingredientName, category));
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return ingredientList;
    }
}
