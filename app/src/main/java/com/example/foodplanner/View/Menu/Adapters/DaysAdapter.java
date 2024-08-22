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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayViewHolder> {
    private final List<String> daysOfWeek;
    private final Context context;
    private OnAddClickListener onAddClickListener;
    private final List<String> dayDates;
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
        this.dayDates = calculateWeekDates();
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String dayName = daysOfWeek.get(position);
        String dayDate = dayDates.get(position);
        holder.tvDayName.setText(dayName + " (" + dayDate + ")");

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
                presenter.updateMondayMeals(mondayMeals);
                break;

            case "Tuesday":
                tuesdayMeals = convertMealEntitiesToTuesdays(weeklyMeals.get(dayIndex));
                presenter.updateTuesdayMeals(tuesdayMeals);
                break;

            case "Wednesday":
                wednesdayMeals = convertMealEntitiesToWednesdays(weeklyMeals.get(dayIndex));
                presenter.updateWednesdayMeals(wednesdayMeals);
                break;

            case "Thursday":
                thursdayMeals = convertMealEntitiesToThursdays(weeklyMeals.get(dayIndex));
                presenter.updateThursdayMeals(thursdayMeals);
                break;

            case "Friday":
                fridayMeals = convertMealEntitiesToFridays(weeklyMeals.get(dayIndex));
                presenter.updateFridayMeals(fridayMeals);
                break;

            case "Saturday":
                saturdayMeals = convertMealEntitiesToSaturdays(weeklyMeals.get(dayIndex));
                presenter.updateSaturdayMeals(saturdayMeals);
                break;

            case "Sunday":
                sundayMeals = convertMealEntitiesToSundays(weeklyMeals.get(dayIndex));
                presenter.updateSundayMeals(sundayMeals);
                break;
        }
    }

    private List<String> calculateWeekDates() {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        // Find the start of the week (Monday)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }

        // Add dates for the week
        for (String day : daysOfWeek) {
            dates.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return dates;
    }
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
