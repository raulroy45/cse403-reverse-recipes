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

import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

public class IngredientSearchIngredientCategoryIngredientListViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private final TextView mListItemText;
    private final IngredientSearchIngredientCategoryIngredientListAdapter.OnClickListener mOnClickListener;
    private final Drawable mListItemTextBackground;
    private final int mColorSelected;
    private final int mColorOnSelected;
    private final int mColorUnselected;
    private final int mColorOnUnselected;

    public IngredientSearchIngredientCategoryIngredientListViewHolder(
            @NonNull View itemView,
            IngredientSearchIngredientCategoryIngredientListAdapter.OnClickListener onClickListener) {
        super(itemView);
        mListItemText = itemView.findViewById(R.id.ingredient_category_ingredient_list_item_text);
        mOnClickListener = onClickListener;
        mListItemTextBackground = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_rounded_button);
        mColorSelected = ContextCompat.getColor(itemView.getContext(), R.color.dark_blue);
        mColorOnSelected = ContextCompat.getColor(itemView.getContext(), R.color.white);
        mColorUnselected = ContextCompat.getColor(itemView.getContext(), R.color.light_gray);
        mColorOnUnselected = ContextCompat.getColor(itemView.getContext(), R.color.dark_gray);

        itemView.setOnClickListener(this);
    }

    // TODO: Use something less esoteric for setting background colors.
    public void bind(Ingredient ingredient) {
        mListItemText.setText(ingredient.getName());
        Drawable background = mListItemTextBackground;
        int listItemTextColor;
        if (ingredient.isSelected()) {
            background.setColorFilter(new PorterDuffColorFilter(mColorSelected, PorterDuff.Mode.SRC_IN));
            listItemTextColor = mColorOnSelected;
        } else {
            background.setColorFilter(new PorterDuffColorFilter(mColorUnselected, PorterDuff.Mode.SRC_IN));
            listItemTextColor = mColorOnUnselected;
        }
        mListItemText.setBackground(background);
        mListItemText.setTextColor(listItemTextColor);
    }

    public static IngredientSearchIngredientCategoryIngredientListViewHolder create(
            ViewGroup parent, IngredientSearchIngredientCategoryIngredientListAdapter.OnClickListener onClickListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient_category_ingredient_list, parent, false);
        return new IngredientSearchIngredientCategoryIngredientListViewHolder(view, onClickListener);
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(getAdapterPosition());
    }
}
