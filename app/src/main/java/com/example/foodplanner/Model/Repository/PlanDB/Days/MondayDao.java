package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MondayDao {
    @Insert
    void insertMeal(Monday meal);

    @Update
    void updateMeal(Monday meal);

    @Query("DELETE FROM Monday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Monday")
    LiveData<List<Monday>> getAllMeals();

    @Query("DELETE FROM Monday")
    void deleteAllMondayMeals();
}
