package com.example.foodplanner.Model.POJO;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;

import java.util.List;

public class MealResponse {
    private List<MealEntity> meals;

    public List<MealEntity> getMeals() {
        return meals;
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
    }
}
