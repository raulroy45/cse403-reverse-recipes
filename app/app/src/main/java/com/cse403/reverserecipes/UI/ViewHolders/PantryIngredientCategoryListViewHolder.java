package com.cse403.reverserecipes.UI.ViewHolders;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.UI.Adapters.PantryIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.IngredientDiff;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

// TODO: Promote code reuse by reconciling with IngredientSearchIngredientCategoryListViewHolder.
public class PantryIngredientCategoryListViewHolder extends RecyclerView.ViewHolder {
    private final TextView mListItemTitle;
    private final PantryIngredientCategoryIngredientListAdapter mListItemListAdapter;

    public PantryIngredientCategoryListViewHolder(@NonNull View itemView) {
        super(itemView);
        mListItemTitle = itemView.findViewById(R.id.ingredient_category_list_item_title);
        RecyclerView listItemList = itemView.findViewById(R.id.ingredient_category_list_item_list);
        mListItemListAdapter =
                new PantryIngredientCategoryIngredientListAdapter(new IngredientDiff());
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
    }

    public void bind(Pair<String, List<Ingredient>> viewIngredientCategory) {
        mListItemTitle.setText(viewIngredientCategory.first.toString());
        mListItemListAdapter.submitList(viewIngredientCategory.second);
    }

    public static PantryIngredientCategoryListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ingredient_category_list, parent, false);
        return new PantryIngredientCategoryListViewHolder(view);
    }
}
