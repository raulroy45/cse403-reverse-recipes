package com.cse403.reverserecipes.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.Recipe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView image;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipePageFragment newInstance(String param1, String param2) {
        RecipePageFragment fragment = new RecipePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_recipe_page, container, false);
        View v = inflater.inflate(R.layout.fragment_recipe_page, container, false);
        Bundle bundle = getArguments();
        Recipe recipe = (Recipe) bundle.getSerializable("recipe");
        image = v.findViewById(R.id.imageView);

        Log.i("image", recipe.getImage());
        return v;


    }
}