package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FridayDao {
    @Insert
    void insertMeal(Friday meal);

    @Update
    void updateMeal(Friday meal);

    @Query("DELETE FROM Friday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Friday")
    LiveData<List<Friday>> getAllMeals();
}
