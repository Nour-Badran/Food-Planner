package com.example.foodplanner.View.Menu.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.OnAddClickListener;
import com.example.foodplanner.View.Menu.Interfaces.OnFabClickListener;
import com.example.foodplanner.View.Menu.Interfaces.OnMealClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayViewHolder> {
    private final List<String> daysOfWeek;
    private final Context context;
    private OnAddClickListener onAddClickListener;
    private final List<List<MealEntity>> weeklyMeals;
    MealPresenter presenter;
    MealRepository repository;
    private OnFabClickListener onFabClickListener;
    private OnMealClickListener onMealClickListener;


    public DaysAdapter(Context context, List<String> daysOfWeek, List<List<MealEntity>> weeklyMeals,MealPresenter presenter,MealRepository repository) {
        this.context = context;
        this.daysOfWeek = daysOfWeek;
        this.weeklyMeals = weeklyMeals;
        this.presenter = presenter;
        this.repository = repository;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.tvDayName.setText(daysOfWeek.get(position));

        List<MealEntity> mealsForDay = weeklyMeals.get(position);
        PlannedMealsAdapter plannedMealsAdapter = new PlannedMealsAdapter(context, mealsForDay, position,presenter);  // Pass position as dayIndex

        plannedMealsAdapter.setOnDeleteListener((dayIndex, mealPosition) -> {
            MealEntity mealToDelete = weeklyMeals.get(dayIndex).get(mealPosition);

            mealsForDay.remove(mealPosition);
            plannedMealsAdapter.notifyItemRemoved(mealPosition);
            plannedMealsAdapter.notifyItemRangeChanged(mealPosition, mealsForDay.size());
            removeMealFromDatabase(dayIndex, mealToDelete);

        });

        plannedMealsAdapter.setOnMealClickListener(meal -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
            }
        });

        plannedMealsAdapter.setOnFabClickListener(meal -> {
            if (onFabClickListener != null) {
                onFabClickListener.onFabClick(meal);
            }
        });
        holder.rvMealsForDay.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvMealsForDay.setAdapter(plannedMealsAdapter);

        holder.fab.setOnClickListener(v -> {
            if (onAddClickListener != null) {
                onAddClickListener.onAddClick(position);
            }
        });
    }
    public void setOnFabClickListener(OnFabClickListener listener) {
        this.onFabClickListener = listener;
    }
    public void setOnMealClickListener(OnMealClickListener listener) {
        this.onMealClickListener = listener;
    }
    private void removeMealFromDatabase(int dayIndex, MealEntity meal) {
        List<Monday> mondayMeals;
        List<Tuesday> tuesdayMeals;
        List<Wednesday> wednesdayMeals;
        List<Thursday> thursdayMeals;
        List<Friday> fridayMeals;
        List<Saturday> saturdayMeals;
        List<Sunday> sundayMeals;

        switch (daysOfWeek.get(dayIndex)) {
            case "Monday":
                mondayMeals = convertMealEntitiesToMondays(weeklyMeals.get(dayIndex));
                repository.updateMondayMeals(mondayMeals);
                break;

            case "Tuesday":
                tuesdayMeals = convertMealEntitiesToTuesdays(weeklyMeals.get(dayIndex));
                repository.updateTuesdayMeals(tuesdayMeals);
                break;

            case "Wednesday":
                wednesdayMeals = convertMealEntitiesToWednesdays(weeklyMeals.get(dayIndex));
                repository.updateWednesdayMeals(wednesdayMeals);
                break;

            case "Thursday":
                thursdayMeals = convertMealEntitiesToThursdays(weeklyMeals.get(dayIndex));
                repository.updateThursdayMeals(thursdayMeals);
                break;

            case "Friday":
                fridayMeals = convertMealEntitiesToFridays(weeklyMeals.get(dayIndex));
                repository.updateFridayMeals(fridayMeals);
                break;

            case "Saturday":
                saturdayMeals = convertMealEntitiesToSaturdays(weeklyMeals.get(dayIndex));
                repository.updateSaturdayMeals(saturdayMeals);
                break;

            case "Sunday":
                sundayMeals = convertMealEntitiesToSundays(weeklyMeals.get(dayIndex));
                repository.updateSundayMeals(sundayMeals);
                break;
        }
    }

// Convert methods for each day

    private List<Monday> convertMealEntitiesToMondays(List<MealEntity> mealEntities) {
        List<Monday> mondays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Monday monday = new Monday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            mondays.add(monday);
        }
        return mondays;
    }

    private List<Tuesday> convertMealEntitiesToTuesdays(List<MealEntity> mealEntities) {
        List<Tuesday> tuesdays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Tuesday tuesday = new Tuesday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            tuesdays.add(tuesday);
        }
        return tuesdays;
    }

    private List<Wednesday> convertMealEntitiesToWednesdays(List<MealEntity> mealEntities) {
        List<Wednesday> wednesdays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Wednesday wednesday = new Wednesday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            wednesdays.add(wednesday);
        }
        return wednesdays;
    }

    private List<Thursday> convertMealEntitiesToThursdays(List<MealEntity> mealEntities) {
        List<Thursday> thursdays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Thursday thursday = new Thursday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            thursdays.add(thursday);
        }
        return thursdays;
    }

    private List<Friday> convertMealEntitiesToFridays(List<MealEntity> mealEntities) {
        List<Friday> fridays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Friday friday = new Friday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            fridays.add(friday);
        }
        return fridays;
    }

    private List<Saturday> convertMealEntitiesToSaturdays(List<MealEntity> mealEntities) {
        List<Saturday> saturdays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Saturday saturday = new Saturday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            saturdays.add(saturday);
        }
        return saturdays;
    }

    private List<Sunday> convertMealEntitiesToSundays(List<MealEntity> mealEntities) {
        List<Sunday> sundays = new ArrayList<>();
        for (MealEntity mealEntity : mealEntities) {
            Sunday sunday = new Sunday(mealEntity.getIdMeal(), mealEntity.getStrMeal(), mealEntity.getStrMealThumb());
            sundays.add(sunday);
        }
        return sundays;
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName;
        RecyclerView rvMealsForDay;
        FloatingActionButton fab;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tv_day_name);
            rvMealsForDay = itemView.findViewById(R.id.rv_meals_for_day);
            fab = itemView.findViewById(R.id.add);
        }
    }
}
