package com.example.foodplanner.Model.Repository.PlanDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "planned_meals")
public class PlannedMealEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mealId;
    private String dayOfWeek; // E.g., "Monday", "Tuesday"
    private String mealTime;  // E.g., "Breakfast", "Lunch", "Dinner"
    private Date date;        // Use Date type for storing the date of the meal

    // Constructor
    public PlannedMealEntity(String mealId, String dayOfWeek, String mealTime, Date date) {
        this.mealId = mealId;
        this.dayOfWeek = dayOfWeek;
        this.mealTime = mealTime;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

