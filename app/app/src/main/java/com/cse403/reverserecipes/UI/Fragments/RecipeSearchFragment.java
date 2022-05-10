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
import com.cse403.reverserecipes.UI.Adapters.IngredientSearchIngredientCategoryListAdapter;
import com.cse403.reverserecipes.UI.Adapters.RecipeSearchResultRecipeListAdapter;
import com.cse403.reverserecipes.UI.Entities.ResultRecipeDiff;
import com.cse403.reverserecipes.UI.Entities.ViewIngredientCategoryDiff;
import com.cse403.reverserecipes.UI.ViewModels.IngredientSearchViewModel;
import com.cse403.reverserecipes.UI.ViewModels.RecipeSearchViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeSearchFragment extends Fragment {

    private RecipeSearchViewModel mRecipeSearchViewModel;

    public RecipeSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeSearchFragment newInstance(String param1, String param2) {
        return new RecipeSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_search, container, false);

        RecyclerView resultRecipeListView = v.findViewById(R.id.recipe_search_result_recipe_list);
        final RecipeSearchResultRecipeListAdapter adapter = new RecipeSearchResultRecipeListAdapter(new ResultRecipeDiff());
        resultRecipeListView.setAdapter(adapter);
        resultRecipeListView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mRecipeSearchViewModel = new ViewModelProvider(requireActivity()).get(RecipeSearchViewModel.class);

        mRecipeSearchViewModel.getResultRecipes().observe(requireActivity(), adapter::submitList);

        return v;
    }
}