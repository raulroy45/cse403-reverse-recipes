//package com.cse403.reverserecipes;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link IngredientSearchFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class IngredientSearchFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public IngredientSearchFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SearchFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static IngredientSearchFragment newInstance(String param1, String param2) {
//        IngredientSearchFragment fragment = new IngredientSearchFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_ingredient_search, container, false);
//    }
//}

package com.cse403.reverserecipes;;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.cse403.reverserecipes.ShapeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class IngredientSearchFragment extends Fragment
{

    public static ArrayList<self.is.ccahill.com.au.shapelist.Shape> shapeList = new ArrayList<self.is.ccahill.com.au.shapelist.Shape>();

    private ListView listView;
    //    private Button sortButton;
//    private Button filterButton;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
//    private LinearLayout sortView;

//    boolean sortHidden = true;
//    boolean filterHidden = true;

    private Button circleButton, squareButton, rectangleButton, triangleButton, octagonButton, allButton;
//    private Button idAscButton, idDescButton, nameAscButton, nameDescButton;

    private ArrayList<String> selectedFilters = new ArrayList<String>();
    private String currentSearchText = "";
    private SearchView searchView;

    private int white, darkGray, red;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient_search, container, false);
        initSearchWidgets(view);
        initWidgets(view);
        setupData();
        setUpList(view);
        setUpOnclickListener();
//        hideFilter();
//        hideSort();
        initColors();
//        lookSelected(idAscButton);
        lookSelected(allButton);
        selectedFilters.add("all");

        allButton.setOnClickListener(
                this::allFilterTapped
        );
        triangleButton.setOnClickListener(
                this::triangleFilterTapped
        );
        circleButton.setOnClickListener(
                this::circleFilterTapped
        );
        rectangleButton.setOnClickListener(
                this::rectangleFilterTapped
        );
        squareButton.setOnClickListener(
                this::squareFilterTapped
        );
        octagonButton.setOnClickListener(
                this::octagonFilterTapped
        );

        return view;
    }

    private void initColors()
    {
        white = ContextCompat.getColor(requireActivity(), R.color.colorPrimary);
        red = ContextCompat.getColor(requireActivity(), R.color.red);
        darkGray = ContextCompat.getColor(requireActivity(), R.color.darkerGray);
    }


