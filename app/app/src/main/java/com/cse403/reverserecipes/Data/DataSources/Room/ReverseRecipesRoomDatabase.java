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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
