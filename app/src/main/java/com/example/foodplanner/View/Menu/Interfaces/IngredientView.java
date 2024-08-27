package com.example.foodplanner.View.Menu.Interfaces;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;

import java.util.List;

public interface IngredientView {
    void showIngredients(List<IngredientResponse.Ingredient> ingredients);
    void showError(String message);
}
