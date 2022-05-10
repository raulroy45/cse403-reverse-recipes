package com.cse403.reverserecipes.UI.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.Data.Entities.ResultRecipe;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientDiff;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;

public class RecipeSearchResultRecipeListViewHolder extends RecyclerView.ViewHolder {
    private final TextView mListItemText;

    public RecipeSearchResultRecipeListViewHolder(@NonNull View itemView) {
        super(itemView);
        mListItemText = itemView.findViewById(R.id.ingredient_category_ingredient_list_item_text);
    }

    public void bind(ResultRecipe resultRecipe) {
        mListItemText.setText(resultRecipe.getName());
    }

    public static RecipeSearchResultRecipeListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient_category_ingredient_list, parent, false);
        return new RecipeSearchResultRecipeListViewHolder(view);
    }
}
