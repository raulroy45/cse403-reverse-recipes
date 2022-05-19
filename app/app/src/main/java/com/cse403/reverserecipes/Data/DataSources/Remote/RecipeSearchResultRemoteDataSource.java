package com.cse403.reverserecipes.Data.DataSources.Remote;

import com.cse403.reverserecipes.Data.API.RecipeSearchApi;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;

import java.util.List;

public class RecipeSearchResultRemoteDataSource {

    private RecipeSearchApi mRecipeSearchApi;

    public RecipeSearchResultRemoteDataSource(RecipeSearchApi recipeSearchApi) {
        mRecipeSearchApi = recipeSearchApi;
    }

    public List<DataRecipe> getResultRecipes(List<DataIngredient> dataIngredients) {
        return mRecipeSearchApi.fetchResultRecipes(dataIngredients);
    }
}
