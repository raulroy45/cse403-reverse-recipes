package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;

import java.util.List;

public class UserIngredientRepository {
    private IngredientRepository mIngredientRepository;
    private IngredientSelectionRepository mIngredientSelectionRepository;
    private LiveData<List<Ingredient>> mAllIngredients;
    private LiveData<List<IngredientSelection>> mAllIngredientSelections;

    public UserIngredientRepository(Application application) {
        mIngredientRepository = new IngredientRepository(application);
        mIngredientSelectionRepository = new IngredientSelectionRepository(application);
        mAllIngredients = mIngredientRepository.getAllIngredients();
        mAllIngredientSelections = mIngredientSelectionRepository.getAllIngredientSelections();
    }

    public LiveData<List<Ingredient>> getAllIngredients() { return mAllIngredients; }

    public LiveData<List<IngredientSelection>> getAllIngredientSelections() { return mAllIngredientSelections; }

    public void insert(IngredientSelection ingredientSelection) {
        mIngredientSelectionRepository.insert(ingredientSelection);
    }

    public void delete(IngredientSelection ingredientSelection) {
        mIngredientSelectionRepository.delete(ingredientSelection);
    }
}
