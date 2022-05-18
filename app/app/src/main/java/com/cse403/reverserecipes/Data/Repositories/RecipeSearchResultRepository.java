package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cse403.reverserecipes.Data.API.RecipeSearchHttp;
import com.cse403.reverserecipes.Data.DataSources.Remote.RecipeSearchResultRemoteDataSource;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.Domain.Mappers.ArrayListMapper;
import com.cse403.reverserecipes.Domain.Mappers.DataIngredientToIngredientMapper;
import com.cse403.reverserecipes.Domain.Mappers.DataRecipeToRecipeMapper;
import com.cse403.reverserecipes.Domain.Mappers.IngredientToDataIngredientMapper;
import com.cse403.reverserecipes.Domain.Mappers.ListMapper;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

public class RecipeSearchResultRepository {

    private RecipeSearchResultRemoteDataSource mRecipeSearchResultRemoteDataSource;

    // TODO: Get rid of Application dependency for testability.
    public RecipeSearchResultRepository(Application application) {
        mRecipeSearchResultRemoteDataSource = new RecipeSearchResultRemoteDataSource(new RecipeSearchHttp());
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
    }

    public LiveData<List<Recipe>> getResultRecipes(List<Ingredient> ingredients) {
        MutableLiveData<List<Recipe>> recipeResultsLiveData = new MutableLiveData<>();

        // TODO: Don't just reuse the Room database's databaseWriteExecutor.
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            // TODO: Dependency injection for the mappers?
            ListMapper<Ingredient, DataIngredient> ingredientToDataIngredientListMapper =
                    new ArrayListMapper<>(new IngredientToDataIngredientMapper());
            ListMapper<DataRecipe, Recipe> dataRecipeToRecipeListMapper =
                    new ArrayListMapper<>(new DataRecipeToRecipeMapper());
            List<DataRecipe> recipeResults = mRecipeSearchResultRemoteDataSource
                            .getResultRecipes(ingredientToDataIngredientListMapper.map(ingredients));
            recipeResultsLiveData.postValue(dataRecipeToRecipeListMapper.map(recipeResults));
        });

        return recipeResultsLiveData;
    }
}
