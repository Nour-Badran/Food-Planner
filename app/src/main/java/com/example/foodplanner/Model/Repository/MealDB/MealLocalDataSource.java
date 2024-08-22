package com.example.foodplanner.Model.Repository.MealDB;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

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
    void updateMeals(List<MealEntity> newMeals);


    // Daily meal methods
    void insertMondayMeal(Monday meal);
    void updateMondayMeal(Monday meal);
    void deleteMondayMeal(String mealId);
    LiveData<List<Monday>> getMondayMeals();
    void deleteAllMondayMeals();
    void insertMondayMeals(List<Monday> meals);  // New method
    @Transaction
    void updateMondayMeals(List<Monday> newMeals);  // New method

    void insertTuesdayMeal(Tuesday meal);
    void updateTuesdayMeal(Tuesday meal);
    void deleteTuesdayMeal(String mealId);
    LiveData<List<Tuesday>> getTuesdayMeals();
    void deleteAllTuesdayMeals();
    void insertTuesdayMeals(List<Tuesday> meals);  // New method
    @Transaction
    void updateTuesdayMeals(List<Tuesday> newMeals);  // New method

    void insertWednesdayMeal(Wednesday meal);
    void updateWednesdayMeal(Wednesday meal);
    void deleteWednesdayMeal(String mealId);
    LiveData<List<Wednesday>> getWednesdayMeals();
    void deleteAllWednesdayMeals();
    void insertWednesdayMeals(List<Wednesday> meals);  // New method
    @Transaction
    void updateWednesdayMeals(List<Wednesday> newMeals);  // New method

    void insertThursdayMeal(Thursday meal);
    void updateThursdayMeal(Thursday meal);
    void deleteThursdayMeal(String mealId);
    LiveData<List<Thursday>> getThursdayMeals();
    void deleteAllThursdayMeals();
    void insertThursdayMeals(List<Thursday> meals);  // New method
    @Transaction
    void updateThursdayMeals(List<Thursday> newMeals);  // New method

    void insertFridayMeal(Friday meal);
    void updateFridayMeal(Friday meal);
    void deleteFridayMeal(String mealId);
    LiveData<List<Friday>> getFridayMeals();
    void deleteAllFridayMeals();
    void insertFridayMeals(List<Friday> meals);  // New method
    @Transaction
    void updateFridayMeals(List<Friday> newMeals);  // New method

    void insertSaturdayMeal(Saturday meal);
    void updateSaturdayMeal(Saturday meal);
    void deleteSaturdayMeal(String mealId);
    LiveData<List<Saturday>> getSaturdayMeals();
    void deleteAllSaturdayMeals();
    void insertSaturdayMeals(List<Saturday> meals);  // New method
    @Transaction
    void updateSaturdayMeals(List<Saturday> newMeals);  // New method

    void insertSundayMeal(Sunday meal);
    void updateSundayMeal(Sunday meal);
    void deleteSundayMeal(String mealId);
    LiveData<List<Sunday>> getSundayMeals();
    void deleteAllSundayMeals();
    void insertSundayMeals(List<Sunday> meals);  // New method
    @Transaction
    void updateSundayMeals(List<Sunday> newMeals);  // New method

}
