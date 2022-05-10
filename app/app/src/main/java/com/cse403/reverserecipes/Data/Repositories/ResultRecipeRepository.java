package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.DataSources.Room.IngredientDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ResultRecipeDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;

import java.util.List;

public class ResultRecipeRepository {
    private ResultRecipeDao mResultRecipeDao;
    private LiveData<List<ResultRecipe>> mAllResultRecipes;

    // TODO: Get rid of Application dependency for testability.
    public ResultRecipeRepository(Application application) {
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mResultRecipeDao = db.resultRecipeDao();
        mAllResultRecipes = mResultRecipeDao.getResultRecipes();
    }

    public LiveData<List<ResultRecipe>> getAllResultRecipes() { return mAllResultRecipes; }
}
