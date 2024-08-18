package com.example.foodplanner.Model.Repository.MealRemoteDataSource;

public interface MealCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
