package com.cse403.reverserecipes.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.UI.ViewModels.IngredientSearchViewModel;
import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.ViewIngredient;
import com.cse403.reverserecipes.Data.Entities.ViewIngredientCategoryDiff;

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

        RecyclerView ingredientCategoryListView = v.findViewById(R.id.ingredient_search_ingredient_category_list);
        final IngredientSearchIngredientCategoryListAdapter adapter =
                new IngredientSearchIngredientCategoryListAdapter(
                        new ViewIngredientCategoryDiff(), this);
        ingredientCategoryListView.setAdapter(adapter);
        ingredientCategoryListView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mIngredientSearchViewModel = new ViewModelProvider(requireActivity()).get(IngredientSearchViewModel.class);

        mIngredientSearchViewModel.getViewIngredientCategories().observe(requireActivity(), adapter::submitList);

        return v;
    }

    @Override
    public void onClick(int categoryPosition, int ingredientPosition) {
        ViewIngredient clickedIngredient = mIngredientSearchViewModel
                .getViewIngredientCategories()
                .getValue()
                .get(categoryPosition)
                .getIngredientList()
                .get(ingredientPosition);

        if (clickedIngredient.isSelected()) {
            mIngredientSearchViewModel.deselect(clickedIngredient);
        } else {
            mIngredientSearchViewModel.select(clickedIngredient);
        }
    }
}