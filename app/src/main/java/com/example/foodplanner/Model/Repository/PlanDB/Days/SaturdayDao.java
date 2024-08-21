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
public interface SaturdayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Saturday meal);

    @Update
    void updateMeal(Saturday meal);

    @Query("DELETE FROM Saturday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Saturday")
    LiveData<List<Saturday>> getAllMeals();

    @Query("DELETE FROM Saturday")
    void deleteAllSaturdayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Saturday> meals);

    @Transaction
    default void updateSaturdayMeals(List<Saturday> newMeals) {
        deleteAllSaturdayMeals();;
        insertMeals(newMeals);
    }
}
