package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;
import android.os.SystemClock;

import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.API.IngredientHttp;
import com.cse403.reverserecipes.Data.API.ResultRecipeHttp;
import com.cse403.reverserecipes.Data.DataSources.Remote.IngredientRemoteDataSource;
import com.cse403.reverserecipes.Data.DataSources.Remote.ResultRecipeRemoteDataSource;
import com.cse403.reverserecipes.Data.DataSources.Room.IngredientDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ResultRecipeDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;
import com.cse403.reverserecipes.IngredientCategory;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultRecipeRepository {
    private ResultRecipeRemoteDataSource mResultRecipeRemoteDataSource;
    private IngredientRepository mIngredientRepository;
    private IngredientSelectionRepository mIngredientSelectionRepository;
    private ResultRecipeDao mResultRecipeDao;
    private LiveData<List<ResultRecipe>> mAllResultRecipes;

    // TODO: Get rid of Application dependency for testability.
    public ResultRecipeRepository(Application application) {
        mResultRecipeRemoteDataSource = new ResultRecipeRemoteDataSource(new ResultRecipeHttp());
        mIngredientRepository = new IngredientRepository(application);
        mIngredientSelectionRepository = new IngredientSelectionRepository(application);
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mResultRecipeDao = db.resultRecipeDao();
        mAllResultRecipes = mResultRecipeDao.getResultRecipes();
    }

    public LiveData<List<ResultRecipe>> getAllResultRecipes() {
        return mAllResultRecipes;
    }

    public void updateResultRecipes(List<Ingredient> selectedIngredients) {
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            mResultRecipeDao.deleteAll();
            List<ResultRecipe> resultRecipes = mResultRecipeRemoteDataSource.getResultRecipes(selectedIngredients);
            mResultRecipeDao.insert(resultRecipes);
        });
    }
}
