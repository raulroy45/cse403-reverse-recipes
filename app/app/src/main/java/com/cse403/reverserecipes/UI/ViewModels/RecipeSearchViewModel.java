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

    private LiveData<List<ResultRecipe>> mResultRecipes;

    public RecipeSearchViewModel(Application application) {
        super(application);

        mResultRecipeRepository = new ResultRecipeRepository(application);
        mResultRecipes = mResultRecipeRepository.getAllResultRecipes();
    }

    public LiveData<List<ResultRecipe>> getResultRecipes() {
        return mResultRecipes;
    }
}