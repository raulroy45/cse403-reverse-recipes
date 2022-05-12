package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Domain.UseCases.GetRecipeSearchResultsUseCase;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.util.List;

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