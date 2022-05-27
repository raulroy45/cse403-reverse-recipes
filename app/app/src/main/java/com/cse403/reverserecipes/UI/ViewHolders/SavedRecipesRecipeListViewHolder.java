package com.cse403.reverserecipes.UI.ViewHolders;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.RecipeSearchRecipeListAdapter;
import com.cse403.reverserecipes.UI.Adapters.SavedRecipesRecipeListAdapter;
import com.cse403.reverserecipes.UI.Entities.Recipe;

// TODO: Promote code reuse by reconciling with RecipeSearchRecipeListViewHolder.
public class SavedRecipesRecipeListViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final TextView mListItemTime;
    private final TextView mListItemTitle;
    private final ImageView mListItemFavoriteButton;
    private final Drawable mListItemFavoriteButtonFilledDrawable;
    private final SavedRecipesRecipeListAdapter.OnClickListener mOnClickListener;

    public SavedRecipesRecipeListViewHolder(@NonNull View itemView, SavedRecipesRecipeListAdapter.OnClickListener onClickListener) {
        super(itemView);

        mListItemTime = itemView.findViewById(R.id.recipe_list_item_time);
        mListItemTitle = itemView.findViewById(R.id.recipe_list_item_title);
        mListItemFavoriteButton = itemView.findViewById(R.id.recipe_list_favorite_button);
        mListItemFavoriteButtonFilledDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_favorite);
        mOnClickListener = onClickListener;

        itemView.setOnClickListener(this);
        // TODO: Do this in a better practice way?
        mListItemFavoriteButton.setOnClickListener(
                view -> mOnClickListener.onClickFavorite(getAdapterPosition())
        );
    }

    public void bind(Recipe recipe) {
        mListItemTitle.setText(recipe.getTitle());
        mListItemTime.setText(Integer.toString(recipe.getTotalTime()));

        // Everything is saved, so just set favorite icon to filled.
        mListItemFavoriteButton.setImageDrawable(mListItemFavoriteButtonFilledDrawable);
    }

    public static SavedRecipesRecipeListViewHolder create(
            ViewGroup parent,
            SavedRecipesRecipeListAdapter.OnClickListener onClickListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe_list, parent, false);
        return new SavedRecipesRecipeListViewHolder(view, onClickListener);
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClickCard(getAdapterPosition());
    }
}
