package com.cse403.reverserecipes.UI.Entities;

public class Ingredient {
    private String mName;
    private String mCategory;
    private boolean mSelected;

    public Ingredient(String name, String category, boolean selected) {
        mName = name;
        mCategory = category;
        mSelected = selected;
    }

    public String getName() {
        return mName;
    }

    public String getCategory() {
        return mCategory;
    }

    public boolean isSelected() {
        return mSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ingredient) {
            Ingredient other = (Ingredient) o;
            return
                    this.mName.equals(other.mName) &&
                    this.mCategory.equals(other.mCategory) &&
                    this.mSelected == other.mSelected;
        } else {
            return false;
        }
    }
}
