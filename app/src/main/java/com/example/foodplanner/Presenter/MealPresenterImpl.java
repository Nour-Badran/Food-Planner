package com.example.foodplanner.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealDB.OnMealExistsCallback;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealCallback;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class MealPresenterImpl implements MealPresenter {

    private final MealView view;
    private final MealRepository repository;
    DataRepository dataRepository;
    public MealPresenterImpl(MealView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
        dataRepository = new DataRepository();
    }

    @Override
    public void loadRandomMealForDay(int dayIndex) {
        repository.loadRandomMeal(new MealCallback<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                view.showMeal(meal);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError("Failed to load meal for day " + dayIndex + ": " + errorMessage);
            }
        });
    }

    @Override
    public void addRandomMealForDay(int dayIndex) {
        repository.loadRandomMeal(new MealCallback<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                view.addMeal(meal);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError("Failed to load meal for day " + dayIndex + ": " + errorMessage);
            }
        });
    }

    @Override
    public void loadRandomMeal() {
        repository.loadRandomMeal(new MealCallback<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                view.showMeal(meal);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getAllMeals() {
        repository.getAllMealsFromRemote(new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }


    @Override
    public void getMealByArea(String area) {
        repository.getMealByArea(area, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }


    @Override
    public LiveData<List<MealEntity>> getFavMeals() {
        return repository.getStoredMeals();
    }

    @Override
    public void isMealExists(String mealId, OnMealExistsCallback callback) {
        repository.isMealExists(mealId, new OnMealExistsCallback() {
            @Override
            public void onResult(boolean exists) {
                callback.onResult(exists);
            }
        });
    }

    @Override
    public void insertMeal(MealEntity meal) {
        repository.insertMeal(meal);
    }
    @Override
    public void updateMeals(List<MealEntity> newMeals) {
        repository.updateMeals(newMeals);
    }

    @Override
    public void deleteMeal(MealEntity meal) {
        repository.deleteMeal(meal.getIdMeal());
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        repository.getMealsByCategory(categoryName, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        repository.getMealsByIngredient(ingredient, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void searchMeals(String mealName) {
        repository.searchMeals(mealName, new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void getRandomMeals() {
        repository.getRandomMeals(new MealCallback<List<MealEntity>>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showError("No meals found");
                } else {
                    view.showMeals(meals);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    // Monday methods
    @Override
    public void insertMondayMeal(Monday meal) {
        repository.insertMondayMeal(meal);
    }

    @Override
    public void updateMondayMeal(Monday meal) {
        repository.updateMondayMeal(meal);
    }

    @Override
    public void deleteMondayMeal(String mealId) {
        repository.deleteMondayMeal(mealId);
    }

    @Override
    public LiveData<List<Monday>> getMondayMeals() {
        return repository.getMondayMeals();
    }

    @Override
    public void deleteAllMondayMeals() {
        repository.deleteAllMondayMeals();
    }
    @Override
    public void updateMondayMeals(List<Monday> newMeals) {
        repository.updateMondayMeals(newMeals);
    }

    // Tuesday methods
    @Override
    public void insertTuesdayMeal(Tuesday meal) {
        repository.insertTuesdayMeal(meal);
    }

    @Override
    public void updateTuesdayMeal(Tuesday meal) {
        repository.updateTuesdayMeal(meal);
    }

    @Override
    public void deleteTuesdayMeal(String mealId) {
        repository.deleteTuesdayMeal(mealId);
    }

    @Override
    public LiveData<List<Tuesday>> getTuesdayMeals() {
        return repository.getTuesdayMeals();
    }

    @Override
    public void deleteAllTuesdayMeals() {
        repository.deleteAllTuesdayMeals();
    }
    @Override
    public void updateTuesdayMeals(List<Tuesday> newMeals) {
        repository.updateTuesdayMeals(newMeals);
    }

    // Wednesday methods
    @Override
    public void insertWednesdayMeal(Wednesday meal) {
        repository.insertWednesdayMeal(meal);
    }

    @Override
    public void updateWednesdayMeal(Wednesday meal) {
        repository.updateWednesdayMeal(meal);
    }

    @Override
    public void deleteWednesdayMeal(String mealId) {
        repository.deleteWednesdayMeal(mealId);
    }

    @Override
    public LiveData<List<Wednesday>> getWednesdayMeals() {
        return repository.getWednesdayMeals();
    }

    @Override
    public void deleteAllWednesdayMeals() {
        repository.deleteAllWednesdayMeals();
    }
    @Override
    public void updateWednesdayMeals(List<Wednesday> newMeals) {
        repository.updateWednesdayMeals(newMeals);
    }

    // Thursday methods
    @Override
    public void insertThursdayMeal(Thursday meal) {
        repository.insertThursdayMeal(meal);
    }

    @Override
    public void updateThursdayMeal(Thursday meal) {
        repository.updateThursdayMeal(meal);
    }

    @Override
    public void deleteThursdayMeal(String mealId) {
        repository.deleteThursdayMeal(mealId);
    }

    @Override
    public LiveData<List<Thursday>> getThursdayMeals() {
        return repository.getThursdayMeals();
    }

    @Override
    public void deleteAllThursdayMeals() {
        repository.deleteAllThursdayMeals();
    }
    @Override
    public void updateThursdayMeals(List<Thursday> newMeals) {
        repository.updateThursdayMeals(newMeals);
    }

    // Friday methods
    @Override
    public void insertFridayMeal(Friday meal) {
        repository.insertFridayMeal(meal);
    }

    @Override
    public void updateFridayMeal(Friday meal) {
        repository.updateFridayMeal(meal);
    }

    @Override
    public void deleteFridayMeal(String mealId) {
        repository.deleteFridayMeal(mealId);
    }

    @Override
    public LiveData<List<Friday>> getFridayMeals() {
        return repository.getFridayMeals();
    }

    @Override
    public void deleteAllFridayMeals() {
        repository.deleteAllFridayMeals();
    }
    @Override
    public void updateFridayMeals(List<Friday> newMeals) {
        repository.updateFridayMeals(newMeals);
    }

    // Saturday methods
    @Override
    public void insertSaturdayMeal(Saturday meal) {
        repository.insertSaturdayMeal(meal);
    }

    @Override
    public void updateSaturdayMeal(Saturday meal) {
        repository.updateSaturdayMeal(meal);
    }

    @Override
    public void deleteSaturdayMeal(String mealId) {
        repository.deleteSaturdayMeal(mealId);
    }

    @Override
    public LiveData<List<Saturday>> getSaturdayMeals() {
        return repository.getSaturdayMeals();
    }

    @Override
    public void deleteAllSaturdayMeals() {
        repository.deleteAllSaturdayMeals();
    }
    @Override
    public void updateSaturdayMeals(List<Saturday> newMeals) {
        repository.updateSaturdayMeals(newMeals);
    }

    // Sunday methods
    @Override
    public void insertSundayMeal(Sunday meal) {
        repository.insertSundayMeal(meal);
    }

    @Override
    public void updateSundayMeal(Sunday meal) {
        repository.updateSundayMeal(meal);
    }

    @Override
    public void deleteSundayMeal(String mealId) {
        repository.deleteSundayMeal(mealId);
    }

    @Override
    public LiveData<List<Sunday>> getSundayMeals() {
        return repository.getSundayMeals();
    }

    @Override
    public void deleteAllSundayMeals() {
        repository.deleteAllSundayMeals();
    }
    @Override
    public void updateSundayMeals(List<Sunday> newMeals) {
        repository.updateSundayMeals(newMeals);
    }
}