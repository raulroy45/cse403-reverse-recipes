package com.cse403.reverserecipes.Domain.UseCases;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Data.Repositories.RecipeSearchResultRepository;
import com.cse403.reverserecipes.Data.Repositories.SavedRecipeRepository;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.Recipe;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetRecipeSearchResultsUseCase {
    private final IngredientRepository mIngredientRepository;
    private final RecipeSearchResultRepository mRecipeSearchResultRepository;
    private final SavedRecipeRepository mSavedRecipeRepository;

    // TODO: Get rid of Application dependency for testability.
    public GetRecipeSearchResultsUseCase(Application application) {
        mIngredientRepository = new IngredientRepository(application);
        mRecipeSearchResultRepository = new RecipeSearchResultRepository(application);
        mSavedRecipeRepository = new SavedRecipeRepository(application);
    }

    public LiveData<List<Recipe>> invoke() {
        LiveData<List<Recipe>> savedRecipeLiveData = mSavedRecipeRepository.getSavedRecipes();
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

                            recipeSearchResultsLiveData = LiveDataUtilities.combine(
                                    mRecipeSearchResultRepository.getResultRecipes(ingredients),
                                    savedRecipeLiveData,
                                    (resultRecipes, savedRecipes) -> {
                                        List<Recipe> augmentedResultRecipes = new ArrayList<>();

                                        if (resultRecipes != null && savedRecipes != null) {
                                            // Use resultRecipes as a base. Set recipe as saved
                                            // if in savedRecipes.
                                            Map<Integer, Recipe> ridToSavedRecipeMap = new HashMap<>();
                                            for (Recipe recipe : savedRecipes) {
                                                ridToSavedRecipeMap.put(recipe.getRid(), recipe);
                                            }

                                            for (Recipe recipe : resultRecipes) {
                                                if (ridToSavedRecipeMap.containsKey(recipe.getRid())) {
                                                    augmentedResultRecipes.add(ridToSavedRecipeMap.get(recipe.getRid()));
                                                } else {
                                                    augmentedResultRecipes.add(recipe);
                                                }
                                            }
                                        }

                                        return augmentedResultRecipes;
                                    }
                            );

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
