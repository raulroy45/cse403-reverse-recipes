package com.cse403.reverserecipes.Data.DataSources.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;

import java.util.List;

@Dao
public interface ResultRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResultRecipe resultRecipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ResultRecipe> resultRecipe);

    @Query("DELETE FROM result_recipe_table")
    void deleteAll();

    @Query("SELECT * FROM result_recipe_table")
    LiveData<List<ResultRecipe>> getResultRecipes();
}
