package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ThursdayDao {
    @Insert
    void insertMeal(Thursday meal);

    @Update
    void updateMeal(Thursday meal);

    @Query("DELETE FROM Thursday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Thursday")
    LiveData<List<Thursday>> getAllMeals();
}
