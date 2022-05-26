package com.cse403.reverserecipes.UI.Entities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class IngredientDiff extends DiffUtil.ItemCallback<Ingredient> {

    @Override
    public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
        return oldItem.equals(newItem);
    }
}
