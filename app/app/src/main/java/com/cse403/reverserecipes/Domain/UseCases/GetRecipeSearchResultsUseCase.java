package com.cse403.reverserecipes.Domain.UseCases;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Data.Repositories.RecipeSearchResultRepository;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

public class GetRecipeSearchResultsUseCase {
    private final IngredientRepository mIngredientRepository;
    private final RecipeSearchResultRepository mRecipeSearchResultRepository;

    // TODO: Get rid of Application dependency for testability.
    public GetRecipeSearchResultsUseCase(Application application) {
        mIngredientRepository = new IngredientRepository(application);
        mRecipeSearchResultRepository = new RecipeSearchResultRepository(application);
    }

    public LiveData<List<Recipe>> invoke() {
        LiveData<List<Ingredient>> ingredientsLiveData = mIngredientRepository.getIngredients();
        MediatorLiveData<List<Recipe>> resolvedRecipeSearchResultsLiveData = new MediatorLiveData<>();
        resolvedRecipeSearchResultsLiveData.addSource(
                mIngredientRepository.getSelectedIngredients(),
                new Observer<List<Ingredient>>() {
                    private LiveData<List<Recipe>> recipeSearchResultsLiveData = null;

                    @Override
                    public void onChanged(List<Ingredient> ingredients) {
                        if (ingredients != null) {
                            // Unsubscribe from previous recipeSearchResultsLiveData and use new one.
                            if (recipeSearchResultsLiveData != null) {
                                resolvedRecipeSearchResultsLiveData.removeSource(recipeSearchResultsLiveData);
                            }
                            recipeSearchResultsLiveData =
                                    mRecipeSearchResultRepository.getResultRecipes(ingredients);
                            resolvedRecipeSearchResultsLiveData.addSource(
                                    recipeSearchResultsLiveData,
                                    resolvedRecipeSearchResultsLiveData::postValue
                            );
                        }
                    }
                }
        );
        return resolvedRecipeSearchResultsLiveData;
    }
}
