package com.cse403.reverserecipes.Domain.Mappers;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.UI.Entities.Recipe;

public class RecipeToDataRecipeMapper implements Mapper<Recipe, DataRecipe> {

    @Override
    public DataRecipe map(Recipe o) {
        return new DataRecipe(
                o.getRid(),
                o.getImage(),
                o.getLink(),
                o.getTitle(),
                o.getTotalTime(),
                o.getYields(),
                o.getIngredients()
        );
    }
}
