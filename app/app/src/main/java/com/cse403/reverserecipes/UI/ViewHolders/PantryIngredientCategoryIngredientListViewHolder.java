package com.cse403.reverserecipes.UI.ViewHolders;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

// TODO: Promote code reuse by reconciling with IngredientSearchIngredientCategoryIngredientListViewHolder.
public class PantryIngredientCategoryIngredientListViewHolder
        extends RecyclerView.ViewHolder {
    private final TextView mListItemText;
    private final Drawable mListItemTextBackground;
    private final int mColorSelected;
    private final int mColorOnSelected;

    public PantryIngredientCategoryIngredientListViewHolder(@NonNull View itemView) {
        super(itemView);
        mListItemText = itemView.findViewById(R.id.ingredient_category_ingredient_list_item_text);
        mListItemTextBackground = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_rounded_button);
        mColorSelected = ContextCompat.getColor(itemView.getContext(), R.color.dark_blue);
        mColorOnSelected = ContextCompat.getColor(itemView.getContext(), R.color.white);
    }

    // TODO: Use something less esoteric for setting background colors.
    public void bind(Ingredient ingredient) {
        mListItemText.setText(ingredient.getName());
        Drawable background = mListItemTextBackground;
        int listItemTextColor;

        // Everything is selected, so just set color scheme to selected.
        background.setColorFilter(new PorterDuffColorFilter(mColorSelected, PorterDuff.Mode.SRC_IN));
        listItemTextColor = mColorOnSelected;

        mListItemText.setBackground(background);
        mListItemText.setTextColor(listItemTextColor);
    }

    public static PantryIngredientCategoryIngredientListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient_category_ingredient_list, parent, false);
        return new PantryIngredientCategoryIngredientListViewHolder(view);
    }
}
