package com.cse403.reverserecipes.Domain.Mappers;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

public class IngredientToDataIngredientMapper implements Mapper<Ingredient, DataIngredient> {
    @Override
    public DataIngredient map(Ingredient o) {
        return new DataIngredient(
                o.getName(),
                o.getCategory(),
                o.isSelected()
        );
    }
}
