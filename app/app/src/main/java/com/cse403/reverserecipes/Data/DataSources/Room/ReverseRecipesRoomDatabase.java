package com.cse403.reverserecipes.Data.DataSources.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cse403.reverserecipes.Data.Converters.Converters;
import com.cse403.reverserecipes.Data.Entities.DataIngredient;
import com.cse403.reverserecipes.Data.Entities.DataRecipe;
import com.cse403.reverserecipes.Data.Entities.IngredientSelection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Set schema to export.
@Database(entities = {DataIngredient.class, DataRecipe.class}, version = 1, exportSchema = false)
@TypeConverters({ Converters.class })
public abstract class ReverseRecipesRoomDatabase extends RoomDatabase {
    public abstract DataIngredientDao ingredientDao();
    public abstract DataRecipeDao recipeDao();

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
