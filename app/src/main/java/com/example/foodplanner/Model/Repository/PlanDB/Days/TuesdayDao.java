package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;

import java.util.List;

@Dao
public interface TuesdayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Tuesday meal);

    @Query("SELECT * FROM Tuesday")
    LiveData<List<Tuesday>> getAllMeals();
    @Query("DELETE FROM Tuesday")
    void deleteAllTuesdayMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Tuesday> meals);
}
