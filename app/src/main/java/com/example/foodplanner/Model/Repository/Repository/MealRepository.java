package com.example.foodplanner.Model.Repository.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.Repository.MealDB.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.MealDB.MealLocalDataSource;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;

import java.util.List;

public class MealRepository implements MealRepositoryInterface {
    private final MealLocalDataSource localDataSource;
    private final MealRemoteDataSource remoteDataSource;

    public MealRepository(MealLocalDataSource localDataSource, MealRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public LiveData<List<MealEntity>> getStoredMeals()
    {
        return localDataSource.getStoredMeals();
    }
    @Override
    public void insertMeal(MealEntity meal) {
        localDataSource.insertMeal(meal);
    }

    // Delete a meal from the local database
    @Override
    public void deleteMeal(String mealId) {
        localDataSource.deleteMeal(mealId);
    }
    // Load a random meal from the remote source
    @Override
    public void getMealDetailsById(String mealId, MealCallback<MealEntity> callback) {
        remoteDataSource.getMealDetailsById(mealId, callback);
    }
    @Override
    public void loadRandomMeal(MealCallback<MealEntity> callback) {
        remoteDataSource.loadRandomMeal(callback);
    }

    // Get all meals from the remote source
    @Override
    public void getAllMealsFromRemote(MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getAllMeals(callback);
    }

    // Get meals by area from the remote source
    @Override
    public void getMealByArea(String area, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealByArea(area, callback);
    }

    // Search meals by name from the remote source
    @Override
    public void searchMeals(String mealName, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.searchMeals(mealName, callback);
    }

    // Get ingredients from the remote source
    @Override
    public void getIngredients(MealCallback<List<IngredientResponse.Ingredient>> callback) {
        remoteDataSource.getIngredients(callback);
    }

    // Get ingredients by substring from the remote source
    @Override
    public void getIngredientsBySubstring(String substring, MealCallback<List<IngredientResponse.Ingredient>> callback) {
        remoteDataSource.getIngredientsBySubstring(substring, callback);
    }

    // Get meals by category from the remote source
    @Override
    public void getMealsByCategory(String categoryName, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealsByCategory(categoryName, callback);
    }

    // Get meals by ingredient from the remote source
    @Override
    public void getMealsByIngredient(String ingredient, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealsByIngredient(ingredient, callback);
    }

    // Get random meals from the remote source
    @Override
    public void getRandomMeals(MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getRandomMeals(callback);
    }

    // Get categories from the remote source
    @Override
    public void getCategories(MealCallback<List<CategoryResponse.Category>> callback) {
        remoteDataSource.getCategories(callback);
    }
    @Override
    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        localDataSource.isMealExists(mealId, callback);
    }
    // Monday methods
    public void insertMondayMeal(Monday meal) {
        localDataSource.insertMondayMeal(meal);
    }

    public void updateMondayMeal(Monday meal) {
        localDataSource.updateMondayMeal(meal);
    }

    public void deleteMondayMeal(String mealId) {
        localDataSource.deleteMondayMeal(mealId);
    }

    public LiveData<List<Monday>> getMondayMeals() {
        return localDataSource.getMondayMeals();
    }

    // Tuesday methods

    public void insertTuesdayMeal(Tuesday meal) {
        localDataSource.insertTuesdayMeal(meal);
    }

    public void updateTuesdayMeal(Tuesday meal) {
        localDataSource.updateTuesdayMeal(meal);
    }

    public void deleteTuesdayMeal(String mealId) {
        localDataSource.deleteTuesdayMeal(mealId);
    }

    public LiveData<List<Tuesday>> getTuesdayMeals() {
        return localDataSource.getTuesdayMeals();
    }

    // Wednesday methods
    public void insertWednesdayMeal(Wednesday meal) {
        localDataSource.insertWednesdayMeal(meal);
    }

    public void updateWednesdayMeal(Wednesday meal) {
        localDataSource.updateWednesdayMeal(meal);
    }

    public void deleteWednesdayMeal(String mealId) {
        localDataSource.deleteWednesdayMeal(mealId);
    }

    public LiveData<List<Wednesday>> getWednesdayMeals() {
        return localDataSource.getWednesdayMeals();
    }

    // Thursday methods
    public void insertThursdayMeal(Thursday meal) {
        localDataSource.insertThursdayMeal(meal);
    }

    public void updateThursdayMeal(Thursday meal) {
        localDataSource.updateThursdayMeal(meal);
    }

    public void deleteThursdayMeal(String mealId) {
        localDataSource.deleteThursdayMeal(mealId);
    }

    public LiveData<List<Thursday>> getThursdayMeals() {
        return localDataSource.getThursdayMeals();
    }

    // Friday methods
    public void insertFridayMeal(Friday meal) {
        localDataSource.insertFridayMeal(meal);
    }

    public void updateFridayMeal(Friday meal) {
        localDataSource.updateFridayMeal(meal);
    }

    public void deleteFridayMeal(String mealId) {
        localDataSource.deleteFridayMeal(mealId);
    }

    public LiveData<List<Friday>> getFridayMeals() {
        return localDataSource.getFridayMeals();
    }

    // Saturday methods
    public void insertSaturdayMeal(Saturday meal) {
        localDataSource.insertSaturdayMeal(meal);
    }

    public void updateSaturdayMeal(Saturday meal) {
        localDataSource.updateSaturdayMeal(meal);
    }

    public void deleteSaturdayMeal(String mealId) {
        localDataSource.deleteSaturdayMeal(mealId);
    }

    public LiveData<List<Saturday>> getSaturdayMeals() {
        return localDataSource.getSaturdayMeals();
    }

    // Sunday methods
    public void insertSundayMeal(Sunday meal) {
        localDataSource.insertSundayMeal(meal);
    }

    public void updateSundayMeal(Sunday meal) {
        localDataSource.updateSundayMeal(meal);
    }

    public void deleteSundayMeal(String mealId) {
        localDataSource.deleteSundayMeal(mealId);
    }

    public LiveData<List<Sunday>> getSundayMeals() {
        return localDataSource.getSundayMeals();
    }
}
