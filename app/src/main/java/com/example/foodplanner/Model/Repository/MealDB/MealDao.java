package com.example.foodplanner.Model.Repository.MealDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealEntity meal);
    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>>getAllMeals();

    @Query("DELETE FROM meals WHERE idMeal = :mealId")
    void delete(String mealId);

    @Query("SELECT COUNT(*) > 0 FROM meals WHERE idMeal = :mealId")
    boolean isMealExists(String mealId);

    @Query("DELETE FROM meals")
    void deleteAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<MealEntity> meals);

    @Transaction
    default void updateMeals(List<MealEntity> newMeals) {
        deleteAllMeals();;
        insertMeals(newMeals);
    }
}
