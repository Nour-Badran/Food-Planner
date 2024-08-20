package com.example.foodplanner.Model.Repository.DataBase;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.POJO.MealEntity;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private final MealDao mealDao;

    private LiveData<List<MealEntity>> storedMeals;

    public MealLocalDataSourceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
        this.storedMeals = mealDao.getAllMeals();
    }
    @Override
    public void insertMeal(MealEntity meal) {
        new Thread(() -> mealDao.insertMeal(meal)).start();
    }

    @Override
    public void deleteMeal(String mealId) {
        new Thread(() -> mealDao.delete(mealId)).start();
    }

    @Override
    public LiveData<List<MealEntity>> getStoredMeals() {
        return storedMeals;
    }

    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        new Thread(() -> {
            boolean exists = mealDao.isMealExists(mealId);
            callback.onResult(exists);
        }).start();
    }


}
