package com.example.foodplanner.View.Menu.Interfaces;

import com.example.foodplanner.Model.POJO.CategoryResponse;

import java.util.List;

public interface CategoriesView {
    void showCategories(List<CategoryResponse.Category> categories);
    void showError(String message);
}
