package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.MealPresenter;
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
