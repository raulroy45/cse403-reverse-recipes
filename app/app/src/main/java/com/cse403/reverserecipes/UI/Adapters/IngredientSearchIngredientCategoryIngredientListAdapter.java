package com.cse403.reverserecipes.UI.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryIngredientListViewHolder;
import com.cse403.reverserecipes.UI.Entities.ViewIngredient;

public class IngredientSearchIngredientCategoryIngredientListAdapter
        extends ListAdapter<ViewIngredient, IngredientSearchIngredientCategoryIngredientListViewHolder> {
    private final OnClickListener mOnClickListener;

    public IngredientSearchIngredientCategoryIngredientListAdapter(
            @NonNull DiffUtil.ItemCallback<ViewIngredient> diffCallback,
            OnClickListener onClickListener) {
        super(diffCallback);
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public IngredientSearchIngredientCategoryIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return IngredientSearchIngredientCategoryIngredientListViewHolder.create(parent, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientSearchIngredientCategoryIngredientListViewHolder holder, int position) {
        ViewIngredient current = getItem(position);
        holder.bind(current);
    }

    public interface OnClickListener {
        void onClick(int ingredientPosition);
    }
}