package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SaturdayDao {
    @Insert
    void insertMeal(Saturday meal);

    @Update
    void updateMeal(Saturday meal);

    @Query("DELETE FROM Saturday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Saturday")
    LiveData<List<Saturday>> getAllMeals();
}
