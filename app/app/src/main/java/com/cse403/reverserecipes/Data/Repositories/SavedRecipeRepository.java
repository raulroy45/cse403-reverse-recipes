package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.cse403.reverserecipes.Data.API.RecipeFetchHttp;
import com.cse403.reverserecipes.Data.DataSources.Remote.RecipeRemoteDataSource;
import com.cse403.reverserecipes.Data.DataSources.Room.DataRecipeDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.Domain.Mappers.ArrayListMapper;
import com.cse403.reverserecipes.Domain.Mappers.DataRecipeToSavedRecipeMapper;
import com.cse403.reverserecipes.Domain.Mappers.DataRecipeToUnsavedRecipeMapper;
import com.cse403.reverserecipes.Domain.Mappers.ListMapper;
import com.cse403.reverserecipes.Domain.Mappers.RecipeToDataRecipeMapper;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

public class SavedRecipeRepository {

    private final RecipeRemoteDataSource mRecipeRemoteDataSource;
    private final DataRecipeDao mDataRecipeDao;

    private final MediatorLiveData<List<Recipe>> mSavedRecipesLiveData;

    // TODO: Get rid of Application dependency for testability.
    public SavedRecipeRepository(Application application) {
        mRecipeRemoteDataSource = new RecipeRemoteDataSource(new RecipeFetchHttp());
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mDataRecipeDao = db.recipeDao();

        LiveData<List<DataRecipe>> dataSavedRecipesLiveData = mDataRecipeDao.getRecipesLiveData();

        // TODO: Dependency injection for the mapper?
        ListMapper<DataRecipe, Recipe> dataRecipeToRecipeListMapper =
                new ArrayListMapper<>(new DataRecipeToSavedRecipeMapper());
        mSavedRecipesLiveData = new MediatorLiveData<>();
        mSavedRecipesLiveData.addSource(
                dataSavedRecipesLiveData,
                dataIngredients -> mSavedRecipesLiveData.postValue(dataRecipeToRecipeListMapper.map(dataIngredients))
        );
    }

    public LiveData<List<Recipe>> getSavedRecipes() {
        executeRecipeFetch();

        // Return the ingredients.
        return mSavedRecipesLiveData;
    }

    public void insertSavedRecipe(Recipe recipe) {
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            // TODO: Dependency injection for the mapper?
            mDataRecipeDao.insertRecipe(new RecipeToDataRecipeMapper().map(recipe));
        });
    }

    public void deleteSavedRecipe(Recipe recipe) {
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            // TODO: Dependency injection for the mapper?
            mDataRecipeDao.deleteRecipe(new RecipeToDataRecipeMapper().map(recipe));
        });
    }

    private void executeRecipeFetch() {
        // Update saved recipe data using a web fetch.
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<DataRecipe> currentSavedRecipes = mDataRecipeDao.getRecipes();

            for (DataRecipe currentRecipe : currentSavedRecipes) {
                DataRecipe fetchedRecipe = mRecipeRemoteDataSource.getRecipe(currentRecipe.getRid());

                // Replace recipe with remote if remote exists.
                if (fetchedRecipe != null) {
                    mDataRecipeDao.insertRecipe(fetchedRecipe);
                }
            }
        });
    }
}
