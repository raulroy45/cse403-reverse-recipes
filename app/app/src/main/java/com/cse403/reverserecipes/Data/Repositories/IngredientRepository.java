package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.API.IngredientHttp;
import com.cse403.reverserecipes.Data.DataSources.Remote.IngredientRemoteDataSource;
import com.cse403.reverserecipes.Data.DataSources.Room.IngredientDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.Ingredient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IngredientRepository {
    private IngredientRemoteDataSource mIngredientRemoteDataSource;
    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    // TODO: Get rid of Application dependency for testability.
    IngredientRepository(Application application) {
        mIngredientRemoteDataSource = new IngredientRemoteDataSource(new IngredientHttp());
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getIngredients();
    }

    LiveData<List<Ingredient>> getAllIngredients() {
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            mIngredientDao.insert(mIngredientRemoteDataSource.getIngredients());
        });
        return mAllIngredients;
    }
}
