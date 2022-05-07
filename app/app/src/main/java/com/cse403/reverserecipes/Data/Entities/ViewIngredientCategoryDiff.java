package com.cse403.reverserecipes.Data.Entities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;

public class ViewIngredientCategoryDiff extends DiffUtil.ItemCallback<ViewIngredientCategory> {

    @Override
    public boolean areItemsTheSame(@NonNull ViewIngredientCategory oldItem, @NonNull ViewIngredientCategory newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ViewIngredientCategory oldItem, @NonNull ViewIngredientCategory newItem) {
        return oldItem.equals(newItem);
    }
}
