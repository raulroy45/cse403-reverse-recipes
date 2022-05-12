package com.cse403.reverserecipes.Data.DataSources.Remote;

import com.cse403.reverserecipes.Data.API.IngredientFetchApi;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;

import java.util.List;

public class IngredientRemoteDataSource {

    private IngredientFetchApi mIngredientFetchApi;

    public IngredientRemoteDataSource(IngredientFetchApi ingredientFetchApi) {
        mIngredientFetchApi = ingredientFetchApi;
    }

    public List<DataIngredient> getIngredients() {
        return mIngredientFetchApi.fetchIngredients();
    }
}
