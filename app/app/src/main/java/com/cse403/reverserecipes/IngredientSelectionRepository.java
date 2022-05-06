package com.cse403.reverserecipes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientSelectionRepository {
    private IngredientSelectionDao mIngredientSelectionDao;
    private LiveData<List<IngredientSelection>> mAllIngredientSelections;

    // TODO: Get rid of Application dependency for testability.
    IngredientSelectionRepository(Application application) {
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mIngredientSelectionDao = db.ingredientSelectionDao();
        mAllIngredientSelections = mIngredientSelectionDao.getIngredientSelections();
    }

    public LiveData<List<IngredientSelection>> getAllIngredientSelections() { return mAllIngredientSelections; }

    void delete(IngredientSelection ingredientSelection) {
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
           mIngredientSelectionDao.delete(ingredientSelection);
        });
    }
}
