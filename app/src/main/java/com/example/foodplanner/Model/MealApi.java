package com.example.foodplanner.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("list.php?c=list")
    Call<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Call<CountryResponse> getCountries();
    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String mealName);

    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);
}
