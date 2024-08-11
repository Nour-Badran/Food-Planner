package com.example.foodplanner.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insertMeal(MealEntity meal);

    @Query("SELECT * FROM meals")
    List<MealEntity> getAllMeals();

    @Query("DELETE FROM meals WHERE idMeal = :mealId")
    void delete(String mealId);
}
