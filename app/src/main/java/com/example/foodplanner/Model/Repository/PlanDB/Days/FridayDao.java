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
public interface FridayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Friday meal);

    @Update
    void updateMeal(Friday meal);

    @Query("DELETE FROM Friday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Friday")
    LiveData<List<Friday>> getAllMeals();

    @Query("DELETE FROM Friday")
    void deleteAllFridayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Friday> meals);

    @Transaction
    default void updateFridayMeals(List<Friday> newMeals) {
        deleteAllFridayMeals();;
        insertMeals(newMeals);
    }
}
