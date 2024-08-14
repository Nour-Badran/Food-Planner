package com.example.foodplanner.View.Menu;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;

public interface OnIngredientClickListener {
    void onIngredientClick(IngredientResponse.Ingredient ingredient);
}
