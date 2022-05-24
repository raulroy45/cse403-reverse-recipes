package com.cse403.reverserecipes.UI.Entities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;

public class RecipeDiff extends DiffUtil.ItemCallback<Recipe> {

    @Override
    public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.equals(newItem);
    }
}
