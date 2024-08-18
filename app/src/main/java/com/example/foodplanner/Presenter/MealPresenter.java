package com.example.foodplanner.Presenter;

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
}
