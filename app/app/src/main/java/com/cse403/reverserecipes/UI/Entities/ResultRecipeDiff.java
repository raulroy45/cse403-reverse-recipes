package com.cse403.reverserecipes.UI.Entities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.cse403.reverserecipes.Data.Entities.ResultRecipe;

public class ResultRecipeDiff extends DiffUtil.ItemCallback<ResultRecipe> {

    @Override
    public boolean areItemsTheSame(@NonNull ResultRecipe oldItem, @NonNull ResultRecipe newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ResultRecipe oldItem, @NonNull ResultRecipe newItem) {
        return oldItem.equals(newItem);
    }
}
