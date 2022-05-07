package com.cse403.reverserecipes.UI.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryIngredientListAdapter;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategory;
import com.cse403.reverserecipes.Data.Entities.ViewIngredientDiff;

public class IngredientSearchIngredientCategoryListViewHolder
        extends RecyclerView.ViewHolder
        implements IngredientSearchIngredientCategoryIngredientListAdapter.OnClickListener {
    private final TextView mListItemTitle;
    private final IngredientSearchIngredientCategoryIngredientListAdapter mListItemListAdapter;
    private final IngredientSearchIngredientCategoryListAdapter.OnClickListener mOnClickListener;

    public IngredientSearchIngredientCategoryListViewHolder(@NonNull View itemView, IngredientSearchIngredientCategoryListAdapter.OnClickListener onClickListener) {
        super(itemView);
        mListItemTitle = itemView.findViewById(R.id.ingredient_category_list_item_title);
        RecyclerView listItemList = itemView.findViewById(R.id.ingredient_category_list_item_list);
        mListItemListAdapter =
                new IngredientSearchIngredientCategoryIngredientListAdapter(
                        new ViewIngredientDiff(), this);
        listItemList.setAdapter(mListItemListAdapter);
        listItemList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        mOnClickListener = onClickListener;
    }

    public void bind(ViewIngredientCategory viewIngredientCategory) {
        mListItemListAdapter.submitList(viewIngredientCategory.getIngredientList());
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
