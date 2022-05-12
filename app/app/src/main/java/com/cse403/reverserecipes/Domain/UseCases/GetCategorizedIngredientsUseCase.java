package com.cse403.reverserecipes.Domain.UseCases;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;
import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.IngredientCategory;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetCategorizedIngredientsUseCase {
    private IngredientRepository mIngredientRepository;

    // TODO: Get rid of Application dependency for testability.
    public GetCategorizedIngredientsUseCase(Application application) {
        mIngredientRepository = new IngredientRepository(application);
    }

    public LiveData<List<Pair<String, List<Ingredient>>>> invoke(boolean onlySelected) {
        // Determine which source to use depending on whether caller only wants selected ingredients.
        LiveData<List<Ingredient>> ingredientsLiveData;
        if (onlySelected) {
            ingredientsLiveData = mIngredientRepository.getSelectedIngredients();
        } else {
            ingredientsLiveData = mIngredientRepository.getIngredients();
        }

        MediatorLiveData<List<Pair<String, List<Ingredient>>>> categorizedIngredientsLiveData = new MediatorLiveData<>();
        categorizedIngredientsLiveData.addSource(
                ingredientsLiveData,
                ingredients -> {
                    if (ingredients != null) {
                        Map<String, List<Ingredient>> ingredientCategoryToIngredientMap = new HashMap<>();
                        for (Ingredient ingredient : ingredients) {
                            if (!ingredientCategoryToIngredientMap.containsKey(ingredient.getCategory())) {
                                ingredientCategoryToIngredientMap.put(ingredient.getCategory(), new ArrayList<>());
                            }
                            ingredientCategoryToIngredientMap.get(ingredient.getCategory()).add(ingredient);
                        }

                        List<Pair<String, List<Ingredient>>> categorizedIngredients = new ArrayList<>();
                        for (String ingredientCategory : ingredientCategoryToIngredientMap.keySet()) {
                            categorizedIngredients.add(
                                    new Pair<>(
                                            ingredientCategory,
                                            ingredientCategoryToIngredientMap.get(ingredientCategory)
                                    )
                            );
                        }

                        categorizedIngredientsLiveData.postValue(categorizedIngredients);
                    } else {
                        categorizedIngredientsLiveData.postValue(null);
                    }
                }
        );

        return categorizedIngredientsLiveData;
    }
}
