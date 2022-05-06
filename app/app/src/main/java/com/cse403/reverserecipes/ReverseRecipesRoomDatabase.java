package com.cse403.reverserecipes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Set schema to export.
@Database(entities = {Ingredient.class, IngredientSelection.class}, version = 1, exportSchema = false)
public abstract class ReverseRecipesRoomDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();
    public abstract IngredientSelectionDao ingredientSelectionDao();

    private static volatile ReverseRecipesRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ReverseRecipesRoomDatabase getDatabase(final Context context) {
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
