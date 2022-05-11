package com.cse403.reverserecipes.Data.DataSources.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;

import java.util.List;

@Dao
public interface DataRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DataRecipe dataRecipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DataRecipe> dataRecipe);

    @Query("DELETE FROM DataRecipe")
    void deleteAll();

    @Query("SELECT * FROM DataRecipe")
    LiveData<List<DataRecipe>> getResultRecipes();
}
