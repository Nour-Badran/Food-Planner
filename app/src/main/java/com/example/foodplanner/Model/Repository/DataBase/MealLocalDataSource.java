package com.example.foodplanner.Model.Repository.DataBase;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.MealEntity;

import java.util.List;

public interface MealLocalDataSource {
    void insertMeal(MealEntity meal);
    void deleteMeal(String mealId);
    LiveData<List<MealEntity>> getAllMeals();
}
