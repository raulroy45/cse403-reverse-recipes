package com.cse403.reverserecipes.UI.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.Data.Entities.ResultRecipe;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;
import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryListViewHolder;
import com.cse403.reverserecipes.UI.ViewHolders.RecipeSearchResultRecipeListViewHolder;

public class RecipeSearchResultRecipeListAdapter extends ListAdapter<ResultRecipe, RecipeSearchResultRecipeListViewHolder> {
    public RecipeSearchResultRecipeListAdapter(@NonNull DiffUtil.ItemCallback<ResultRecipe> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecipeSearchResultRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecipeSearchResultRecipeListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSearchResultRecipeListViewHolder holder, int position) {
        ResultRecipe current = getItem(position);
        holder.bind(current);
    }
}
