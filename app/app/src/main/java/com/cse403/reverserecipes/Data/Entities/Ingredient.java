package com.cse403.reverserecipes.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.cse403.reverserecipes.IngredientCategory;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int mId;

    @ColumnInfo(name = "name")
    private final String mName;

    @ColumnInfo(name = "category")
    private final IngredientCategory mCategory;

    public Ingredient(int id, String name, IngredientCategory category) {
        mId = id;
        mName = name;
        mCategory = category;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public IngredientCategory getCategory() {
        return mCategory;
    }
}
