package com.cse403.reverserecipes.UI.ItemDecorations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse403.reverserecipes.R;

public class RecipeListItemDecoration extends RecyclerView.ItemDecoration {

    private final int mMargin;

    public RecipeListItemDecoration(Context context) {
        mMargin = context.getResources().getDimensionPixelOffset(R.dimen.ingredient_category_list_item_margin);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (position < parent.getAdapter().getItemCount()) {
            outRect.bottom = mMargin;
        }
    }
}
