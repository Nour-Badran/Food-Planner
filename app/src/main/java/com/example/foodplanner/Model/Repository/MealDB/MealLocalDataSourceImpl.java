package com.example.foodplanner.Model.Repository.MealDB;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealDao;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.MondayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.TuesdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.WednesdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.ThursdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.FridayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.SaturdayDao;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.SundayDao;

import java.util.List;
import androidx.room.Transaction;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private final MealDao mealDao;
    private final MondayDao mondayDao;
    private final TuesdayDao tuesdayDao;
    private final WednesdayDao wednesdayDao;
    private final ThursdayDao thursdayDao;
    private final FridayDao fridayDao;
    private final SaturdayDao saturdayDao;
    private final SundayDao sundayDao;
    public MealLocalDataSourceImpl(FavoriteMealDatabase database) {
        this.mealDao = database.favoriteMealDao();
        this.mondayDao = database.mondayDao();
        this.tuesdayDao = database.tuesdayDao();
        this.wednesdayDao = database.wednesdayDao();
        this.thursdayDao = database.thursdayDao();
        this.fridayDao = database.fridayDao();
        this.saturdayDao = database.saturdayDao();
        this.sundayDao = database.sundayDao();
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
        return mealDao.getAllMeals();
    }

    @Override
    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        new Thread(() -> {
            boolean exists = mealDao.isMealExists(mealId);
            callback.onResult(exists);
        }).start();
    }

    @Override
    public void updateMeals(List<MealEntity> newMeals) {
        new Thread(() -> {
            mealDao.updateMeals(newMeals);
        }).start();
    }

    // Monday methods
    @Override
    public void insertMondayMeal(Monday meal) {
        new Thread(() -> mondayDao.insertMeal(meal)).start();
    }

    @Override
    public LiveData<List<Monday>> getMondayMeals() {
        return mondayDao.getAllMeals();
    }

    @Override
    @Transaction
    public void updateMondayMeals(List<Monday> newMeals) {
        new Thread(() -> {
            mondayDao.deleteAllMondayMeals();
            mondayDao.insertMeals(newMeals);
        }).start();
    }

    // Tuesday methods
    @Override
    public void insertTuesdayMeal(Tuesday meal) {
        new Thread(() -> tuesdayDao.insertMeal(meal)).start();
    }

    @Override
    public LiveData<List<Tuesday>> getTuesdayMeals() {
        return tuesdayDao.getAllMeals();
    }

    @Override
    @Transaction
    public void updateTuesdayMeals(List<Tuesday> newMeals) {
        new Thread(() -> {
            tuesdayDao.deleteAllTuesdayMeals();
            tuesdayDao.insertMeals(newMeals);
        }).start();
    }

    // Wednesday methods
    @Override
    public void insertWednesdayMeal(Wednesday meal) {
        new Thread(() -> wednesdayDao.insertMeal(meal)).start();
    }

    @Override
    public LiveData<List<Wednesday>> getWednesdayMeals() {
        return wednesdayDao.getAllMeals();
    }

    @Override
    @Transaction
    public void updateWednesdayMeals(List<Wednesday> newMeals) {
        new Thread(() -> {
            wednesdayDao.deleteAllWednesdayMeals();
            wednesdayDao.insertMeals(newMeals);
        }).start();
    }

    // Thursday methods
    @Override
    public void insertThursdayMeal(Thursday meal) {
        new Thread(() -> thursdayDao.insertMeal(meal)).start();
    }

    @Override
    public LiveData<List<Thursday>> getThursdayMeals() {
        return thursdayDao.getAllMeals();
    }

    @Override
    @Transaction
    public void updateThursdayMeals(List<Thursday> newMeals) {
        new Thread(() -> {
            thursdayDao.deleteAllThursdayMeals();
            thursdayDao.insertMeals(newMeals);
        }).start();
    }

    // Friday methods
    @Override
    public void insertFridayMeal(Friday meal) {
        new Thread(() -> fridayDao.insertMeal(meal)).start();
    }

    @Override
    public LiveData<List<Friday>> getFridayMeals() {
        return fridayDao.getAllMeals();
    }

    @Override
    @Transaction
    public void updateFridayMeals(List<Friday> newMeals) {
        new Thread(() -> {
            fridayDao.deleteAllFridayMeals();
            fridayDao.insertMeals(newMeals);
        }).start();
    }

    // Saturday methods
    @Override
    public void insertSaturdayMeal(Saturday meal) {
        new Thread(() -> saturdayDao.insertMeal(meal)).start();
    }
    @Override
    public LiveData<List<Saturday>> getSaturdayMeals() {
        return saturdayDao.getAllMeals();
    }
    @Override
    @Transaction
    public void updateSaturdayMeals(List<Saturday> newMeals) {
        new Thread(() -> {
            saturdayDao.deleteAllSaturdayMeals();
            saturdayDao.insertMeals(newMeals);
        }).start();
    }

    // Sunday methods
    @Override
    public void insertSundayMeal(Sunday meal) {
        new Thread(() -> sundayDao.insertMeal(meal)).start();
    }
    @Override
    public LiveData<List<Sunday>> getSundayMeals() {
        return sundayDao.getAllMeals();
    }
    @Override
    @Transaction
    public void updateSundayMeals(List<Sunday> newMeals) {
        new Thread(() -> {
            sundayDao.deleteAllSundayMeals();
            sundayDao.insertMeals(newMeals);
        }).start();
    }

}
