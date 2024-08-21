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
public interface MondayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Monday meal);

    @Update
    void updateMeal(Monday meal);

    @Query("DELETE FROM Monday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Monday")
    LiveData<List<Monday>> getAllMeals();

    @Query("DELETE FROM Monday")
    void deleteAllMondayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Monday> meals); // Updated to use List<Monday>

    @Transaction
    default void updateMondayMeals(List<Monday> newMeals) {
        deleteAllMondayMeals();
        insertMeals(newMeals);
    }
}
