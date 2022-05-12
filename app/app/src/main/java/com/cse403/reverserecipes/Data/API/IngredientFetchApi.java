package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;

import java.util.List;

public interface IngredientFetchApi {
    public List<DataIngredient> fetchIngredients();
}
