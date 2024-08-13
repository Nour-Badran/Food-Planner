package com.example.foodplanner.View.Menu;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.MealEntity;

import java.util.List;

public interface MealView {
    void showMeal(MealEntity meal);
    void showMealDetails(MealEntity meal);
    void showError(String message);
    void showCategories(List<CategoryResponse.Category> categories);
    void showMeals(List<MealEntity> meals);
    void getMealsByCategory(String categoryName);
}
