package com.cse403.reverserecipes.UI.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.Recipe;

public class RecipeSearchRecipeListViewHolder extends RecyclerView.ViewHolder {
    private final TextView mListItemText;

    public RecipeSearchRecipeListViewHolder(@NonNull View itemView) {
        super(itemView);
        mListItemText = itemView.findViewById(R.id.ingredient_category_ingredient_list_item_text);
    }

    public void bind(Recipe recipe) {
        mListItemText.setText(recipe.getTitle());
    }

    public static RecipeSearchRecipeListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient_category_ingredient_list, parent, false);
        return new RecipeSearchRecipeListViewHolder(view);
    }
}
