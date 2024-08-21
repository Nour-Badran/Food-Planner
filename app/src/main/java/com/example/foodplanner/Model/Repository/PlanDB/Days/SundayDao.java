package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SundayDao {
    @Insert
    void insertMeal(Sunday meal);

    @Update
    void updateMeal(Sunday meal);

    @Query("DELETE FROM Sunday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Sunday")
    LiveData<List<Sunday>> getAllMeals();
}
