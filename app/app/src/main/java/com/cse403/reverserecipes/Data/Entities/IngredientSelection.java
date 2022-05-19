package com.cse403.reverserecipes.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredient_selection_table",
        foreignKeys = @ForeignKey(entity = DataIngredient.class, parentColumns = "id", childColumns = "ingredient_id", onDelete = CASCADE))
public class IngredientSelection {
    @PrimaryKey
    @ColumnInfo(name = "ingredient_id")
    private final int mIngredientId;

    public IngredientSelection(int ingredientId) {
        mIngredientId = ingredientId;
    }

    public int getIngredientId() {
        return mIngredientId;
    }
}
