package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.Repository.Repository.OnMealsLoadedListener;

import java.util.List;

public class DataPresenter {
    private final DataRepository dataRepository;

    public DataPresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void saveMealsToFirebase(List<MealEntity> meals, String email, String day) {
        dataRepository.saveToFirebase(meals, email, day);
    }

    public void loadFromFirebase(String email, String esmElFolder, OnMealsLoadedListener listener)
    {
        dataRepository.loadFromFirebase(email,esmElFolder,listener);
    }
}
