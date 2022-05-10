package com.cse403.reverserecipes.UI.Entities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.cse403.reverserecipes.UI.Entities.ViewIngredient;

public class ViewIngredientDiff extends DiffUtil.ItemCallback<ViewIngredient> {

    @Override
    public boolean areItemsTheSame(@NonNull ViewIngredient oldItem, @NonNull ViewIngredient newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ViewIngredient oldItem, @NonNull ViewIngredient newItem) {
        return oldItem.equals(newItem);
    }
}
