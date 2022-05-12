package com.cse403.reverserecipes.Domain.Mappers;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

public class DataIngredientToIngredientMapper implements Mapper<DataIngredient, Ingredient> {

    @Override
    public Ingredient map(DataIngredient o) {
        return new Ingredient(
                o.getName(),
                o.getCategory(),
                o.isSelected()
        );
    }
}
