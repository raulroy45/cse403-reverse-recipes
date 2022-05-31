package com.cse403.reverserecipes.UI.Entities;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ViewIngredientCategoryFilterDiff extends DiffUtil.ItemCallback<Pair<String, Boolean>> {

    @Override
    public boolean areItemsTheSame(@NonNull Pair<String, Boolean> oldItem, @NonNull Pair<String, Boolean> newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Pair<String, Boolean> oldItem, @NonNull Pair<String, Boolean> newItem) {
        return oldItem.equals(newItem);
    }
}
