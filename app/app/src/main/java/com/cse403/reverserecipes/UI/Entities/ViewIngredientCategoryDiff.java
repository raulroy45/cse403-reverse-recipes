package com.cse403.reverserecipes.UI.Entities;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;

import java.util.List;

public class ViewIngredientCategoryDiff extends DiffUtil.ItemCallback<Pair<String, List<Ingredient>>> {

    @Override
    public boolean areItemsTheSame(@NonNull Pair<String, List<Ingredient>> oldItem, @NonNull Pair<String, List<Ingredient>> newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Pair<String, List<Ingredient>> oldItem, @NonNull Pair<String, List<Ingredient>> newItem) {
        return oldItem.equals(newItem);
    }
}
