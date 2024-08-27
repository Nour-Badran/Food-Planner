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
import com.example.foodplanner.Model.Repository.Repository.MealRepository;

import java.util.List;

public class UpdateMealsPresenter {
    private final MealRepository repository;

    public UpdateMealsPresenter(MealRepository repository) {
        this.repository = repository;
    }

    public void updateMeals(List<MealEntity> newMeals) {
        repository.updateMeals(newMeals);
    }
    public LiveData<List<MealEntity>> getFavMeals() {
        return repository.getStoredMeals();
    }
    public void deleteMeal(MealEntity meal) {
        repository.deleteMeal(meal.getIdMeal());
    }
    public void insertMeal(MealEntity meal) {
        repository.insertMeal(meal);
    }
    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        repository.isMealExists(mealId, new OnMealExistsCallback() {
            @Override
            public void onResult(boolean exists) {
                callback.onResult(exists);
            }
        });
    }
    public void updateMondayMeals(List<Monday> newMeals) {
        repository.updateMondayMeals(newMeals);
    }
    public void updateTuesdayMeals(List<Tuesday> newMeals) {
        repository.updateTuesdayMeals(newMeals);
    }
    public void updateWednesdayMeals(List<Wednesday> newMeals) {
        repository.updateWednesdayMeals(newMeals);
    }
    public void updateThursdayMeals(List<Thursday> newMeals) {
        repository.updateThursdayMeals(newMeals);
    }
    public void updateFridayMeals(List<Friday> newMeals) {
        repository.updateFridayMeals(newMeals);
    }
    public void updateSaturdayMeals(List<Saturday> newMeals) {
        repository.updateSaturdayMeals(newMeals);
    }
    public void updateSundayMeals(List<Sunday> newMeals) {
        repository.updateSundayMeals(newMeals);
    }

    // Monday methods

    public void insertMondayMeal(Monday meal) {
        repository.insertMondayMeal(meal);
    }
    public LiveData<List<Monday>> getMondayMeals() {
        return repository.getMondayMeals();
    }

    // Tuesday methods
    public void insertTuesdayMeal(Tuesday meal) {
        repository.insertTuesdayMeal(meal);
    }

    public LiveData<List<Tuesday>> getTuesdayMeals() {
        return repository.getTuesdayMeals();
    }

    // Wednesday methods
    public void insertWednesdayMeal(Wednesday meal) {
        repository.insertWednesdayMeal(meal);
    }
    public LiveData<List<Wednesday>> getWednesdayMeals() {
        return repository.getWednesdayMeals();
    }

    // Thursday methods
    public void insertThursdayMeal(Thursday meal) {
        repository.insertThursdayMeal(meal);
    }

    public LiveData<List<Thursday>> getThursdayMeals() {
        return repository.getThursdayMeals();
    }

    // Friday methods
    public void insertFridayMeal(Friday meal) {
        repository.insertFridayMeal(meal);
    }
    public LiveData<List<Friday>> getFridayMeals() {
        return repository.getFridayMeals();
    }

    // Saturday methods
    public void insertSaturdayMeal(Saturday meal) {
        repository.insertSaturdayMeal(meal);
    }
    public LiveData<List<Saturday>> getSaturdayMeals() {
        return repository.getSaturdayMeals();
    }

    // Sunday methods
    public void insertSundayMeal(Sunday meal) {
        repository.insertSundayMeal(meal);
    }
    public LiveData<List<Sunday>> getSundayMeals() {
        return repository.getSundayMeals();
    }
}
