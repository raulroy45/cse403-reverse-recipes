package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class RecipeSearchHttp implements RecipeSearchApi {

    private static final String ENDPOINT_URL = "https://reverserecipes.azurewebsites.net/recipes/";
    private static final int CONNECT_TIMEOUT = 10000;

    @Override
    public List<DataRecipe> fetchResultRecipes(List<DataIngredient> dataIngredients) {
        List<DataRecipe> dataRecipes = new ArrayList<>();

        // Fetch recipes from Reverse Recipes web server using POST request.
        try {
            URL url = new URL(ENDPOINT_URL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
                urlConnection.connect();

                // Generate the request body.
                JSONArray ingredientNamesArray = new JSONArray();
                for (DataIngredient i : dataIngredients) {
                    ingredientNamesArray.put(i.getName());
                }
                JSONObject ingredientNamesObject = new JSONObject();
                ingredientNamesObject.put("ingredients", ingredientNamesArray);

                // Write the request body to the connection.
                OutputStream out = urlConnection.getOutputStream();
                out.write(ingredientNamesObject.toString().getBytes(StandardCharsets.UTF_8));
                out.close();

                // Fetch the data and collect the response body.
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Convert fetched data to DataRecipe form.
                JSONObject fetchedRecipesObject = new JSONObject(result.toString());
                JSONArray fetchedRecipesArray = fetchedRecipesObject.getJSONArray("recipes");
                for (int i = 0; i < fetchedRecipesArray.length(); i++) {
                    JSONObject fetchedRecipeObject = fetchedRecipesArray.getJSONObject(i);
                    dataRecipes.add(
                            new DataRecipe(
                                    fetchedRecipeObject.getInt("rid"),
                                    fetchedRecipeObject.getString("image"),
                                    fetchedRecipeObject.getString("link"),
                                    fetchedRecipeObject.getString("title"),
                                    fetchedRecipeObject.getInt("total_time"),
                                    fetchedRecipeObject.getInt("yields")));
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            // TODO: Do something more graceful here, e.g., prompt the UI to inform the user of
            //  a network fetch failure.
            e.printStackTrace();
        }

        return dataRecipes;
    }
}
