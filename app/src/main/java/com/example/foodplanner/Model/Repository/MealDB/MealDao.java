package com.example.foodplanner.Model.Repository.MealDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealEntity meal);
    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>>getAllMeals();

    @Query("DELETE FROM meals WHERE idMeal = :mealId")
    void delete(String mealId);
    // Check if a meal exists by its ID
    @Query("SELECT COUNT(*) > 0 FROM meals WHERE idMeal = :mealId")
    boolean isMealExists(String mealId);


}
