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
    public LiveData<List<MealEntity>> getFavMeals();
    void insertMeal(MealEntity meal);
    void deleteMeal(MealEntity meal);
    public void updateMeals(List<MealEntity> newMeals);
    void isMealExists(String mealId, OnMealExistsCallback callback);
    void loadRandomMealForDay(int dayIndex);
    void addRandomMealForDay(int dayIndex);

    // Monday methods
    void insertMondayMeal(Monday meal);
    void updateMondayMeal(Monday meal);
    void deleteMondayMeal(String mealId);
    LiveData<List<Monday>> getMondayMeals();
    public void deleteAllMondayMeals();
    public void updateMondayMeals(List<Monday> newMeals);
        // Tuesday methods

    void insertTuesdayMeal(Tuesday meal);
    void updateTuesdayMeal(Tuesday meal);
    void deleteTuesdayMeal(String mealId);
    LiveData<List<Tuesday>> getTuesdayMeals();
    public void deleteAllTuesdayMeals();
    public void updateTuesdayMeals(List<Tuesday> newMeals);

        // Wednesday methods

    void insertWednesdayMeal(Wednesday meal);
    void updateWednesdayMeal(Wednesday meal);
    void deleteWednesdayMeal(String mealId);
    LiveData<List<Wednesday>> getWednesdayMeals();
    public void deleteAllWednesdayMeals();
    public void updateWednesdayMeals(List<Wednesday> newMeals);

        // Thursday methods

    void insertThursdayMeal(Thursday meal);
    void updateThursdayMeal(Thursday meal);
    void deleteThursdayMeal(String mealId);
    LiveData<List<Thursday>> getThursdayMeals();
    public void deleteAllThursdayMeals();
    public void updateThursdayMeals(List<Thursday> newMeals);

        // Friday methods
    void insertFridayMeal(Friday meal);
    void updateFridayMeal(Friday meal);
    void deleteFridayMeal(String mealId);
    LiveData<List<Friday>> getFridayMeals();
    public void deleteAllFridayMeals();
    public void updateFridayMeals(List<Friday> newMeals);

        // Saturday methods

    void insertSaturdayMeal(Saturday meal);
    void updateSaturdayMeal(Saturday meal);
    void deleteSaturdayMeal(String mealId);
    LiveData<List<Saturday>> getSaturdayMeals();
    public void deleteAllSaturdayMeals();
    public void updateSaturdayMeals(List<Saturday> newMeals);

        // Sunday methods

    void insertSundayMeal(Sunday meal);
    void updateSundayMeal(Sunday meal);
    void deleteSundayMeal(String mealId);
    LiveData<List<Sunday>> getSundayMeals();
    public void deleteAllSundayMeals();
    public void updateSundayMeals(List<Sunday> newMeals);
    }
