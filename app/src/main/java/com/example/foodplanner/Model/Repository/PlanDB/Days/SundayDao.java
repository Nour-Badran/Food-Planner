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
public interface SundayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Sunday meal);

    @Update
    void updateMeal(Sunday meal);

    @Query("DELETE FROM Sunday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Sunday")
    LiveData<List<Sunday>> getAllMeals();

    @Query("DELETE FROM Sunday")
    void deleteAllSundayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Sunday> meals);

    @Transaction
    default void updateSundayMeals(List<Sunday> newMeals) {
        deleteAllSundayMeals();
        insertMeals(newMeals);
    }
}
