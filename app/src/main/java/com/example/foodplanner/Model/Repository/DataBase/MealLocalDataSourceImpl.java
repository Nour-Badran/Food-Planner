package com.example.foodplanner.Model.Repository.DataBase;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.MealEntity;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private final MealDao mealDao;

    public MealLocalDataSourceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public void insertMeal(MealEntity meal) {
        // Use ExecutorService or other async mechanisms if needed
        new Thread(() -> mealDao.insertMeal(meal)).start();
    }

    @Override
    public void deleteMeal(String mealId) {
        // Use ExecutorService or other async mechanisms if needed
        new Thread(() -> mealDao.delete(mealId)).start();
    }

    @Override
    public LiveData<List<MealEntity>> getAllMeals() {
        return mealDao.getAllMeals();  // Return LiveData directly
    }
}
