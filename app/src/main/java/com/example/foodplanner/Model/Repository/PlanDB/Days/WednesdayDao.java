package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WednesdayDao {
    @Insert
    void insertMeal(Wednesday meal);

    @Update
    void updateMeal(Wednesday meal);

    @Query("DELETE FROM Wednesday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Wednesday")
    LiveData<List<Wednesday>> getAllMeals();
}
