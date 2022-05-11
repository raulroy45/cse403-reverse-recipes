package com.cse403.reverserecipes.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.cse403.reverserecipes.Data.API.IngredientFetchHttp;
import com.cse403.reverserecipes.Data.DataSources.Remote.IngredientRemoteDataSource;
import com.cse403.reverserecipes.Data.DataSources.Room.DataIngredientDao;
import com.cse403.reverserecipes.Data.DataSources.Room.ReverseRecipesRoomDatabase;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Domain.Mappers.ArrayListMapper;
import com.cse403.reverserecipes.Domain.Mappers.DataIngredientToIngredientMapper;
import com.cse403.reverserecipes.Domain.Mappers.IngredientToDataIngredientMapper;
import com.cse403.reverserecipes.Domain.Mappers.ListMapper;
import com.cse403.reverserecipes.UI.Entities.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IngredientRepository {

    private final IngredientRemoteDataSource mIngredientRemoteDataSource;
    private final DataIngredientDao mDataIngredientDao;

    private final LiveData<List<DataIngredient>> mDataIngredientsLiveData;
    private final MediatorLiveData<List<Ingredient>> mIngredientsLiveData;

    // TODO: Get rid of Application dependency for testability.
    public IngredientRepository(Application application) {
        mIngredientRemoteDataSource = new IngredientRemoteDataSource(new IngredientFetchHttp());
        ReverseRecipesRoomDatabase db = ReverseRecipesRoomDatabase.getDatabase(application);
        mDataIngredientDao = db.ingredientDao();

        mDataIngredientsLiveData = mDataIngredientDao.getIngredientsLiveData();

        // TODO: Dependency injection for the mapper?
        ListMapper<DataIngredient, Ingredient> dataIngredientToIngredientListMapper =
                new ArrayListMapper<>(new DataIngredientToIngredientMapper());
        mIngredientsLiveData = new MediatorLiveData<>();
        mIngredientsLiveData.addSource(
                mDataIngredientsLiveData,
                dataIngredients -> mIngredientsLiveData.postValue(dataIngredientToIngredientListMapper.map(dataIngredients))
        );
    }

    public LiveData<List<Ingredient>> getIngredients() {
        // Update ingredients using a web fetch.
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<DataIngredient> fetchedIngredients = mIngredientRemoteDataSource.getIngredients();
            List<DataIngredient> currentIngredients = mDataIngredientDao.getIngredients();

            // Delete ingredients not in fetchedIngredients and insert fetchedIngredients with
            // selection statuses from currentIngredients;
            Map<String, DataIngredient> ingredientNameToNewIngredientMap = new HashMap<>();
            for (DataIngredient ingredient : fetchedIngredients) {
                ingredientNameToNewIngredientMap.put(ingredient.getName(), ingredient);
            }

            Set<DataIngredient> removedIngredientSet = new HashSet<>();
            for (DataIngredient ingredient : currentIngredients) {
                if (ingredientNameToNewIngredientMap.containsKey(ingredient.getName())) {
                    DataIngredient newIngredient = ingredientNameToNewIngredientMap.get(ingredient.getName());
                    ingredientNameToNewIngredientMap.put(
                            ingredient.getName(),
                            new DataIngredient(
                                    ingredient.getName(),
                                    newIngredient.getCategory(),
                                    ingredient.isSelected()
                            )
                    );
                } else {
                    removedIngredientSet.add(ingredient);
                }
            }

            mDataIngredientDao.deleteIngredients(new ArrayList<>(removedIngredientSet));
            mDataIngredientDao.insertIngredients(new ArrayList<>(ingredientNameToNewIngredientMap.values()));
        });

        // Return the ingredients.
        return mIngredientsLiveData;
    }

    public void insertIngredient(Ingredient ingredient) {
        ReverseRecipesRoomDatabase.databaseWriteExecutor.execute(() -> {
            // TODO: Dependency injection for the mapper?
            mDataIngredientDao.insertIngredient(new IngredientToDataIngredientMapper().map(ingredient));
        });
    }
}
