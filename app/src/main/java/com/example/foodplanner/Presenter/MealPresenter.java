package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Model.Repository.DataBase.OnMealExistsCallback;

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
    boolean isMealExistsByName(String mealName);
    }
