package com.cse403.reverserecipes;

import java.util.List;

public class ViewIngredientCategory {
    private IngredientCategory mIngredientCategory;
    private List<ViewIngredient> mIngredientList;

    public ViewIngredientCategory(IngredientCategory ingredientCategory, List<ViewIngredient> ingredientList) {
        mIngredientCategory = ingredientCategory;
        mIngredientList = ingredientList;
    }

    public IngredientCategory getIngredientCategory() {
        return mIngredientCategory;
    }

    public List<ViewIngredient> getIngredientList() {
        return mIngredientList;
    }
}
