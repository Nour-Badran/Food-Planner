package com.example.foodplanner.Model.Repository.MealRemoteDataSource;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.CountryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    @GET("randomselection.php")
    Call<MealResponse> getRandomMeals();

    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("categories.php")
    Call<CategoryResponse> getCategories();
    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();

    @GET("list.php?a=list")
    Call<CountryResponse> getCountries();
    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String mealName);

    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);
    @GET("filter.php?i")
    Call<MealResponse> getAllMeals();

}
