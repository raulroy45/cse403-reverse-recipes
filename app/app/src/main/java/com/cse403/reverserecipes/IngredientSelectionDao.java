package com.cse403.reverserecipes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientSelectionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(IngredientSelection ingredientSelection);

    @Delete
    void delete(IngredientSelection ingredientSelection);

    @Query("SELECT * FROM ingredient_selection_table")
    LiveData<List<IngredientSelection>> getIngredientSelections();
}
