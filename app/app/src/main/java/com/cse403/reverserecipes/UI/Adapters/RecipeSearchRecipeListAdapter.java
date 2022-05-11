package com.cse403.reverserecipes.UI.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.Entities.Recipe;
import com.cse403.reverserecipes.UI.ViewHolders.RecipeSearchRecipeListViewHolder;

public class RecipeSearchRecipeListAdapter extends ListAdapter<Recipe, RecipeSearchRecipeListViewHolder> {
    public RecipeSearchRecipeListAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecipeSearchRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecipeSearchRecipeListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSearchRecipeListViewHolder holder, int position) {
        Recipe current = getItem(position);
        holder.bind(current);
    }
}
