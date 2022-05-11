package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;
import com.cse403.reverserecipes.IngredientCategory;

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

public class ResultRecipeHttp implements ResultRecipeApi {

    private static final String RESULT_RECIPE_URL = "https://reverserecipes.azurewebsites.net/recipes/";

    @Override
    public List<ResultRecipe> fetchResultRecipes(List<Ingredient> ingredients) {
        List<ResultRecipe> resultRecipeList = new ArrayList<>();

        try {
            URL url = new URL(RESULT_RECIPE_URL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");

                JSONArray ingredientNameList = new JSONArray();
                for (Ingredient i : ingredients) {
                    ingredientNameList.put(i.getName());
                }
                JSONObject ingredientNameListObject = new JSONObject();
                ingredientNameListObject.put("ingredients", ingredientNameList);

                OutputStream out = urlConnection.getOutputStream();
                out.write(ingredientNameListObject.toString().getBytes(StandardCharsets.UTF_8));
                out.close();

                int responseCode = urlConnection.getResponseCode();
                String outString = ingredientNameListObject.toString();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject resultRecipeListObject = new JSONObject(result.toString());
                JSONArray resultRecipeListArray = resultRecipeListObject.getJSONArray("recipes");
                for (int i = 0; i < resultRecipeListArray.length(); i++) {
                    JSONObject resultRecipeObject = resultRecipeListArray.getJSONObject(i);
                    int resultRecipeRid = resultRecipeObject.getInt("rid");
                    String resultRecipeImage = resultRecipeObject.getString("image");
                    String resultRecipeLink = resultRecipeObject.getString("link");
                    String resultRecipeTitle = resultRecipeObject.getString("title");
                    int resultRecipeTotalTime = resultRecipeObject.getInt("total_time");
                    int resultRecipeYields = resultRecipeObject.getInt("yields");
                    resultRecipeList.add(
                            new ResultRecipe(
                                    resultRecipeRid,
                                    resultRecipeImage,
                                    resultRecipeLink,
                                    resultRecipeTitle,
                                    resultRecipeTotalTime,
                                    resultRecipeYields));
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return resultRecipeList;
    }
}
