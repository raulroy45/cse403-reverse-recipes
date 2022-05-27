package com.cse403.reverserecipes.Data.API;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;

import java.util.List;

public interface RecipeFetchApi {
    public DataRecipe fetchRecipe(int rid);
}
