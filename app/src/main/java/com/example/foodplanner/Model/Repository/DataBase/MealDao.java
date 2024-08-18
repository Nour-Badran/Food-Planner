package com.example.foodplanner.Model.Repository.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodplanner.Model.MealEntity;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insertMeal(MealEntity meal);

    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>> getAllMeals();

    @Query("DELETE FROM meals WHERE idMeal = :mealId")
    void delete(String mealId);
}
