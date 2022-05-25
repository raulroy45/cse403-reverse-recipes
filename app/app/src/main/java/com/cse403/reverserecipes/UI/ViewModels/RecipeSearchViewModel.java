package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Data.Repositories.SavedRecipeRepository;
import com.cse403.reverserecipes.Domain.UseCases.GetRecipeSearchResultsUseCase;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

public class RecipeSearchViewModel extends AndroidViewModel {
    // TODO: Replace this (for toggling selection) with a domain layer use case.
    private final SavedRecipeRepository mSavedRecipeRepository;
    private LiveData<List<Recipe>> mResultRecipes;

    // TODO: Address code duplication with SavedRecipesViewModel.
    public RecipeSearchViewModel(Application application) {
        super(application);

        mSavedRecipeRepository = new SavedRecipeRepository(application);
        GetRecipeSearchResultsUseCase useCase = new GetRecipeSearchResultsUseCase(application);
        mResultRecipes = useCase.invoke();
    }

    public LiveData<List<Recipe>> getResultRecipes() {
        return mResultRecipes;
    }

    public void toggleSaved(Recipe recipe) {
        // Insert into or remove from table given saved status.
        if (recipe.isSaved()) {
            mSavedRecipeRepository.deleteSavedRecipe(recipe);
        } else {
            mSavedRecipeRepository.insertSavedRecipe(recipe);
        }
    }
}