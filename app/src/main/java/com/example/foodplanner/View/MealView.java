package com.example.foodplanner.View;

import com.example.foodplanner.Model.MealEntity;

import java.util.List;

public interface MealView {
    void showMeal(MealEntity meal);
    void showMealDetails(MealEntity meal);
    void showError(String message);

    void showMeals(List<MealEntity> meals);
}
