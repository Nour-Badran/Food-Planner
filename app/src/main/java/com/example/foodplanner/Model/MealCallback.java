package com.example.foodplanner.Model;

public interface MealCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
