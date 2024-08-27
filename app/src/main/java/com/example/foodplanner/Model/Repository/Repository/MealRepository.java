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

    public void updateMeals(List<MealEntity> newMeals) {
        localDataSource.updateMeals(newMeals);
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

    @Override
    public void deleteMeal(String mealId) {
        localDataSource.deleteMeal(mealId);
    }
    @Override
    public void loadRandomMeal(MealCallback<MealEntity> callback) {
        remoteDataSource.loadRandomMeal(callback);
    }

    @Override
    public void getAllMealsFromRemote(MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getAllMeals(callback);
    }

    @Override
    public void getMealByArea(String area, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealByArea(area, callback);
    }

    @Override
    public void searchMeals(String mealName, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.searchMeals(mealName, callback);
    }

    @Override
    public void getIngredients(MealCallback<List<IngredientResponse.Ingredient>> callback) {
        remoteDataSource.getIngredients(callback);
    }

    @Override
    public void getIngredientsBySubstring(String substring, MealCallback<List<IngredientResponse.Ingredient>> callback) {
        remoteDataSource.getIngredientsBySubstring(substring, callback);
    }

    @Override
    public void getMealsByCategory(String categoryName, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealsByCategory(categoryName, callback);
    }

    @Override
    public void getMealsByIngredient(String ingredient, MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getMealsByIngredient(ingredient, callback);
    }

    @Override
    public void getRandomMeals(MealCallback<List<MealEntity>> callback) {
        remoteDataSource.getRandomMeals(callback);
    }

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

    public LiveData<List<Monday>> getMondayMeals() {
        return localDataSource.getMondayMeals();
    }

    public void updateMondayMeals(List<Monday> newMeals) {
        localDataSource.updateMondayMeals(newMeals);
    }
    // Tuesday methods

    public void insertTuesdayMeal(Tuesday meal) {
        localDataSource.insertTuesdayMeal(meal);
    }

    public LiveData<List<Tuesday>> getTuesdayMeals() {
        return localDataSource.getTuesdayMeals();
    }
    public void updateTuesdayMeals(List<Tuesday> newMeals) {
        localDataSource.updateTuesdayMeals(newMeals);
    }
        // Wednesday methods
    public void insertWednesdayMeal(Wednesday meal) {
        localDataSource.insertWednesdayMeal(meal);
    }

    public LiveData<List<Wednesday>> getWednesdayMeals() {
        return localDataSource.getWednesdayMeals();
    }
    public void updateWednesdayMeals(List<Wednesday> newMeals) {
        localDataSource.updateWednesdayMeals(newMeals);
    }
        // Thursday methods
    public void insertThursdayMeal(Thursday meal) {
        localDataSource.insertThursdayMeal(meal);
    }

    public LiveData<List<Thursday>> getThursdayMeals() {
        return localDataSource.getThursdayMeals();
    }
    public void updateThursdayMeals(List<Thursday> newMeals) {
        localDataSource.updateThursdayMeals(newMeals);
    }
    // Friday methods
    public void insertFridayMeal(Friday meal) {
        localDataSource.insertFridayMeal(meal);
    }

    public LiveData<List<Friday>> getFridayMeals() {
        return localDataSource.getFridayMeals();
    }
    public void updateFridayMeals(List<Friday> newMeals) {
        localDataSource.updateFridayMeals(newMeals);
    }
        // Saturday methods
    public void insertSaturdayMeal(Saturday meal) {
        localDataSource.insertSaturdayMeal(meal);
    }

    public LiveData<List<Saturday>> getSaturdayMeals() {
        return localDataSource.getSaturdayMeals();
    }

    public void updateSaturdayMeals(List<Saturday> newMeals) {
        localDataSource.updateSaturdayMeals(newMeals);
    }
        // Sunday methods
    public void insertSundayMeal(Sunday meal) {
        localDataSource.insertSundayMeal(meal);
    }

    public LiveData<List<Sunday>> getSundayMeals() {
        return localDataSource.getSundayMeals();
    }
    public void updateSundayMeals(List<Sunday> newMeals) {
        localDataSource.updateSundayMeals(newMeals);
    }}
