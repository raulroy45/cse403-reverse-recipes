package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.DataSources.Room.IngredientDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.Ingredient;

import java.util.List;

public class IngredientRepository {
    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    // TODO: Get rid of Application dependency for testability.
    IngredientRepository(Application application) {
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getIngredients();
    }

    LiveData<List<Ingredient>> getAllIngredients() { return mAllIngredients; }
}
