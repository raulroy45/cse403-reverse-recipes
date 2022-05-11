package com.cse403.reverserecipes.Data.DataSources.Remote;

import com.cse403.reverserecipes.Data.API.IngredientApi;
import com.cse403.reverserecipes.Data.Entities.Ingredient;

import java.util.List;

public class IngredientRemoteDataSource {

    private IngredientApi mIngredientApi;

    public IngredientRemoteDataSource(IngredientApi ingredientApi) {
        mIngredientApi = ingredientApi;
    }

    public List<Ingredient> getIngredients() {
        return mIngredientApi.fetchIngredients();
    }
}
