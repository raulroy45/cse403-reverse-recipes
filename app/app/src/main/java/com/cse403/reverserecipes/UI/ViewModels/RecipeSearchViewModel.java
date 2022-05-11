package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;
import com.cse403.reverserecipes.Data.Repositories.ResultRecipeRepository;
import com.cse403.reverserecipes.Data.Repositories.UserIngredientRepository;
import com.cse403.reverserecipes.IngredientCategory;
import com.cse403.reverserecipes.UI.Entities.ViewIngredient;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Result;

public class RecipeSearchViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private ResultRecipeRepository mResultRecipeRepository;
    private UserIngredientRepository mUserIngredientRepository;

    private LiveData<List<ResultRecipe>> mResultRecipes;

    private LiveData<List<Ingredient>> mSelectedIngredients;

    public RecipeSearchViewModel(Application application) {
        super(application);

        mUserIngredientRepository = new UserIngredientRepository(application);
        mResultRecipeRepository = new ResultRecipeRepository(application);
        mResultRecipes = mResultRecipeRepository.getAllResultRecipes();
        LiveData<List<Ingredient>> allIngredients = mUserIngredientRepository.getAllIngredients();
        LiveData<List<IngredientSelection>> allIngredientSelections = mUserIngredientRepository.getAllIngredientSelections();
        mSelectedIngredients = LiveDataUtilities.combine(allIngredients, allIngredientSelections,
                (ingredients, ingredientSelections) -> {
                    if (ingredients != null && ingredientSelections != null) {
                        Set<Integer> selectedIngredientIds = new HashSet<>();
                        for (IngredientSelection selection : allIngredientSelections.getValue()) {
                            selectedIngredientIds.add(selection.getIngredientId());
                        }
                        List<Ingredient> selectedIngredients = new ArrayList<>();
                        for (Ingredient ingredient : allIngredients.getValue()) {
                            if (selectedIngredientIds.contains(ingredient.getId())) {
                                selectedIngredients.add(ingredient);
                            }
                        }
                        return selectedIngredients;
                    } else {
                        return new ArrayList<>();
                    }
                }
        );
    }

    public LiveData<List<ResultRecipe>> getResultRecipes() {
        return mResultRecipes;
    }

    public LiveData<List<Ingredient>> getSelectedIngredients() { return mSelectedIngredients; }

    public void updateResultRecipes(List<Ingredient> selectedIngredients) {
        mResultRecipeRepository.updateResultRecipes(selectedIngredients);
    }
}