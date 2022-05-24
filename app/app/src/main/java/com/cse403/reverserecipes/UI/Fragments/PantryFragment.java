package com.cse403.reverserecipes.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.PantryIngredientCategoryListAdapter;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategoryDiff;
import com.cse403.reverserecipes.UI.ItemDecorations.IngredientCategoryListItemDecoration;
import com.cse403.reverserecipes.UI.ViewModels.PantryViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantryFragment extends Fragment {

    private PantryViewModel mPantryViewModel;

    public PantryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PantryFragment newInstance(String param1, String param2) {
        PantryFragment fragment = new PantryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pantry, container, false);

        // Set button actions.
        View ingredientSearchButton = v.findViewById(R.id.ingredient_search_button);
        ingredientSearchButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_pantryFragment_to_ingredientSearchFragment, null)
        );

        View recipeSearchButton = v.findViewById(R.id.recipe_search_button);
        recipeSearchButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_pantryFragment_to_recipeSearchFragment, null)
        );

        // Set up ingredient list.
        // TODO: Promote code reuse by reconciling with same in IngredientSearchFragment.
        RecyclerView ingredientCategoryListView = v.findViewById(R.id.pantry_ingredient_category_list);
        final PantryIngredientCategoryListAdapter adapter =
                new PantryIngredientCategoryListAdapter(new ViewIngredientCategoryDiff());
        ingredientCategoryListView.setAdapter(adapter);
        ingredientCategoryListView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        RecyclerView.ItemDecoration itemDecoration = new IngredientCategoryListItemDecoration(requireActivity());
        ingredientCategoryListView.addItemDecoration(itemDecoration);
        ingredientCategoryListView.setItemAnimator(null);

        // Set up ViewModel.
        mPantryViewModel = new ViewModelProvider(requireActivity()).get(PantryViewModel.class);
        mPantryViewModel.getIngredientCategories().observe(requireActivity(), adapter::submitList);

        return v;
    }
}