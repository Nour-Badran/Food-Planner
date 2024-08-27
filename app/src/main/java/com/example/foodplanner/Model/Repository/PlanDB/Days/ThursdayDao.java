package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;

import java.util.List;

@Dao
public interface ThursdayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Thursday meal);

    @Query("SELECT * FROM Thursday")
    LiveData<List<Thursday>> getAllMeals();

    @Query("DELETE FROM Thursday")
    void deleteAllThursdayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Thursday> meals);
}
