package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.View.Menu.Interfaces.CategoriesView;
import com.example.foodplanner.View.Menu.Interfaces.IngredientView;

import java.util.List;

public class IngredientPresenter {
    private final IngredientView view;
    private final MealRepository repository;

    public IngredientPresenter(IngredientView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

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
}
