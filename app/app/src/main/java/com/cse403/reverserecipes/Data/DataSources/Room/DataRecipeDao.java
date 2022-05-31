package com.cse403.reverserecipes.Data.DataSources.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;

import java.util.List;

@Dao
public interface DataRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(DataRecipe dataRecipe);

    @Delete
    void deleteRecipe(DataRecipe dataRecipe);

    @Query("SELECT * FROM saved_recipe_table")
    LiveData<List<DataRecipe>> getRecipesLiveData();

    @Query("SELECT * FROM saved_recipe_table")
    List<DataRecipe> getRecipes();
}
