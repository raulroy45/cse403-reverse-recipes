package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Domain.UseCases.GetCategorizedIngredientsUseCase;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

import java.util.List;

// TODO: Remove need for context (AndroidViewModel -> ViewModel).
public class IngredientSearchViewModel extends AndroidViewModel {
    // TODO: Replace this (for toggling selection) with a domain layer use case.
    private final IngredientRepository mIngredientRepository;
    private final LiveData<List<Pair<String, List<Ingredient>>>> mIngredientCategories;

    // TODO: Address code duplication with PantryViewModel.
    public IngredientSearchViewModel(Application application) {
        super(application);

        mIngredientRepository = new IngredientRepository(application);
        GetCategorizedIngredientsUseCase useCase = new GetCategorizedIngredientsUseCase(application);
        mIngredientCategories = useCase.invoke(false);
    }

    public LiveData<List<Pair<String, List<Ingredient>>>> getIngredientCategories() {
        return mIngredientCategories;
    }

    public void toggleSelection(Ingredient ingredient) {
        // Flip the selection and insert into table.
        Ingredient toggledIngredient = new Ingredient(
                ingredient.getName(),
                ingredient.getCategory(),
                !ingredient.isSelected()
        );
        mIngredientRepository.insertIngredient(toggledIngredient);
    }
}
