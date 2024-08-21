package com.example.foodplanner.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealDB.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;

import java.util.List;

public interface MealPresenter {
    void loadRandomMeal();
    void searchMeals(String query);
    void loadCountries();
    void getCategories();
    void getRandomMeals();
    void getMealsByCategory(String categoryName);
    void getMealsByIngredient(String ingredient);
    void getAllMeals();
    void getMealByArea(String area);
    void getIngredients();
    void getIngredientsBySubstring(String substring);
    void getFavMeals();
    void insertMeal(MealEntity meal);
    void deleteMeal(MealEntity meal);
    void isMealExists(String mealId, OnMealExistsCallback callback);
    void loadRandomMealForDay(int dayIndex);
    void addRandomMealForDay(int dayIndex);

    // Monday methods
    void insertMondayMeal(Monday meal);
    void updateMondayMeal(Monday meal);
    void deleteMondayMeal(String mealId);
    LiveData<List<Monday>> getMondayMeals();

    // Tuesday methods
    void insertTuesdayMeal(Tuesday meal);
    void updateTuesdayMeal(Tuesday meal);
    void deleteTuesdayMeal(String mealId);
    LiveData<List<Tuesday>> getTuesdayMeals();

    // Wednesday methods
    void insertWednesdayMeal(Wednesday meal);
    void updateWednesdayMeal(Wednesday meal);
    void deleteWednesdayMeal(String mealId);
    LiveData<List<Wednesday>> getWednesdayMeals();

    // Thursday methods
    void insertThursdayMeal(Thursday meal);
    void updateThursdayMeal(Thursday meal);
    void deleteThursdayMeal(String mealId);
    LiveData<List<Thursday>> getThursdayMeals();

    // Friday methods
    void insertFridayMeal(Friday meal);
    void updateFridayMeal(Friday meal);
    void deleteFridayMeal(String mealId);
    LiveData<List<Friday>> getFridayMeals();

    // Saturday methods
    void insertSaturdayMeal(Saturday meal);
    void updateSaturdayMeal(Saturday meal);
    void deleteSaturdayMeal(String mealId);
    LiveData<List<Saturday>> getSaturdayMeals();

    // Sunday methods
    void insertSundayMeal(Sunday meal);
    void updateSundayMeal(Sunday meal);
    void deleteSundayMeal(String mealId);
    LiveData<List<Sunday>> getSundayMeals();
    }
