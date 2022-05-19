package com.cse403.reverserecipes.UI.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.RecipeSearchRecipeListAdapter;
import com.cse403.reverserecipes.UI.Entities.Recipe;

public class RecipeSearchRecipeListViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final TextView mListItemTime;
    private final TextView mListItemTitle;
    private final RecipeSearchRecipeListAdapter.OnClickListener mOnClickListener;

    public RecipeSearchRecipeListViewHolder(@NonNull View itemView, RecipeSearchRecipeListAdapter.OnClickListener onClickListener) {
        super(itemView);

        mListItemTime = itemView.findViewById(R.id.recipe_list_item_time);
        mListItemTitle = itemView.findViewById(R.id.recipe_list_item_title);
        mOnClickListener = onClickListener;

        itemView.setOnClickListener(this);
    }

    public void bind(Recipe recipe) {
        mListItemTitle.setText(recipe.getTitle());
        mListItemTime.setText(Integer.toString(recipe.getTotalTime()));
    }

    public static RecipeSearchRecipeListViewHolder create(
            ViewGroup parent,
            RecipeSearchRecipeListAdapter.OnClickListener onClickListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe_list, parent, false);
        return new RecipeSearchRecipeListViewHolder(view, onClickListener);
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(getAdapterPosition());
    }
}