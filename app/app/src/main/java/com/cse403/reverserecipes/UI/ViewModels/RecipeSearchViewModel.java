package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;
import com.cse403.reverserecipes.Data.Repositories.RecipeSearchResultRepository;
import com.cse403.reverserecipes.Data.Repositories.UserIngredientRepository;
import com.cse403.reverserecipes.Domain.UseCases.GetCategorizedIngredientsUseCase;
import com.cse403.reverserecipes.Domain.UseCases.GetRecipeSearchResultsUseCase;
import com.cse403.reverserecipes.UI.Entities.Recipe;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeSearchViewModel extends AndroidViewModel {

    private LiveData<List<Recipe>> mResultRecipes;

    public RecipeSearchViewModel(Application application) {
        super(application);

        GetRecipeSearchResultsUseCase useCase = new GetRecipeSearchResultsUseCase(application);
        mResultRecipes = useCase.invoke();
    }

    public LiveData<List<Recipe>> getResultRecipes() {
        return mResultRecipes;
    }
}