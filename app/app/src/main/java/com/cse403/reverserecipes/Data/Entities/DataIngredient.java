package com.cse403.reverserecipes.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.cse403.reverserecipes.IngredientCategory;

@Entity(tableName = "ingredient_table")
public class DataIngredient {
    @PrimaryKey
    @ColumnInfo(name = "name")
    private final String mName;

    @ColumnInfo(name = "category")
    private final String mCategory;

    @ColumnInfo(name = "selected")
    private final boolean mSelected;

    public DataIngredient(String name, String category, boolean selected) {
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
}
