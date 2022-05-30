package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Domain.UseCases.GetCategorizedIngredientsUseCase;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: Remove need for context (AndroidViewModel -> ViewModel).
public class IngredientSearchViewModel extends AndroidViewModel {
    // TODO: Replace this (for toggling selection) with a domain layer use case.
    private final IngredientRepository mIngredientRepository;
    private final LiveData<List<Pair<String, List<Ingredient>>>> mFilteredIngredientCategories;
    private final MutableLiveData<Set<String>> mFilteredIngredientCategoryNames;
    private final LiveData<List<Pair<String, Boolean>>> mIngredientCategoryFilters;

    // TODO: Address code duplication with PantryViewModel.
    public IngredientSearchViewModel(Application application) {
        super(application);

        mIngredientRepository = new IngredientRepository(application);
        GetCategorizedIngredientsUseCase useCase = new GetCategorizedIngredientsUseCase(application);
        LiveData<List<Pair<String, List<Ingredient>>>> ingredientCategoriesLiveData = useCase.invoke(false);

        mFilteredIngredientCategoryNames = new MutableLiveData<>(new HashSet<>());

        mIngredientCategoryFilters = LiveDataUtilities.combine(
                ingredientCategoriesLiveData, mFilteredIngredientCategoryNames,
                (ingredientCategories, filteredIngredientCategoryNames) -> {
                    List<Pair<String, Boolean>> ingredientCategoryFilters = new ArrayList<>();

                    if (ingredientCategories != null && filteredIngredientCategoryNames != null) {
                        for (Pair<String, List<Ingredient>> ingredientCategory : ingredientCategories) {
                            String categoryName = ingredientCategory.first;
                            if (filteredIngredientCategoryNames.contains(categoryName)) {
                                ingredientCategoryFilters.add(new Pair<>(categoryName, false));
                            } else {
                                ingredientCategoryFilters.add(new Pair<>(categoryName, true));
                            }
                        }
                    }

                    return ingredientCategoryFilters;
                }
        );

        mFilteredIngredientCategories = LiveDataUtilities.combine(
                ingredientCategoriesLiveData, mFilteredIngredientCategoryNames,
                (ingredientCategories, filteredIngredientCategoryNames) -> {
                    List<Pair<String, List<Ingredient>>> ingredientCategorySelections = new ArrayList<>();

                    if (ingredientCategories != null && filteredIngredientCategoryNames != null) {
                        for (Pair<String, List<Ingredient>> ingredientCategory : ingredientCategories) {
                            String categoryName = ingredientCategory.first;
                            if (!filteredIngredientCategoryNames.contains(categoryName)) {
                                ingredientCategorySelections.add(ingredientCategory);
                            }
                        }
                    }

                    return ingredientCategorySelections;
                }
        );
    }

    public LiveData<List<Pair<String, List<Ingredient>>>> getFilteredIngredientCategories() {
        return mFilteredIngredientCategories;
    }

    public LiveData<List<Pair<String, Boolean>>> getIngredientCategoryFilters() {
        return mIngredientCategoryFilters;
    }

    public void toggleIngredientSelection(Ingredient ingredient) {
        // Flip the selection and insert into table.
        Ingredient toggledIngredient = new Ingredient(
                ingredient.getName(),
                ingredient.getCategory(),
                !ingredient.isSelected()
        );
        mIngredientRepository.insertIngredient(toggledIngredient);
    }

    public void toggleIngredientCategoryFilter(String category) {
        Set<String> selectedIngredientCategories = mFilteredIngredientCategoryNames.getValue();
        if (selectedIngredientCategories.contains(category)) {
            selectedIngredientCategories.remove(category);
        } else {
            selectedIngredientCategories.add(category);
        }
        mFilteredIngredientCategoryNames.postValue(selectedIngredientCategories);
    }
}
