package com.cse403.reverserecipes.UI.Adapters;

import android.util.Pair;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.Entities.Recipe;
import com.cse403.reverserecipes.UI.ViewHolders.IngredientSearchIngredientCategoryFilterListViewHolder;
import com.cse403.reverserecipes.UI.ViewHolders.SavedRecipesRecipeListViewHolder;

public class IngredientSearchIngredientCategoryFilterListAdapter
        extends ListAdapter<Pair<String, Boolean>, IngredientSearchIngredientCategoryFilterListViewHolder> {

    private OnClickListener mOnClickListener;

    public IngredientSearchIngredientCategoryFilterListAdapter(
            @NonNull DiffUtil.ItemCallback<Pair<String, Boolean>> diffCallback, OnClickListener onClickListener) {
        super(diffCallback);

        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public IngredientSearchIngredientCategoryFilterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return IngredientSearchIngredientCategoryFilterListViewHolder.create(parent, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientSearchIngredientCategoryFilterListViewHolder holder, int position) {
        Pair<String, Boolean> current = getItem(position);
        holder.bind(current);
    }

    public interface OnClickListener {
        void onClick(int filterPosition);
    }
}
