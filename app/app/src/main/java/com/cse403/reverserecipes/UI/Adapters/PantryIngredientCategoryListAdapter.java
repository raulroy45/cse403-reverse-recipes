package com.cse403.reverserecipes.UI.Adapters;

import android.util.Pair;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryListViewHolder;
import com.cse403.reverserecipes.UI.ViewHolders.PantryIngredientCategoryListViewHolder;

import java.util.List;

// TODO: Promote code reuse by reconciling with IngredientSearchIngredientCategoryListAdapter.
public class PantryIngredientCategoryListAdapter
        extends ListAdapter<Pair<String, List<Ingredient>>, PantryIngredientCategoryListViewHolder> {

    public PantryIngredientCategoryListAdapter(
            @NonNull DiffUtil.ItemCallback<Pair<String, List<Ingredient>>> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PantryIngredientCategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PantryIngredientCategoryListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryIngredientCategoryListViewHolder holder, int position) {
        Pair<String, List<Ingredient>> current = getItem(position);
        holder.bind(current);
    }
}
