package com.cse403.reverserecipes.UI.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryListViewHolder;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;

public class IngredientSearchIngredientCategoryListAdapter extends ListAdapter<ViewIngredientCategory, IngredientSearchIngredientCategoryListViewHolder> {
    private final OnClickListener mOnClickListener;

    public IngredientSearchIngredientCategoryListAdapter(
            @NonNull DiffUtil.ItemCallback<ViewIngredientCategory> diffCallback,
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
        ViewIngredientCategory current = getItem(position);
        holder.bind(current);
    }

    public interface OnClickListener {
        void onClick(int categoryPosition, int ingredientPosition);
    }
}
