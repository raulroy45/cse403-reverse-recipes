package com.cse403.reverserecipes.UI.Adapters;

import android.util.Pair;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryListViewHolder;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;

import java.util.List;

// TODO: Promote code reuse by reconciling with PantryIngredientCategoryListAdapter.
public class IngredientSearchIngredientCategoryListAdapter
        extends ListAdapter<Pair<String, List<Ingredient>>, IngredientSearchIngredientCategoryListViewHolder> {
    private final OnClickListener mOnClickListener;

    public IngredientSearchIngredientCategoryListAdapter(
            @NonNull DiffUtil.ItemCallback<Pair<String, List<Ingredient>>> diffCallback,
            OnClickListener onClickListener) {
        super(diffCallback);
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public IngredientSearchIngredientCategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return IngredientSearchIngredientCategoryListViewHolder.create(parent, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientSearchIngredientCategoryListViewHolder holder, int position) {
        Pair<String, List<Ingredient>> current = getItem(position);
        holder.bind(current);
    }

    public interface OnClickListener {
        void onClick(int categoryPosition, int ingredientPosition);
    }
}
