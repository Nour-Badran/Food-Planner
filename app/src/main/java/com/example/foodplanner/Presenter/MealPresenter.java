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
    void getRandomMeals();
    void getMealsByCategory(String categoryName);
    void getMealsByIngredient(String ingredient);
    void getAllMeals();
    void getMealByArea(String area);
    void loadRandomMealForDay(int dayIndex);
    void addRandomMealForDay(int dayIndex);
}
