package com.example.foodplanner.Presenter;

import android.util.Log;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Model.Repository.DataBase.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class MealPresenterImpl implements MealPresenter {

    private final MealView view;
    private final MealRepository repository;

    public MealPresenterImpl(MealView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
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
    public void getIngredients() {
        repository.getIngredients(new MealCallback<List<IngredientResponse.Ingredient>>() {
            @Override
            public void onSuccess(List<IngredientResponse.Ingredient> ingredients) {
                if (ingredients == null || ingredients.isEmpty()) {
                    view.showError("No ingredients found");
                } else {
                    view.showIngredients(ingredients);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getIngredientsBySubstring(String substring) {
        repository.getIngredientsBySubstring(substring, new MealCallback<List<IngredientResponse.Ingredient>>() {
            @Override
            public void onSuccess(List<IngredientResponse.Ingredient> ingredients) {
                if (ingredients == null || ingredients.isEmpty()) {
                    view.showError("No ingredients found");
                } else {
                    view.showIngredients(ingredients);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getFavMeals() {
        // Retrieve all meals and manually notify view
        repository.getStoredMeals().observeForever(meals -> {
            if (meals == null || meals.isEmpty()) {
                view.showError("No favorite meals found");
            } else {
                view.showMeals(meals);
            }
        });
    }
    @Override
    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        repository.isMealExists(mealId, new OnMealExistsCallback() {
            @Override
            public void onResult(boolean exists) {
                callback.onResult(exists);
            }
        });
    }

    @Override
    public boolean isMealExistsByName(String mealName){
        return repository.isMealExistsByName(mealName);
    }

    @Override
    public void insertMeal(MealEntity meal) {
        repository.insertMeal(meal);
    }

    @Override
    public void deleteMeal(MealEntity meal) {
        repository.deleteMeal(meal.getIdMeal());
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
    public void getCategories() {
        repository.getCategories(new MealCallback<List<CategoryResponse.Category>>() {
            @Override
            public void onSuccess(List<CategoryResponse.Category> categories) {
                if (categories == null || categories.isEmpty()) {
                    view.showError("No categories found");
                } else {
                    view.showCategories(categories);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void loadCountries() {
        // Implement loadCountries with repository when available
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
