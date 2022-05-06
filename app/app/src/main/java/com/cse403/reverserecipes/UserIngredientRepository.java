package com.cse403.reverserecipes;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

    LiveData<List<Ingredient>> getAllIngredients() { return mAllIngredients; }

    public LiveData<List<IngredientSelection>> getAllIngredientSelections() { return mAllIngredientSelections; }
}
