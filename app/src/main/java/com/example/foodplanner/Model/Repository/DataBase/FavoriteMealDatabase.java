package com.example.foodplanner.Model.Repository.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.Model.MealEntity;

@Database(entities = {MealEntity.class}, version = 1, exportSchema = false)
public abstract class FavoriteMealDatabase extends RoomDatabase {

    private static volatile FavoriteMealDatabase instance;  // Use volatile for thread safety

    public abstract MealDao favoriteMealDao();  // Consistent naming

    public static synchronized FavoriteMealDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (FavoriteMealDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    FavoriteMealDatabase.class, "favorite_meal_database")
                            .fallbackToDestructiveMigration()  // Consider migration strategy
                            .build();
                }
            }
        }
        return instance;
    }
}
