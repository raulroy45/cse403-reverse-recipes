package com.cse403.reverserecipes.Domain.Mappers;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.UI.Entities.Recipe;

public class DataRecipeToSavedRecipeMapper implements Mapper<DataRecipe, Recipe> {

    @Override
    public Recipe map(DataRecipe o) {
        return new Recipe(
                o.getRid(),
                o.getImage(),
                o.getLink(),
                o.getTitle(),
                o.getTotalTime(),
                o.getYields(),
                o.getIngredients(),
                o.getInstructions(),
                true
        );
    }
}
