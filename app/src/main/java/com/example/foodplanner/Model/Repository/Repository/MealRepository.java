package com.example.foodplanner.Model.Repository.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.Repository.DataBase.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.DataBase.MealLocalDataSource;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.POJO.IngredientResponse;

import java.util.List;

public class MealRepository {
    private final MealLocalDataSource localDataSource;
    private final MealRemoteDataSource remoteDataSource;

    public MealRepository(MealLocalDataSource localDataSource, MealRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public LiveData<List<MealEntity>> getStoredMeals()
    {
        return localDataSource.getStoredMeals();
    }
    public void insertMeal(MealEntity meal) {
        localDataSource.insertMeal(meal);
    }

    // Delete a meal from the local database
    public void deleteMeal(String mealId) {
        localDataSource.deleteMeal(mealId);
    }
    // Load a random meal from the remote source
    public void loadRandomMeal(MealCallback<MealEntity> callback) {
        remoteDataSource.loadRandomMeal(callback);
    }

    // Get all meals from the remote source
    public void getAllMealsFromRemote(MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getAllMeals(callback);
    }

    // Get meals by area from the remote source
    public void getMealByArea(String area, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealByArea(area, callback);
    }

    // Search meals by name from the remote source
    public void searchMeals(String mealName, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.searchMeals(mealName, callback);
    }

    // Get ingredients from the remote source
    public void getIngredients(MealCallback<List<IngredientResponse.Ingredient>> callback) {
        remoteDataSource.getIngredients(callback);
    }

    // Get ingredients by substring from the remote source
    public void getIngredientsBySubstring(String substring, MealCallback<List<IngredientResponse.Ingredient>> callback) {
        remoteDataSource.getIngredientsBySubstring(substring, callback);
    }

    // Get meals by category from the remote source
    public void getMealsByCategory(String categoryName, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealsByCategory(categoryName, callback);
    }

    // Get meals by ingredient from the remote source
    public void getMealsByIngredient(String ingredient, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealsByIngredient(ingredient, callback);
    }

    // Get random meals from the remote source
    public void getRandomMeals(MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getRandomMeals(callback);
    }

    // Get categories from the remote source
    public void getCategories(MealCallback<List<CategoryResponse.Category>> callback) {
        remoteDataSource.getCategories(callback);
    }
    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        localDataSource.isMealExists(mealId, callback);
    }
    public boolean isMealExistsByName(String mealName) {
        return localDataSource.isMealExistsByName(mealName);
    }
}
