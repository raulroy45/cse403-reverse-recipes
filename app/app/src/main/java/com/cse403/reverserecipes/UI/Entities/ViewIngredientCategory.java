package com.cse403.reverserecipes.UI.Entities;

import com.cse403.reverserecipes.IngredientCategory;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof ViewIngredientCategory) {
            ViewIngredientCategory other = (ViewIngredientCategory) o;
            return
                    this.mIngredientCategory == other.mIngredientCategory &&
                    this.mIngredientList.equals(other.mIngredientList);
        } else {
            return false;
        }
    }
}
