package com.example.foodplanner.Presenter;

import android.util.Log;

import com.example.foodplanner.Model.FavoriteMealDatabase;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealResponse;
import com.example.foodplanner.View.MealView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealPresenterImpl implements MealPresenter {
    private MealView view;
    private MealApi mealApi;
    private FavoriteMealDatabase appDatabase;

    public MealPresenterImpl(MealView view, MealApi mealApi, FavoriteMealDatabase appDatabase) {
        this.view = view;
        this.mealApi = mealApi;
        this.appDatabase = appDatabase;
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
                        Log.d("MealPresenterImpl", "Meals fetched: " + meals.size());
                        Log.d("MealPresenterImpl", "First meal details: " + new Gson().toJson(meals.get(0)));
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
    public void searchMeals(String query) {

    }

    @Override
    public void loadCategories() {

    }

    @Override
    public void loadCountries() {

    }

    // Implement other methods similarly
}

