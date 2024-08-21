package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TuesdayDao {
    @Insert
    void insertMeal(Tuesday meal);

    @Update
    void updateMeal(Tuesday meal);

    @Query("DELETE FROM Tuesday WHERE id = :mealId")
    void deleteMealById(String mealId);

    @Query("SELECT * FROM Tuesday")
    LiveData<List<Tuesday>> getAllMeals();
}
