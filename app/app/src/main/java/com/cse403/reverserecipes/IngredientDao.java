package com.cse403.reverserecipes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Query("SELECT * FROM ingredient_table")
    LiveData<List<Ingredient>> getIngredients();
}
