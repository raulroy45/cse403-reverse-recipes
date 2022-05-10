package com.cse403.reverserecipes.Data.DataSources.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cse403.reverserecipes.Data.Entities.Ingredient;
import com.cse403.reverserecipes.Data.Entities.ResultRecipe;
import com.cse403.reverserecipes.IngredientCategory;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Set schema to export.
@Database(entities = {Ingredient.class, IngredientSelection.class, ResultRecipe.class}, version = 1, exportSchema = false)
public abstract class ReverseRecipesRoomDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();
    public abstract IngredientSelectionDao ingredientSelectionDao();
    public abstract ResultRecipeDao resultRecipeDao();

    private static volatile ReverseRecipesRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ReverseRecipesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ReverseRecipesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReverseRecipesRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // TODO: Get rid of initial database fill, done for debug purposes.
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                IngredientDao dao = INSTANCE.ingredientDao();

                Ingredient ingredient = new Ingredient(1, "Radish", IngredientCategory.VEGETABLE);
                dao.insert(ingredient);
                ingredient = new Ingredient(2, "Pork", IngredientCategory.PROTEIN);
                dao.insert(ingredient);
                ingredient = new Ingredient(3, "Dragonfruit", IngredientCategory.FRUIT);
                dao.insert(ingredient);
                ingredient = new Ingredient(4, "Pomegranate", IngredientCategory.FRUIT);
                dao.insert(ingredient);
                ingredient = new Ingredient(5, "Jackfruit", IngredientCategory.FRUIT);
                dao.insert(ingredient);
                ingredient = new Ingredient(6, "Apple", IngredientCategory.FRUIT);
                dao.insert(ingredient);
                ingredient = new Ingredient(7, "Pear", IngredientCategory.FRUIT);
                dao.insert(ingredient);

                ResultRecipeDao rrDao = INSTANCE.resultRecipeDao();

                ResultRecipe resultRecipe = new ResultRecipe(1, "Green Eggs");
                rrDao.insert(resultRecipe);
                resultRecipe = new ResultRecipe(2, "Green Ham");
                rrDao.insert(resultRecipe);
            });
        }
    };
}