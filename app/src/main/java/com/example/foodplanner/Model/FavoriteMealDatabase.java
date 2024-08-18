package com.example.foodplanner.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MealEntity.class}, version = 1)
public abstract class FavoriteMealDatabase extends RoomDatabase {
    private static FavoriteMealDatabase instance;

    public abstract MealDao favoriteMealDao();

    public static synchronized FavoriteMealDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteMealDatabase.class, "favorite_meal_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}