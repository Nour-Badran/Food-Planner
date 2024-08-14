package com.example.foodplanner.Presenter;

import android.util.Log;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.FavoriteMealDatabase;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealResponse;
import com.example.foodplanner.View.Menu.MealView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealPresenterImpl implements MealPresenter {
    private MealView view;
    private MealApi mealApi;
    private FavoriteMealDatabase appDatabase;

    public MealPresenterImpl(MealView view, MealApi mealApi) {
        this.view = view;
        this.mealApi = mealApi;
        //this.appDatabase = appDatabase;
    }

    @Override
    public void loadRandomMeal() {
        mealApi.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    Log.d("MealPresenterImpl", "Response JSON: " + new Gson().toJson(mealResponse));
                    List<MealEntity> meals = mealResponse.getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        view.showMeal(meals.get(0));
                    } else {
                        view.showError("No meals found");
                    }
                } else {
                    view.showError("Failed to load meal: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getAllMeals() {
        mealApi.getAllMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        view.showError("No meals found");
                    } else {
                        view.showMeals(meals);
                    }
                } else {
                    view.showError("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getMealByArea(String area) {
        mealApi.getMealsByArea(area).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        view.showError("No meals found");
                    } else {
                        view.showMeals(meals);
                    }
                } else {
                    view.showError("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getIngredients() {
        mealApi.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                Log.d("MealPresenterImpl", "Request URL: " + call.request().url());
                Log.d("MealPresenterImpl", "Response Code: " + response.code());
                Log.d("MealPresenterImpl", "Response Body: " + new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    IngredientResponse ingredientResponse = response.body();
                    List<IngredientResponse.Ingredient> ingredients = ingredientResponse.getIngredients();
                    if (ingredients == null || ingredients.isEmpty()) {
                        view.showError("No ingredients found");
                    } else {
                        view.showIngredients(ingredients);
                    }
                } else {
                    view.showError("Failed to find ingredients: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Log.e("MealPresenterImpl", "Error: " + t.getMessage());
                view.showError("Error: " + t.getMessage());
            }
        });
    }


    @Override
    public void getMealsByCategory(String categoryName) {
        mealApi.getMealsByCategory(categoryName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        view.showError("No meals found");
                    } else {
                        view.showMeals(meals);
                    }
                } else {
                    view.showError("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }
    @Override
    public void searchMeals(String mealName) {
        mealApi.searchMeals(mealName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        view.showError("No meals found");
                    } else {
                        view.showMeals(meals);
                    }
                } else {
                    view.showError("Failed to search meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    public void getRandomMeals(){
        mealApi.getRandomMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    // Handle null list explicitly
                    if (meals == null || meals.isEmpty()) {
                        view.showError("No meals found");
                    } else {
                        view.showMeals(meals);
                    }
                } else {
                    // Handle non-successful response
                    view.showError("Failed to search meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

            }
        });
    }



    @Override
    public void loadCategories() {

    }

    @Override
    public void loadCountries() {

    }

    @Override
    public void getCategories() {
        mealApi.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();
                    List<CategoryResponse.Category> categories = categoryResponse.getCategories();

                    // Handle null list explicitly
                    if (categories == null || categories.isEmpty()) {
                        view.showError("No categories found");
                    } else {
                        view.showCategories(categories);
                    }
                } else {
                    // Handle non-successful response
                    view.showError("Failed to connect: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }

    // Implement other methods similarly
}

