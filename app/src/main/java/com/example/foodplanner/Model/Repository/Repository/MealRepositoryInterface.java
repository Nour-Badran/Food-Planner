package com.example.foodplanner.Model.Repository.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Model.Repository.DataBase.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;

import java.util.List;

public interface MealRepositoryInterface {
    LiveData<List<MealEntity>> getStoredMeals();

    void insertMeal(MealEntity meal);

    // Delete a meal from the local database
    void deleteMeal(String mealId);

    // Load a random meal from the remote source
    void getMealDetailsById(String mealId, MealCallback<MealEntity> callback);

    void loadRandomMeal(MealCallback<MealEntity> callback);

    // Get all meals from the remote source
    void getAllMealsFromRemote(MealCallback<List<MealEntity>> callback);

    // Get meals by area from the remote source
    void getMealByArea(String area, MealCallback<List<MealEntity>> callback);

    // Search meals by name from the remote source
    void searchMeals(String mealName, MealCallback<List<MealEntity>> callback);

    // Get ingredients from the remote source
    void getIngredients(MealCallback<List<IngredientResponse.Ingredient>> callback);

    // Get ingredients by substring from the remote source
    void getIngredientsBySubstring(String substring, MealCallback<List<IngredientResponse.Ingredient>> callback);

    // Get meals by category from the remote source
    void getMealsByCategory(String categoryName, MealCallback<List<MealEntity>> callback);

    // Get meals by ingredient from the remote source
    void getMealsByIngredient(String ingredient, MealCallback<List<MealEntity>> callback);

    // Get random meals from the remote source
    void getRandomMeals(MealCallback<List<MealEntity>> callback);

    // Get categories from the remote source
    void getCategories(MealCallback<List<CategoryResponse.Category>> callback);

    void isMealExists(String mealId, OnMealExistsCallback callback);

}
