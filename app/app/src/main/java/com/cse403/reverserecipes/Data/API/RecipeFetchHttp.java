package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;

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

public class RecipeFetchHttp implements RecipeFetchApi {

    private static final String ENDPOINT_URL = "https://reverserecipes.azurewebsites.net/recipes/%d/";
    private static final int CONNECT_TIMEOUT = 10000;
    
    @Override
    public DataRecipe fetchRecipe(int rid) {
        DataRecipe fetchedRecipe = null;

        // Fetch recipe from Reverse Recipes web server using GET request.
        try {
            URL url = new URL(String.format(ENDPOINT_URL, rid));
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
                urlConnection.connect();

                // Fetch the data and collect the response body.
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Convert fetched data to DataIngredient form.
                JSONObject fetchedRecipeObject = new JSONObject(result.toString());
                fetchedRecipe =
                        new DataRecipe(
                                fetchedRecipeObject.getInt("rid"),
                                fetchedRecipeObject.getString("image"),
                                fetchedRecipeObject.getString("link"),
                                fetchedRecipeObject.getString("title"),
                                fetchedRecipeObject.getInt("total_time"),
                                fetchedRecipeObject.getInt("yields"));
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            // TODO: Do something more graceful here, e.g., prompt the UI to inform the user of
            //  a network fetch failure.
            e.printStackTrace();
        }

        return fetchedRecipe;
    }
}
