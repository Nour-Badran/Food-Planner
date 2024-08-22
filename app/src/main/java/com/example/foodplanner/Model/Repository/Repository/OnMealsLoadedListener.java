package com.example.foodplanner.Model.Repository.Repository;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;

import java.util.List;

public interface OnMealsLoadedListener {
    void onMealsLoaded(List<MealEntity> meals);
}
