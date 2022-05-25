package com.cse403.reverserecipes.Domain.Mappers;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.Recipe;

public class DataRecipeToUnsavedRecipeMapper implements Mapper<DataRecipe, Recipe> {

    @Override
    public Recipe map(DataRecipe o) {
        return new Recipe(
                o.getRid(),
                o.getImage(),
                o.getLink(),
                o.getTitle(),
                o.getTotalTime(),
                o.getYields(),
                false
        );
    }
}
