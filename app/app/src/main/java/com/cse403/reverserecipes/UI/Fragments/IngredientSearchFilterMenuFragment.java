package com.cse403.reverserecipes.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryFilterListAdapter;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategoryFilterDiff;
import com.cse403.reverserecipes.UI.ViewModels.IngredientSearchViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientSearchFilterMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientSearchFilterMenuFragment
        extends Fragment implements IngredientSearchIngredientCategoryFilterListAdapter.OnClickListener {

    private IngredientSearchViewModel mIngredientSearchViewModel;

    public IngredientSearchFilterMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static IngredientSearchFilterMenuFragment newInstance(String param1, String param2) {
        return new IngredientSearchFilterMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredient_search_filter_menu, container, false);

        // Set up filter list.
        RecyclerView ingredientCategoryFilterListView = v.findViewById(R.id.ingredient_search_ingredient_category_filter_list);
        final IngredientSearchIngredientCategoryFilterListAdapter adapter =
                new IngredientSearchIngredientCategoryFilterListAdapter(
                        new ViewIngredientCategoryFilterDiff(), this);
        ingredientCategoryFilterListView.setAdapter(adapter);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireActivity());
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        ingredientCategoryFilterListView.setLayoutManager(layoutManager);
        ingredientCategoryFilterListView.setItemAnimator(null);

        // Set up ViewModel.
        mIngredientSearchViewModel = new ViewModelProvider(requireActivity()).get(IngredientSearchViewModel.class);
        mIngredientSearchViewModel.getIngredientCategoryFilters().observe(requireActivity(), adapter::submitList);

        return v;
    }

    @Override
    public void onClick(int filterPosition) {
        String clickedIngredientCategory = mIngredientSearchViewModel
                .getIngredientCategoryFilters()
                .getValue()
                .get(filterPosition)
                .first;

        mIngredientSearchViewModel.toggleIngredientCategoryFilter(clickedIngredientCategory);
    }
}