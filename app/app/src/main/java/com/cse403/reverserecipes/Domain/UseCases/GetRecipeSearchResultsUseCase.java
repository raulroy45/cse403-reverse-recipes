package com.cse403.reverserecipes.Domain.UseCases;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Data.Repositories.RecipeSearchResultRepository;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

public class GetRecipeSearchResultsUseCase {
    private IngredientRepository mIngredientRepository;
    private RecipeSearchResultRepository mRecipeSearchResultRepository;

    // TODO: Get rid of Application dependency for testability.
    public GetRecipeSearchResultsUseCase(Application application) {
        mIngredientRepository = new IngredientRepository(application);
        mRecipeSearchResultRepository = new RecipeSearchResultRepository(application);
    }

    public LiveData<List<Recipe>> invoke() {
        MediatorLiveData<List<Recipe>> recipeSearchResultsLiveData = new MediatorLiveData<>();
        recipeSearchResultsLiveData.addSource(
                mIngredientRepository.getIngredients(),
                ingredients -> {
                    if (ingredients != null) {
                        LiveData<List<Recipe>> recipeSearchResults =
                                mRecipeSearchResultRepository.getResultRecipes(ingredients);
                        recipeSearchResultsLiveData.addSource(
                                recipeSearchResults,
                                recipeSearchResultsLiveData::postValue
                        );
                    }
                }
        );
        return recipeSearchResultsLiveData;
    }
}
