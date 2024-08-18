package com.example.foodplanner.Presenter;

import android.util.Log;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealCallback;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.MealModel;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class MealPresenterImpl implements MealPresenter {

    private final MealView view;
    private final MealModel model;

    public MealPresenterImpl(MealView view, MealModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadRandomMeal() {
        model.loadRandomMeal(new MealCallback<MealEntity>() {
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
        model.getAllMeals(new MealCallback<List<MealEntity>>() {
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
        model.getMealByArea(area, new MealCallback<List<MealEntity>>() {
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
        model.getIngredients(new MealCallback<List<IngredientResponse.Ingredient>>() {
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
        model.getIngredientsBySubstring(substring, new MealCallback<List<IngredientResponse.Ingredient>>() {
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
    public void getMealsByCategory(String categoryName) {
        model.getMealsByCategory(categoryName, new MealCallback<List<MealEntity>>() {
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
        model.getMealsByIngredient(ingredient, new MealCallback<List<MealEntity>>() {
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
        model.searchMeals(mealName, new MealCallback<List<MealEntity>>() {
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
    public void loadCategories() {

    }

    @Override
    public void loadCountries() {

    }

    @Override
    public void getRandomMeals() {
        model.getRandomMeals(new MealCallback<List<MealEntity>>() {
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
        model.getCategories(new MealCallback<List<CategoryResponse.Category>>() {
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

}