//    private void unSelectAllSortButtons()
//    {
//        lookUnSelected(idAscButton);
//        lookUnSelected(idDescButton);
//        lookUnSelected(nameAscButton);
//        lookUnSelected(nameDescButton);
//    }

    private void unSelectAllFilterButtons()
    {
        lookUnSelected(allButton);
        lookUnSelected(circleButton);
        lookUnSelected(rectangleButton);
        lookUnSelected(octagonButton);
        lookUnSelected(triangleButton);
        lookUnSelected(squareButton);
    }

    private void lookSelected(Button parsedButton)
    {
        parsedButton.setTextColor(white);
        parsedButton.setBackgroundColor(red);
    }

    private void lookUnSelected(Button parsedButton)
    {
        parsedButton.setTextColor(red);
        parsedButton.setBackgroundColor(darkGray);
    }

    private void initWidgets(View view)
    {
//        sortButton = (Button) findViewById(R.id.sortButton);
//        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) view.findViewById(R.id.filterTabsLayout);
        filterView2 = (LinearLayout) view.findViewById(R.id.filterTabsLayout2);
//        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout2);

        circleButton = (Button) view.findViewById(R.id.circleFilter);
        squareButton = (Button) view.findViewById(R.id.squareFilter);
        rectangleButton = (Button) view.findViewById(R.id.rectangleFilter);
        triangleButton  = (Button) view.findViewById(R.id.triangleFilter);
        octagonButton  = (Button) view.findViewById(R.id.octagonFilter);
        allButton  = (Button) view.findViewById(R.id.allFilter);

//        idAscButton  = (Button) findViewById(R.id.idAsc);
//        idDescButton  = (Button) findViewById(R.id.idDesc);
//        nameAscButton  = (Button) findViewById(R.id.nameAsc);
//        nameDescButton  = (Button) findViewById(R.id.nameDesc);
    }

    private void initSearchWidgets(View view)
    {
        searchView = (SearchView) view.findViewById(R.id.shapeListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                ArrayList<self.is.ccahill.com.au.shapelist.Shape> filteredShapes = new ArrayList<self.is.ccahill.com.au.shapelist.Shape>();

                for(self.is.ccahill.com.au.shapelist.Shape shape: shapeList)
                {
                    if(shape.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilters.contains("all"))
                        {
                            filteredShapes.add(shape);
                        }
                        else
                        {
                            for(String filter: selectedFilters)
                            {
                                if (shape.getName().toLowerCase().contains(filter))
                                {
                                    filteredShapes.add(shape);
                                }
                            }
                        }
                    }
                }
                setAdapter(filteredShapes);

                return false;
            }
        });
    }

    private void setupData()
    {
        shapeList = new ArrayList<self.is.ccahill.com.au.shapelist.Shape>();
        self.is.ccahill.com.au.shapelist.Shape circle = new self.is.ccahill.com.au.shapelist.Shape("0", "avocado", "fruits");
        shapeList.add(circle);

        self.is.ccahill.com.au.shapelist.Shape triangle = new self.is.ccahill.com.au.shapelist.Shape("1","broccoli", "veggies");
        shapeList.add(triangle);

        self.is.ccahill.com.au.shapelist.Shape square = new self.is.ccahill.com.au.shapelist.Shape("2","beef", "meat");
        shapeList.add(square);

        self.is.ccahill.com.au.shapelist.Shape rectangle = new self.is.ccahill.com.au.shapelist.Shape("3","milk", "diary");
        shapeList.add(rectangle);

        self.is.ccahill.com.au.shapelist.Shape octagon = new self.is.ccahill.com.au.shapelist.Shape("4","rice", "grains");
        shapeList.add(octagon);

        self.is.ccahill.com.au.shapelist.Shape circle2 = new self.is.ccahill.com.au.shapelist.Shape("5", "apple", "fruits");
        shapeList.add(circle2);

//        Shape triangle2 = new Shape("6","Triangle 2", R.drawable.triangle);
//        shapeList.add(triangle2);
//
//        Shape square2 = new Shape("7","Square 2", R.drawable.square);
//        shapeList.add(square2);
//
//        Shape rectangle2 = new Shape("8","Rectangle 2", R.drawable.rectangle);
//        shapeList.add(rectangle2);
//
//        Shape octagon2 = new Shape("9","Octagon 2", R.drawable.octagon);
//        shapeList.add(octagon2);
    }

    private void setUpList(View view)
    {
        listView = (ListView) view.findViewById(R.id.shapesListView);

        setAdapter(shapeList);
    }

    private void setUpOnclickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                self.is.ccahill.com.au.shapelist.Shape selectShape = (self.is.ccahill.com.au.shapelist.Shape) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectShape.getId());
                startActivity(showDetail);
            }
        });

    }



    private void filterList(String status)
    {
        if(status != null && !selectedFilters.contains(status))
            selectedFilters.add(status);

        ArrayList<self.is.ccahill.com.au.shapelist.Shape> filteredShapes = new ArrayList<self.is.ccahill.com.au.shapelist.Shape>();

        for(self.is.ccahill.com.au.shapelist.Shape shape: shapeList)
        {
            for(String filter: selectedFilters)
            {
                if(shape.getType().toUpperCase().contains(filter.toUpperCase()))
                {
                    if(currentSearchText == "")
                    {
                        filteredShapes.add(shape);
                    }
                    else
                    {
                        if(shape.getName().toLowerCase().contains(currentSearchText.toLowerCase()))
                        {
                            filteredShapes.add(shape);
                        }
                    }
                }
            }
        }

        setAdapter(filteredShapes);
    }




    public void allFilterTapped(View view)
    {
        selectedFilters.clear();
        selectedFilters.add("all");

        unSelectAllFilterButtons();
        lookSelected(allButton);

        setAdapter(shapeList);
    }

    public void triangleFilterTapped(View view)
    {
        filterList("fruits");
        lookSelected(triangleButton);
        lookUnSelected(allButton);
    }

    public void squareFilterTapped(View view)
    {
        filterList("veggies");
        lookSelected(squareButton);
        lookUnSelected(allButton);
    }

    public void octagonFilterTapped(View view)
    {
        filterList("meat");
        lookSelected(octagonButton);
        lookUnSelected(allButton);
    }

    public void rectangleFilterTapped(View view)
    {
        filterList("diary");
        lookSelected(rectangleButton);
        lookUnSelected(allButton);
    }

    public void circleFilterTapped(View view)
    {
        filterList("grains");
        lookSelected(circleButton);
        lookUnSelected(allButton);
    }


