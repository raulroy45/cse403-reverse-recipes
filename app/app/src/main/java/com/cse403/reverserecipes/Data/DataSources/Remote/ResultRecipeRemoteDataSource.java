package com.cse403.reverserecipes.Data.DataSources.Remote;

import com.cse403.reverserecipes.Data.API.IngredientApi;
import com.cse403.reverserecipes.Data.API.ResultRecipeApi;
import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;

import java.util.List;

public class ResultRecipeRemoteDataSource {

    private ResultRecipeApi mResultRecipeApi;

    public ResultRecipeRemoteDataSource(ResultRecipeApi resultRecipeApi) {
        mResultRecipeApi = resultRecipeApi;
    }

    public List<ResultRecipe> getResultRecipes(List<Ingredient> ingredients) {
        return mResultRecipeApi.fetchResultRecipes(ingredients);
    }
}
