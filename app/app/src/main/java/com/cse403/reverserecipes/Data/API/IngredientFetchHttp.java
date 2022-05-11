package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class IngredientFetchHttp implements IngredientFetchApi {

    private static final String ENDPOINT_URL = "https://reverserecipes.azurewebsites.net/ingredients/";
    
    @Override
    public List<DataIngredient> fetchIngredients() {
        List<DataIngredient> fetchedIngredients = new ArrayList<>();

        // Fetch ingredients from Reverse Recipes web server using GET request.
        try {
            URL url = new URL(ENDPOINT_URL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                // Fetch the data and collect the response body.
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Convert fetched data to DataIngredient form.
                JSONObject fetchedIngredientsObject = new JSONObject(result.toString());
                JSONArray fetchedIngredientsArray = fetchedIngredientsObject.getJSONArray("ingredients");
                for (int i = 0; i < fetchedIngredientsArray.length(); i++) {
                    // We consider ingredients to not be selected by default.
                    JSONObject fetchedIngredientObject = fetchedIngredientsArray.getJSONObject(i);
                    fetchedIngredients.add(
                            new DataIngredient(
                                    fetchedIngredientObject.getString("name"),
                                    fetchedIngredientObject.getString("category"),
                                    false));
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            // TODO: Do something more graceful here, e.g., prompt the UI to inform the user of
            //  a network fetch failure.
            e.printStackTrace();
        }

        return fetchedIngredients;
    }
}
