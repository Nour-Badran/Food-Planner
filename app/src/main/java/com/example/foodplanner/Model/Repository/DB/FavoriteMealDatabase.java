package com.example.foodplanner.Model.Repository.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.Model.Repository.MealDB.MealDao;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.FridayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.MondayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.SaturdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.SundayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.ThursdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.TuesdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.WednesdayDao;

@Database(entities = {MealEntity.class, Monday.class, Tuesday.class, Wednesday.class, Thursday.class, Friday.class, Saturday.class, Sunday.class}, version = 5, exportSchema = false)
public abstract class FavoriteMealDatabase extends RoomDatabase {

    private static FavoriteMealDatabase instance = null;

    public abstract MealDao favoriteMealDao();
    public abstract MondayDao mondayDao();
    public abstract TuesdayDao tuesdayDao();
    public abstract WednesdayDao wednesdayDao();
    public abstract ThursdayDao thursdayDao();
    public abstract FridayDao fridayDao();
    public abstract SaturdayDao saturdayDao();
    public abstract SundayDao sundayDao();
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
