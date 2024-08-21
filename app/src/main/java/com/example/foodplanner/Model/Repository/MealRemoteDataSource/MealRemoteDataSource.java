package com.example.foodplanner.Model.Repository.MealRemoteDataSource;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.POJO.MealResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDataSource implements MealModel {

    private final MealApi mealApi;

    public MealRemoteDataSource(MealApi mealApi) {
        this.mealApi = mealApi;
    }
    @Override
    public void getMealDetailsById(String mealId, MealCallback<MealEntity> callback) {
        mealApi.getMealDetails(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealEntity> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        callback.onSuccess(meals.get(0));
                    } else {
                        callback.onFailure("No meal details found");
                    }
                } else {
                    callback.onFailure("Failed to load meal details: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadRandomMeal(MealCallback<MealEntity> callback) {
        mealApi.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealEntity> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        callback.onSuccess(meals.get(0));
                    } else {
                        callback.onFailure("No meals found");
                    }
                } else {
                    callback.onFailure("Failed to load meal: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getAllMeals(MealCallback<List<MealEntity>> callback) {
        mealApi.getAllMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealEntity> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        callback.onSuccess(meals);
                    } else {
                        callback.onFailure("No meals found");
                    }
                } else {
                    callback.onFailure("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getMealByArea(String area, MealCallback<List<MealEntity>> callback) {
        mealApi.getMealsByArea(area).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        callback.onFailure("No meals found");
                    } else {
                        callback.onSuccess(meals);
                    }
                } else {
                    callback.onFailure("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getIngredients(MealCallback<List<IngredientResponse.Ingredient>> callback) {
        mealApi.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IngredientResponse ingredientResponse = response.body();
                    List<IngredientResponse.Ingredient> ingredients = ingredientResponse.getIngredients();
                    if (ingredients == null || ingredients.isEmpty()) {
                        callback.onFailure("No ingredients found");
                    } else {
                        callback.onSuccess(ingredients);
                    }
                } else {
                    callback.onFailure("Failed to find ingredients: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getIngredientsBySubstring(String substring, MealCallback<List<IngredientResponse.Ingredient>> callback) {
        mealApi.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IngredientResponse ingredientResponse = response.body();
                    List<IngredientResponse.Ingredient> ingredients = ingredientResponse.getIngredients();
                    List<IngredientResponse.Ingredient> matchingIngredients = new ArrayList<>();
                    for(int i = 0; i < ingredients.size();i++)
                    {
                        String ingredientName = ingredients.get(i).getName();
                        if(ingredientName.toLowerCase().contains(substring.toLowerCase()))
                        {
                            matchingIngredients.add(ingredients.get(i));
                        }
                    }
                    if (matchingIngredients == null || matchingIngredients.isEmpty()) {
                        callback.onFailure("No ingredients found");
                    } else {
                        callback.onSuccess(matchingIngredients);
                    }
                } else {
                    callback.onFailure("Failed to find ingredients: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getMealsByCategory(String categoryName, MealCallback<List<MealEntity>> callback) {
        mealApi.getMealsByCategory(categoryName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        callback.onFailure("No meals found");
                    } else {
                        callback.onSuccess(meals);
                    }
                } else {
                    callback.onFailure("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getMealsByIngredient(String ingredient, MealCallback<List<MealEntity>> callback) {
        mealApi.getMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        callback.onFailure("No meals found");
                    } else {
                        callback.onSuccess(meals);
                    }
                } else {
                    callback.onFailure("Failed to find meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void searchMeals(String mealName, MealCallback<List<MealEntity>> callback) {
        mealApi.searchMeals(mealName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    if (meals == null || meals.isEmpty()) {
                        callback.onFailure("No meals found");
                    } else {
                        callback.onSuccess(meals);
                    }
                } else {
                    callback.onFailure("Failed to search meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getRandomMeals(MealCallback<List<MealEntity>> callback) {
        mealApi.getRandomMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    List<MealEntity> meals = mealResponse.getMeals();

                    // Handle null list explicitly
                    if (meals == null || meals.isEmpty()) {
                        callback.onFailure("No meals found");
                    } else {
                        callback.onSuccess(meals);
                    }
                } else {
                    // Handle non-successful response
                    callback.onFailure("Failed to search meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getCategories(MealCallback<List<CategoryResponse.Category>> callback) {
        mealApi.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();
                    List<CategoryResponse.Category> categories = categoryResponse.getCategories();

                    // Handle null list explicitly
                    if (categories == null || categories.isEmpty()) {
                        callback.onFailure("No categories found");
                    } else {
                        callback.onSuccess(categories);
                    }
                } else {
                    // Handle non-successful response
                    callback.onFailure("Failed to connect: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }
}

