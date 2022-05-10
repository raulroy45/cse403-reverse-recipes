package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.IngredientCategory;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;
import com.cse403.reverserecipes.Data.Repositories.UserIngredientRepository;
import com.cse403.reverserecipes.UI.Entities.ViewIngredient;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PantryViewModel extends ViewModel {
    private UserIngredientRepository mUserIngredientRepository;

    private LiveData<List<ViewIngredientCategory>> mViewIngredientCategories;

    // TODO: Address code duplication with RecipeSearchViewModel.
    public PantryViewModel(Application application) {
        mUserIngredientRepository = new UserIngredientRepository(application);
        LiveData<List<Ingredient>> allIngredients = mUserIngredientRepository.getAllIngredients();
        LiveData<List<IngredientSelection>> allIngredientSelections = mUserIngredientRepository.getAllIngredientSelections();
        mViewIngredientCategories = LiveDataUtilities.combine(allIngredients, allIngredientSelections,
                (ingredients, ingredientSelections) -> {
                    if (ingredients != null && ingredientSelections != null) {
                        Set<Integer> selectedIngredientIdSet = new HashSet<>();
                        for (IngredientSelection ingredientSelection : ingredientSelections) {
                            selectedIngredientIdSet.add(ingredientSelection.getIngredientId());
                        }

                        Map<IngredientCategory, List<ViewIngredient>> ingredientCategoryIngredientMap = new HashMap<>();
                        List<IngredientCategory> indexIngredientCategoryList = new ArrayList<>();

                        // Iterate through ingredients, adding it into its category if selected.
                        // If the category has no previous ingredients, reserve the next index for it.
                        for (Ingredient ingredient : ingredients) {
                            if (selectedIngredientIdSet.contains(ingredient.getId())) {
                                ViewIngredient viewIngredient = new ViewIngredient(
                                        ingredient.getId(), ingredient.getName(), true);

                                if (!ingredientCategoryIngredientMap.containsKey(ingredient.getCategory())) {
                                    indexIngredientCategoryList.add(ingredient.getCategory());
                                    ingredientCategoryIngredientMap.put(ingredient.getCategory(), new ArrayList<>());
                                }
                                ingredientCategoryIngredientMap.get(ingredient.getCategory()).add(viewIngredient);
                            }
                        }

                        List<ViewIngredientCategory> viewIngredientCategories = new ArrayList<>();

                        // Fill viewIngredientCategories with the contents of ingredientCategoryMap, using
                        // indexIngredientCategoryList for ordering.
                        for (IngredientCategory ingredientCategory : indexIngredientCategoryList) {
                            viewIngredientCategories.add(new ViewIngredientCategory(ingredientCategory,
                                    ingredientCategoryIngredientMap.get(ingredientCategory)));
                        }

                        return viewIngredientCategories;
                    } else {
                        return null;
                    }
                }
        );
    }

    public LiveData<List<ViewIngredientCategory>> getViewIngredientCategories() {
        return mViewIngredientCategories;
    }
}
