package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.View.Menu.Interfaces.CategoriesView;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class CategoryPresenter {

    private final CategoriesView view;
    private final MealRepository repository;

    public CategoryPresenter(CategoriesView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

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
}
