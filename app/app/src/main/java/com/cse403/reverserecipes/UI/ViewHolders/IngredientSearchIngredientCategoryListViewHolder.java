package com.cse403.reverserecipes.UI.ViewHolders;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.IngredientDiff;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

// TODO: Promote code reuse by reconciling with PantryIngredientCategoryListViewHolder.
public class IngredientSearchIngredientCategoryListViewHolder
        extends RecyclerView.ViewHolder
        implements IngredientSearchIngredientCategoryIngredientListAdapter.OnClickListener {
    private final TextView mListItemTitle;
    private final IngredientSearchIngredientCategoryIngredientListAdapter mListItemListAdapter;
    private final IngredientSearchIngredientCategoryListAdapter.OnClickListener mOnClickListener;

    public IngredientSearchIngredientCategoryListViewHolder(
            @NonNull View itemView, IngredientSearchIngredientCategoryListAdapter.OnClickListener onClickListener) {
        super(itemView);
        mListItemTitle = itemView.findViewById(R.id.ingredient_category_list_item_title);
        RecyclerView listItemList = itemView.findViewById(R.id.ingredient_category_list_item_list);
        mListItemListAdapter =
                new IngredientSearchIngredientCategoryIngredientListAdapter(
                        new IngredientDiff(), this);
        listItemList.setAdapter(mListItemListAdapter);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        listItemList.setLayoutManager(layoutManager);
        FlexboxItemDecoration itemDecoration = new FlexboxItemDecoration(itemView.getContext());
        itemDecoration.setOrientation(FlexboxItemDecoration.BOTH);
        itemDecoration.setDrawable(ContextCompat.getDrawable(
                itemView.getContext(), R.drawable.ingredient_category_ingredient_list_divider));
        listItemList.addItemDecoration(itemDecoration);
        listItemList.setItemAnimator(null);
        mOnClickListener = onClickListener;

        // TODO: Change this; it prevents the list from briefly showing old contents when
        //  selections are updated, causing items to jump around the screen, but also wastes
        //  resources.
        setIsRecyclable(false);
    }

    public void bind(Pair<String, List<Ingredient>> viewIngredientCategory) {
        mListItemTitle.setText(viewIngredientCategory.first.toString());
        mListItemListAdapter.submitList(viewIngredientCategory.second);
    }

    public static IngredientSearchIngredientCategoryListViewHolder create(
            ViewGroup parent, IngredientSearchIngredientCategoryListAdapter.OnClickListener onClickListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient_category_list, parent, false);
        return new IngredientSearchIngredientCategoryListViewHolder(view, onClickListener);
    }

    @Override
    public void onClick(int ingredientPosition) {
        mOnClickListener.onClick(getAdapterPosition(), ingredientPosition);
    }
}
