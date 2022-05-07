package com.cse403.reverserecipes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientSearchViewModel extends ViewModel {
    private UserIngredientRepository mUserIngredientRepository;

    private MediatorLiveData<List<ViewIngredientCategory>> mViewIngredientCategories;

    // TODO: Address code duplication with PantryViewModel.
    public IngredientSearchViewModel(Application application) {
        mUserIngredientRepository = new UserIngredientRepository(application);
        LiveData<List<Ingredient>> allIngredients = mUserIngredientRepository.getAllIngredients();
        mViewIngredientCategories = new MediatorLiveData<>();
        mViewIngredientCategories.addSource(allIngredients,
                ingredients -> {
                    if (ingredients != null) {
                        Map<IngredientCategory, List<ViewIngredient>> ingredientCategoryIngredientMap = new HashMap<>();
                        List<IngredientCategory> indexIngredientCategoryList = new ArrayList<>();

                        // Iterate through ingredients, adding it into the its category if selected.
                        // If the category has no previous ingredients, reserve the next index for it.
                        for (Ingredient ingredient : ingredients) {
                            ViewIngredient viewIngredient = new ViewIngredient(
                                    ingredient.getId(), ingredient.getName(), true);

                            if (!ingredientCategoryIngredientMap.containsKey(ingredient.getCategory())) {
                                indexIngredientCategoryList.add(ingredient.getCategory());
                                ingredientCategoryIngredientMap.put(ingredient.getCategory(), new ArrayList<>());
                            }
                            ingredientCategoryIngredientMap.get(ingredient.getCategory()).add(viewIngredient);
                        }

                        List<ViewIngredientCategory> viewIngredientCategories = new ArrayList<>();

                        // Fill viewIngredientCategories with the contents of ingredientCategoryMap, using
                        // indexIngredientCategoryList for ordering.
                        for (IngredientCategory ingredientCategory : indexIngredientCategoryList) {
                            viewIngredientCategories.add(new ViewIngredientCategory(ingredientCategory,
                                    ingredientCategoryIngredientMap.get(ingredientCategory)));
                        }

                        mViewIngredientCategories.setValue(viewIngredientCategories);
                    }
                }
        );
    }

    public LiveData<List<ViewIngredientCategory>> getViewIngredientCategories() {
        return mViewIngredientCategories;
    }

    public void select(ViewIngredient viewIngredient) {
        mUserIngredientRepository.insert(new IngredientSelection(viewIngredient.getId()));
    }

    public void deselect(ViewIngredient viewIngredient) {
        mUserIngredientRepository.delete(new IngredientSelection(viewIngredient.getId()));
    }
}
