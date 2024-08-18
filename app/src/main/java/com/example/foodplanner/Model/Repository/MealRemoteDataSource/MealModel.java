package com.example.foodplanner.Model.Repository.MealRemoteDataSource;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealEntity;

import java.util.List;

public interface MealModel {
    void loadRandomMeal(MealCallback<MealEntity> callback);
    void getAllMeals(MealCallback<List<MealEntity>> callback);
    void getMealByArea(String area, MealCallback<List<MealEntity>> callback);
    void getIngredients(MealCallback<List<IngredientResponse.Ingredient>> callback);
    void getIngredientsBySubstring(String substring, MealCallback<List<IngredientResponse.Ingredient>> callback);
    void getMealsByCategory(String categoryName, MealCallback<List<MealEntity>> callback);
    void getMealsByIngredient(String ingredient, MealCallback<List<MealEntity>> callback);
    void searchMeals(String mealName, MealCallback<List<MealEntity>> callback);
    void getRandomMeals(MealCallback<List<MealEntity>> callback);
    void getCategories(MealCallback<List<CategoryResponse.Category>> callback);
}
