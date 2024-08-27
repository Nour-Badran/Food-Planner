package com.example.foodplanner.View.Menu.Interfaces;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;

import java.util.List;

public interface MealView {
    void showMeal(MealEntity meal);
    void showError(String message);
    void showMeals(List<MealEntity> meals);
    void addMeal(MealEntity meal);
}
