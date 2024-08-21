package com.example.foodplanner.Model.Repository.MealDB;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;

import java.util.List;

public interface MealLocalDataSource {
    void insertMeal(MealEntity meal);
    void deleteMeal(String mealId);
    LiveData<List<MealEntity>> getStoredMeals();
    void isMealExists(String mealId,OnMealExistsCallback callback);

    // Daily meal methods
    void insertMondayMeal(Monday meal);
    void updateMondayMeal(Monday meal);
    void deleteMondayMeal(String mealId);
    LiveData<List<Monday>> getMondayMeals();

    void insertTuesdayMeal(Tuesday meal);
    void updateTuesdayMeal(Tuesday meal);
    void deleteTuesdayMeal(String mealId);
    LiveData<List<Tuesday>> getTuesdayMeals();

    void insertWednesdayMeal(Wednesday meal);
    void updateWednesdayMeal(Wednesday meal);
    void deleteWednesdayMeal(String mealId);
    LiveData<List<Wednesday>> getWednesdayMeals();

    void insertThursdayMeal(Thursday meal);
    void updateThursdayMeal(Thursday meal);
    void deleteThursdayMeal(String mealId);
    LiveData<List<Thursday>> getThursdayMeals();

    void insertFridayMeal(Friday meal);
    void updateFridayMeal(Friday meal);
    void deleteFridayMeal(String mealId);
    LiveData<List<Friday>> getFridayMeals();

    void insertSaturdayMeal(Saturday meal);
    void updateSaturdayMeal(Saturday meal);
    void deleteSaturdayMeal(String mealId);
    LiveData<List<Saturday>> getSaturdayMeals();

    void insertSundayMeal(Sunday meal);
    void updateSundayMeal(Sunday meal);
    void deleteSundayMeal(String mealId);
    LiveData<List<Sunday>> getSundayMeals();
}