//    public void showFilterTapped(View view)
//    {
//        if(filterHidden == true)
//        {
//            filterHidden = false;
//            showFilter();
//        }
//        else
//            {
//                filterHidden = true;
//                hideFilter();
//            }
//    }

//    public void showSortTapped(View view)
//    {
//        if(sortHidden == true)
//        {
//            sortHidden = false;
//            showSort();
//        }
//        else
//        {
//            sortHidden = true;
//            hideSort();
//        }
//    }



//    private void hideFilter()
//    {
//        searchView.setVisibility(View.GONE);
//        filterView1.setVisibility(View.GONE);
//        filterView2.setVisibility(View.GONE);
//        filterButton.setText("FILTER");
//    }
//
//    private void showFilter()
//    {
//        searchView.setVisibility(View.VISIBLE);
//        filterView1.setVisibility(View.VISIBLE);
//        filterView2.setVisibility(View.VISIBLE);
//        filterButton.setText("HIDE");
//    }
//
//    private void hideSort()
//    {
//        sortView.setVisibility(View.GONE);
//        sortButton.setText("SORT");
//    }
//
//    private void showSort()
//    {
//        sortView.setVisibility(View.VISIBLE);
//        sortButton.setText("HIDE");
//    }

//    public void idASCTapped(View view)
//    {
//        Collections.sort(shapeList, Shape.idAscending);
//        checkForFilter();
//        unSelectAllSortButtons();
//        lookSelected(idAscButton);
//    }
//
//    public void idDESCTapped(View view)
//    {
//        Collections.sort(shapeList, Shape.idAscending);
//        Collections.reverse(shapeList);
//        checkForFilter();
//        unSelectAllSortButtons();
//        lookSelected(idDescButton);
//    }

//    public void nameASCTapped(View view)
//    {
//        Collections.sort(shapeList, Shape.nameAscending);
//        checkForFilter();
//        unSelectAllSortButtons();
//        lookSelected(nameAscButton);
//    }
//
//    public void nameDESCTapped(View view)
//    {
//        Collections.sort(shapeList, Shape.nameAscending);
//        Collections.reverse(shapeList);
//        checkForFilter();
//        unSelectAllSortButtons();
//        lookSelected(nameDescButton);
//    }

    private void checkForFilter()
    {
        if(selectedFilters.contains("all"))
        {
            if(currentSearchText.equals(""))
            {
                setAdapter(shapeList);
            }
            else
            {
                ArrayList<self.is.ccahill.com.au.shapelist.Shape> filteredShapes = new ArrayList<self.is.ccahill.com.au.shapelist.Shape>();
                for(self.is.ccahill.com.au.shapelist.Shape shape: shapeList)
                {
                    if(shape.getName().toLowerCase().contains(currentSearchText))
                    {
                        filteredShapes.add(shape);
                    }
                }
                setAdapter(filteredShapes);
            }
        }
        else
        {
            filterList(null);
        }
    }

    private void setAdapter(ArrayList<self.is.ccahill.com.au.shapelist.Shape> shapeList)
    {
        ShapeAdapter adapter = new ShapeAdapter(requireActivity(), 0, shapeList);
        listView.setAdapter(adapter);
    }
}