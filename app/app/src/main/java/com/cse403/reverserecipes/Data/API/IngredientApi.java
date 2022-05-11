package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.Ingredient;

import java.util.List;

public interface IngredientApi {
    public List<Ingredient> fetchIngredients();
}
