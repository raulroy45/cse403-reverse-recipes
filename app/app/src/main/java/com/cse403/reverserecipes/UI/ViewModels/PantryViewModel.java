package com.cse403.reverserecipes.UI.ViewModels;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Repositories.IngredientRepository;
import com.cse403.reverserecipes.Domain.UseCases.GetCategorizedIngredientsUseCase;
import com.cse403.reverserecipes.IngredientCategory;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;
import com.cse403.reverserecipes.Utils.LiveDataUtilities;
import com.cse403.reverserecipes.Data.Repositories.UserIngredientRepository;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PantryViewModel extends AndroidViewModel {

    private final LiveData<List<Pair<String, List<Ingredient>>>> mViewIngredientCategories;

    // TODO: Address code duplication with RecipeSearchViewModel.
    public PantryViewModel(Application application) {
        super(application);

        GetCategorizedIngredientsUseCase useCase = new GetCategorizedIngredientsUseCase(application);
        mViewIngredientCategories = useCase.invoke(true);
    }
}
