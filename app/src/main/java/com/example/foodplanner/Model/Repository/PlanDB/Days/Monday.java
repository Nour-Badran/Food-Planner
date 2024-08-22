package com.example.foodplanner.Model.Repository.PlanDB.Days;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Monday")
public class Monday {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mealId;
    private String mealName;
    private String strMealThumb;


    // Constructors
    public Monday(String mealId, String mealName, String strMealThumb) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.strMealThumb = strMealThumb;
    }

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

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }
}
