package com.example.foodplanner.Presenter;

public interface MealPresenter {
    void loadRandomMeal();
    void searchMeals(String query);
    void loadCategories();
    void loadCountries();

    void getRandomMeals();
}
