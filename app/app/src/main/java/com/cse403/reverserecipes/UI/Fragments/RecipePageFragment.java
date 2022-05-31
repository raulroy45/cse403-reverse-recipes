package com.cse403.reverserecipes.UI.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse403.reverserecipes.R;
import com.cse403.reverserecipes.UI.Entities.Recipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
        Recipe recipe = RecipePageFragmentArgs.fromBundle(getArguments()).getRecipe();
        ImageView recipeImage = (ImageView)v.findViewById(R.id.recipeImage);

        Button serving = (Button)v.findViewById(R.id.serving);
        String servingTxt = recipe.getYields() + " servings";
        serving.setText(servingTxt);

        Button timing = (Button)v.findViewById(R.id.timing);
        String timingTxt = recipe.getTotalTime() + " Min";
        timing.setText(timingTxt);

        Button website = (Button)v.findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Uri link = Uri.parse(recipe.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                startActivity(intent);
            }
        });
//        URL url = null;
//        try {
//            url = new URL(recipe.getImage());
//            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            recipeImage.setImageBitmap(image);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(recipe.getTitle());
        TextView ingredientText = (TextView)v.findViewById(R.id.ingredientText);
        List<String> ingredients = recipe.getIngredients();
        String ingredientStr = "";
        if (!ingredients.isEmpty()) {
            ingredientStr = recipe.getIngredients().get(0);
            for (int i = 1; i < ingredients.size(); i++) {
                ingredientStr += "\n" + ingredients.get(i);
            }
        }
        ingredientText.setText(ingredientStr);
        return v;


    }
}