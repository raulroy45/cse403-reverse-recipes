package com.cse403.reverserecipes.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.UI.ItemDecorations.IngredientCategoryListItemDecoration;
import com.cse403.reverserecipes.UI.ViewModels.IngredientSearchViewModel;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.Ingredient;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategoryDiff;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientSearchFragment
        extends Fragment implements IngredientSearchIngredientCategoryListAdapter.OnClickListener {

    private IngredientSearchViewModel mIngredientSearchViewModel;

    public IngredientSearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static IngredientSearchFragment newInstance() {
        return new IngredientSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredient_search, container, false);

        // Set up ingredient list.
        // TODO: Promote code reuse by reconciling with same in PantryFragment.
        RecyclerView ingredientCategoryListView = v.findViewById(R.id.ingredient_search_ingredient_category_list);
        final IngredientSearchIngredientCategoryListAdapter adapter =
                new IngredientSearchIngredientCategoryListAdapter(
                        new ViewIngredientCategoryDiff(), this);
        ingredientCategoryListView.setAdapter(adapter);
        ingredientCategoryListView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        RecyclerView.ItemDecoration itemDecoration = new IngredientCategoryListItemDecoration(requireActivity());
        ingredientCategoryListView.addItemDecoration(itemDecoration);
        ingredientCategoryListView.setItemAnimator(null);

        // Set up filter menu button.
        Button filterMenuButton = v.findViewById(R.id.ingredient_search_filter_menu_button);
        FragmentContainerView filterMenuFrame = v.findViewById(R.id.ingredient_search_filter_menu_frame);
        filterMenuButton.setOnClickListener(
                view -> {
                    if (filterMenuFrame.getVisibility() == View.GONE) {
                        filterMenuFrame.setVisibility(View.VISIBLE);
                    } else {
                        filterMenuFrame.setVisibility(View.GONE);
                    }
                });

        // Set up ViewModel.
        mIngredientSearchViewModel = new ViewModelProvider(requireActivity()).get(IngredientSearchViewModel.class);
        mIngredientSearchViewModel.getIngredientCategories().observe(requireActivity(), adapter::submitList);

        return v;
    }

    @Override
    public void onClick(int categoryPosition, int ingredientPosition) {
        Ingredient clickedIngredient = mIngredientSearchViewModel
                .getIngredientCategories()
                .getValue()
                .get(categoryPosition)
                .second
                .get(ingredientPosition);

        mIngredientSearchViewModel.toggleSelection(clickedIngredient);
    }
}