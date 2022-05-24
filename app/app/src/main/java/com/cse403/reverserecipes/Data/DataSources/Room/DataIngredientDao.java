package com.cse403.reverserecipes.Data.DataSources.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;

import java.util.List;

@Dao
public interface DataIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(DataIngredient dataIngredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(List<DataIngredient> dataIngredients);

    @Delete
    void deleteIngredients(List<DataIngredient> dataIngredients);

    @Query("SELECT * FROM ingredient_table ORDER BY category, name ASC")
    LiveData<List<DataIngredient>> getIngredientsLiveData();

    @Query("SELECT * FROM ingredient_table WHERE selected = 1 ORDER BY category, name ASC")
    LiveData<List<DataIngredient>> getSelectedIngredientsLiveData();

    @Query("SELECT * FROM ingredient_table")
    List<DataIngredient> getIngredients();
}
