package com.example.foodplanner.Model.Repository.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.Model.POJO.MealEntity;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealEntity meal);
    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>>getAllMeals();

    @Query("DELETE FROM meals WHERE idMeal = :mealId")
    void delete(String mealId);

    @Query("SELECT * FROM meals WHERE idMeal = :mealId LIMIT 1")
    MealEntity checkMealExists(String mealId);

    // Check if a meal exists by its ID
    @Query("SELECT COUNT(*) > 0 FROM meals WHERE idMeal = :mealId")
    boolean isMealExists(String mealId);

    // Alternatively, check if a meal exists by its name
    @Query("SELECT COUNT(*) > 0 FROM meals WHERE strMeal = :mealName")
    boolean isMealExistsByName(String mealName);
}
