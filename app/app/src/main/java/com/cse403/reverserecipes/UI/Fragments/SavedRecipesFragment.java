package com.cse403.reverserecipes.UI.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Adapters.RecipeSearchRecipeListAdapter;
import com.cse403.reverserecipes.UI.Adapters.SavedRecipesRecipeListAdapter;
import com.cse403.reverserecipes.UI.Entities.Recipe;
import com.cse403.reverserecipes.UI.Entities.RecipeDiff;
import com.cse403.reverserecipes.UI.ItemDecorations.RecipeListItemDecoration;
import com.cse403.reverserecipes.UI.ViewModels.RecipeSearchViewModel;
import com.cse403.reverserecipes.UI.ViewModels.SavedRecipesViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedRecipesFragment
        extends Fragment
        implements SavedRecipesRecipeListAdapter.OnClickListener {

    private SavedRecipesViewModel mSavedRecipesViewModel;

    public SavedRecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedRecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedRecipesFragment newInstance(String param1, String param2) {
        return new SavedRecipesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saved_recipes, container, false);

        RecyclerView recipeListView = v.findViewById(R.id.saved_recipe_list);
        final SavedRecipesRecipeListAdapter adapter = new SavedRecipesRecipeListAdapter(new RecipeDiff(), this);
        recipeListView.setAdapter(adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        RecyclerView.ItemDecoration itemDecoration = new RecipeListItemDecoration(requireActivity());
        recipeListView.addItemDecoration(itemDecoration);

        mSavedRecipesViewModel = new ViewModelProvider(requireActivity()).get(SavedRecipesViewModel.class);
        mSavedRecipesViewModel.getSavedRecipes().observe(requireActivity(), adapter::submitList);

        return v;
    }

    @Override
    public void onClickCard(int recipePosition) {
        Recipe clickedRecipe = mSavedRecipesViewModel
                .getSavedRecipes()
                .getValue()
                .get(recipePosition);
        SavedRecipesFragmentDirections.ActionSavedRecipesFragmentToRecipePageFragment action =
                SavedRecipesFragmentDirections.actionSavedRecipesFragmentToRecipePageFragment(clickedRecipe);

        FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();

        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        navController.navigate(action);
    }

    @Override
    public void onClickFavorite(int recipePosition) {
        Recipe clickedRecipe = mSavedRecipesViewModel
                .getSavedRecipes()
                .getValue()
                .get(recipePosition);
        mSavedRecipesViewModel.removeSaved(clickedRecipe);
    }
}