package com.cse403.reverserecipes.UI.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cse403.reverserecipes.UI.Entities.Recipe;
import com.cse403.reverserecipes.UI.ViewHolders.RecipeSearchRecipeListViewHolder;
import com.cse403.reverserecipes.UI.ViewHolders.SavedRecipesRecipeListViewHolder;

// TODO: Promote code reuse by reconciling with RecipeSearchRecipeListAdapter.
public class SavedRecipesRecipeListAdapter extends ListAdapter<Recipe, SavedRecipesRecipeListViewHolder> {

    private OnClickListener mOnClickListener;

    public SavedRecipesRecipeListAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback, OnClickListener onClickListener) {
        super(diffCallback);

        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SavedRecipesRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SavedRecipesRecipeListViewHolder.create(parent, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedRecipesRecipeListViewHolder holder, int position) {
        Recipe current = getItem(position);
        holder.bind(current);
    }

    public interface OnClickListener {
        void onClickCard(int recipePosition);
        void onClickFavorite(int recipePosition);
    }
}
