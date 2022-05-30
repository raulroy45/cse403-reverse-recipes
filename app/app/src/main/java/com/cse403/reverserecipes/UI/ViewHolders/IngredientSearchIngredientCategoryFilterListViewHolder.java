package com.cse403.reverserecipes.UI.ViewHolders;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryFilterListAdapter;

public class IngredientSearchIngredientCategoryFilterListViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final TextView mListItemName;
    private final ImageView mListItemCircle;
    private final IngredientSearchIngredientCategoryFilterListAdapter.OnClickListener mOnClickListener;
    private final int mColorSelected;
    private final int mColorUnselected;

    public IngredientSearchIngredientCategoryFilterListViewHolder(
            @NonNull View itemView, IngredientSearchIngredientCategoryFilterListAdapter.OnClickListener onClickListener) {
        super(itemView);

        mListItemName = itemView.findViewById(R.id.filter_list_text);
        mListItemCircle = itemView.findViewById(R.id.filter_list_circle);
        mOnClickListener = onClickListener;
        mColorSelected = ContextCompat.getColor(itemView.getContext(), R.color.dark_blue);
        mColorUnselected = ContextCompat.getColor(itemView.getContext(), R.color.white);

        itemView.setOnClickListener(this);
    }

    public void bind(Pair<String, Boolean> ingredientCategoryFilter) {
        mListItemName.setText(ingredientCategoryFilter.first);

        if (ingredientCategoryFilter.second) {
            mListItemCircle.setColorFilter(mColorSelected);
        } else {
            mListItemCircle.setColorFilter(mColorUnselected);
        }
    }

    public static IngredientSearchIngredientCategoryFilterListViewHolder create(
            ViewGroup parent,
            IngredientSearchIngredientCategoryFilterListAdapter.OnClickListener onClickListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_filter_list, parent, false);
        return new IngredientSearchIngredientCategoryFilterListViewHolder(view, onClickListener);
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(getAdapterPosition());
    }
}
