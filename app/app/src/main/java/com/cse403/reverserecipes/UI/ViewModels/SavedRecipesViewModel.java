package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.Repositories.SavedRecipeRepository;
import com.cse403.reverserecipes.Domain.UseCases.GetRecipeSearchResultsUseCase;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

public class SavedRecipesViewModel extends AndroidViewModel {
    // TODO: Replace this (for removing selection, getting saved recipes) with a domain layer use case.
    private final SavedRecipeRepository mSavedRecipeRepository;
    private LiveData<List<Recipe>> mSavedRecipes;

    // TODO: Address code duplication with RecipeSearchViewModel.
    public SavedRecipesViewModel(Application application) {
        super(application);

        mSavedRecipeRepository = new SavedRecipeRepository(application);
        mSavedRecipes = mSavedRecipeRepository.getSavedRecipes();
    }

    public LiveData<List<Recipe>> getSavedRecipes() {
        return mSavedRecipes;
    }

    public void removeSaved(Recipe recipe) {
        // Remove from table.
        mSavedRecipeRepository.deleteSavedRecipe(recipe);
    }
}