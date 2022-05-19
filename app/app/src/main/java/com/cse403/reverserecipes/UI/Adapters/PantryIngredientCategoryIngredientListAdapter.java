package com.cse403.reverserecipes.UI.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryIngredientListViewHolder;
import com.cse403.reverserecipes.UI.ViewHolders.PantryIngredientCategoryIngredientListViewHolder;

// TODO: Promote code reuse by reconciling with IngredientSearchIngredientCategoryIngredientListAdapter.
public class PantryIngredientCategoryIngredientListAdapter
        extends ListAdapter<Ingredient, PantryIngredientCategoryIngredientListViewHolder> {

    public PantryIngredientCategoryIngredientListAdapter(
            @NonNull DiffUtil.ItemCallback<Ingredient> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PantryIngredientCategoryIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PantryIngredientCategoryIngredientListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryIngredientCategoryIngredientListViewHolder holder, int position) {
        Ingredient current = getItem(position);
        holder.bind(current);
    }
}
