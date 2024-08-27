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
    LiveData<List<Monday>> getMondayMeals();
    @Transaction
    void updateMondayMeals(List<Monday> newMeals);  // New method

    void insertTuesdayMeal(Tuesday meal);
    LiveData<List<Tuesday>> getTuesdayMeals();
    @Transaction
    void updateTuesdayMeals(List<Tuesday> newMeals);  // New method

    void insertWednesdayMeal(Wednesday meal);
    LiveData<List<Wednesday>> getWednesdayMeals();
    @Transaction
    void updateWednesdayMeals(List<Wednesday> newMeals);  // New method

    void insertThursdayMeal(Thursday meal);
    LiveData<List<Thursday>> getThursdayMeals();
    @Transaction
    void updateThursdayMeals(List<Thursday> newMeals);  // New method

    void insertFridayMeal(Friday meal);
    LiveData<List<Friday>> getFridayMeals();
    @Transaction
    void updateFridayMeals(List<Friday> newMeals);  // New method

    void insertSaturdayMeal(Saturday meal);
    LiveData<List<Saturday>> getSaturdayMeals();
    @Transaction
    void updateSaturdayMeals(List<Saturday> newMeals);  // New method

    void insertSundayMeal(Sunday meal);
    LiveData<List<Sunday>> getSundayMeals();
    @Transaction
    void updateSundayMeals(List<Sunday> newMeals);  // New method

}
