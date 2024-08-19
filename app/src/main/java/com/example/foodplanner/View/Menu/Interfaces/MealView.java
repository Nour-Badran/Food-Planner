package com.example.foodplanner.View.Menu.Interfaces;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.POJO.MealEntity;

import java.util.List;

public interface MealView {
//    void showMealsFromDatabase(List<MealEntity> mealsInDb);
    void showMeal(MealEntity meal);
    void showMealDetails(MealEntity meal);
    void showError(String message);
    void showMessage(String message);
    void showCategories(List<CategoryResponse.Category> categories);
    void showMeals(List<MealEntity> meals);
    void showIngredients(List<IngredientResponse.Ingredient> ingredients);
    void getMealsByCategory(String categoryName);
}
