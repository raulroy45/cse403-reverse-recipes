package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;

import java.util.List;

public interface ResultRecipeApi {
    public List<ResultRecipe> fetchResultRecipes(List<Ingredient> ingredients);
}
