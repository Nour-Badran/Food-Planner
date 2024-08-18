package com.example.foodplanner.View.Menu.Interfaces;

import com.airbnb.lottie.L;
import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealEntity;

import java.util.List;

public interface MealView {
    void showMeal(MealEntity meal);
    void showMealDetails(MealEntity meal);
    void showError(String message);
    void showCategories(List<CategoryResponse.Category> categories);
    void showMeals(List<MealEntity> meals);
    void showIngredients(List<IngredientResponse.Ingredient> ingredients);
    void getMealsByCategory(String categoryName);
}
