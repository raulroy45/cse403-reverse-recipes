package com.cse403.reverserecipes.Data.DataSources.Remote;

import com.cse403.reverserecipes.Data.API.IngredientFetchApi;
import com.cse403.reverserecipes.Data.API.RecipeFetchApi;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;

import java.util.List;

public class RecipeRemoteDataSource {

    private RecipeFetchApi mRecipeFetchApi;

    public RecipeRemoteDataSource(RecipeFetchApi recipeFetchApi) {
        mRecipeFetchApi = recipeFetchApi;
    }

    public DataRecipe getRecipe(int rid) {
        return mRecipeFetchApi.fetchRecipe(rid);
    }
}
