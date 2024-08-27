package com.example.foodplanner.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealDB.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class MealPresenterImpl implements MealPresenter {

    private final MealView view;
    private final MealRepository repository;
    DataRepository dataRepository;
    public MealPresenterImpl(MealView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
        dataRepository = new DataRepository();
    }

    @Override
    public void loadRandomMealForDay(int dayIndex) {
        repository.loadRandomMeal(new MealCallback<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                view.showMeal(meal);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError("Failed to load meal for day " + dayIndex + ": " + errorMessage);
            }
        });
    }

    @Override
    public void addRandomMealForDay(int dayIndex) {
        repository.loadRandomMeal(new MealCallback<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                view.addMeal(meal);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError("Failed to load meal for day " + dayIndex + ": " + errorMessage);
            }
        });
    }

    @Override
    public void loadRandomMeal() {
        repository.loadRandomMeal(new MealCallback<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                view.showMeal(meal);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getAllMeals() {
        repository.getAllMealsFromRemote(new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }


    @Override
    public void getMealByArea(String area) {
        repository.getMealByArea(area, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }
    @Override
    public void getMealsByCategory(String categoryName) {
        repository.getMealsByCategory(categoryName, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        repository.getMealsByIngredient(ingredient, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void searchMeals(String mealName) {
        repository.searchMeals(mealName, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getRandomMeals() {
        repository.getRandomMeals(new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }


}