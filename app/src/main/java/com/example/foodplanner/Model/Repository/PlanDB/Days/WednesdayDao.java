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
public interface WednesdayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Wednesday meal);

    @Query("SELECT * FROM Wednesday")
    LiveData<List<Wednesday>> getAllMeals();

    @Query("DELETE FROM Wednesday")
    void deleteAllWednesdayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Wednesday> meals);
}
