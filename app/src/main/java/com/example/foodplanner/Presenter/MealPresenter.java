package com.example.foodplanner.Presenter;

public interface MealPresenter {
    void loadRandomMeal();
    void searchMeals(String query);
    void loadCategories();
    void loadCountries();
    void getCategories();
    void getRandomMeals();
    void getMealsByCategory(String categoryName);
    void getAllMeals();
    void getMealByArea(String area);
}
