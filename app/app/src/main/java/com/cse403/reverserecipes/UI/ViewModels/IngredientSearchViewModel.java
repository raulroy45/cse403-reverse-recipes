package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

// TODO: Remove need for context (AndroidViewModel -> ViewModel).
public class IngredientSearchViewModel extends AndroidViewModel {
    private UserIngredientRepository mUserIngredientRepository;

    private LiveData<List<ViewIngredientCategory>> mViewIngredientCategories;

    // TODO: Address code duplication with PantryViewModel.
    public IngredientSearchViewModel(Application application) {
        super(application);

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

                        // Iterate through ingredients, adding it into the category with its selection status.
                        // If the category has no previous ingredients, reserve the next index for it.
                        for (Ingredient ingredient : ingredients) {
                            ViewIngredient viewIngredient = new ViewIngredient(
                                    ingredient.getId(), ingredient.getName(), selectedIngredientIdSet.contains(ingredient.getId()));

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

    public void select(ViewIngredient viewIngredient) {
        mUserIngredientRepository.insert(new IngredientSelection(viewIngredient.getId()));
    }

    public void deselect(ViewIngredient viewIngredient) {
        mUserIngredientRepository.delete(new IngredientSelection(viewIngredient.getId()));
    }
}
