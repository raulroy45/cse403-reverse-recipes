package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Domain.UseCases.GetCategorizedIngredientsUseCase;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

import java.util.List;

public class PantryViewModel extends AndroidViewModel {

    private final LiveData<List<Pair<String, List<Ingredient>>>> mIngredientCategories;

    // TODO: Address code duplication with RecipeSearchViewModel.
    public PantryViewModel(Application application) {
        super(application);

        GetCategorizedIngredientsUseCase useCase = new GetCategorizedIngredientsUseCase(application);
        mIngredientCategories = useCase.invoke(true);
    }

    public LiveData<List<Pair<String, List<Ingredient>>>> getIngredientCategories() {
        return mIngredientCategories;
    }
}
