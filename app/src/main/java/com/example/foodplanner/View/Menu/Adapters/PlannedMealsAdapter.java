package com.example.foodplanner.View.Menu.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.OnDeleteListener;
import com.example.foodplanner.View.Menu.Interfaces.OnFabClickListener;
import com.example.foodplanner.View.Menu.Interfaces.OnMealClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.MealViewHolder> {

    private final Context context;
    private final List<MealEntity> meals;
    private OnDeleteListener onDeleteListener;
    private int dayIndex;
    MealPresenter presenter;
    private OnFabClickListener onFabClickListener;
    private OnMealClickListener onMealClickListener;


    public PlannedMealsAdapter(Context context, List<MealEntity> meals, int dayIndex,MealPresenter presenter) {
        this.context = context;
        this.meals = meals;
        this.dayIndex = dayIndex;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }
    public void setOnFabClickListener(OnFabClickListener listener) {
        this.onFabClickListener = listener;
    }
    public void setOnMealClickListener(OnMealClickListener listener) {
        this.onMealClickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealEntity meal = meals.get(position);
        Log.d("MealsAdapter", "Binding meal: " + meal.getStrMeal());
        holder.tvMealName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.ivMealImage);
        int defaultColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
        holder.fabFav.setBackgroundTintList(ColorStateList.valueOf(defaultColor));

        // Check if meal exists in the database
        presenter.isMealExists(meal.getIdMeal(), exists -> {
            int color = exists ? ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor) : defaultColor;
            holder.fabFav.setBackgroundTintList(ColorStateList.valueOf(color));
        });

        holder.fabFav.setOnClickListener(v -> {
            if (onFabClickListener != null) {
                onFabClickListener.onFabClick(meal);
                presenter.isMealExists(meal.getIdMeal(), exists -> {
                    int color = exists ? defaultColor : ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor);
                    holder.fabFav.setBackgroundTintList(ColorStateList.valueOf(color));
                });
            }
        });

        holder.ivMealImage.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
                //addMealToCalendar(meal);
            }
        });

        holder.addToCalendar.setOnClickListener(v -> {
            addMealToCalendar(meal);
        });

        holder.fabExit.setOnClickListener(v -> {
            if (onDeleteListener != null) {
                onDeleteListener.onMealDelete(dayIndex, position);  // Pass dayIndex and position
            }
        });

    }
    private void addMealToCalendar(MealEntity meal) {
        // Show date and time picker dialogs
        showDateTimePickerDialog(meal);
    }

    private void showDateTimePickerDialog(MealEntity meal) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Set the selected date
                    calendar.set(year, monthOfYear, dayOfMonth);

                    // Show time picker dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                            (timeView, hourOfDay, minute) -> {
                                // Set the selected time
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                // Add the meal to the calendar with the selected date and time
                                addEventToCalendar(meal, calendar);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true); // 24-hour format
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void addEventToCalendar(MealEntity meal, Calendar calendar) {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_CALENDAR}, 100);
            return;
        }

        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1); // Assuming you are using the primary calendar
        values.put(CalendarContract.Events.TITLE, meal.getStrMeal());
        values.put(CalendarContract.Events.DESCRIPTION, "Meal Planning");
        values.put(CalendarContract.Events.EVENT_LOCATION, "Home");
        values.put(CalendarContract.Events.DTSTART, calendar.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, calendar.getTimeInMillis() + 60 * 60 * 1000); // 1 hour later as placeholder
        values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        if (uri != null) {
            long eventID = Long.parseLong(uri.getLastPathSegment());
            Log.d("Calendar", "Event added with ID: " + eventID + meal.getStrMeal());
        }
    }

    @Override
    public int getItemCount() {
        Log.d("MealsAdapter", "Binding meal: " + meals.size());
        return meals.size();
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.onDeleteListener = listener;
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMealImage;
        TextView tvMealName;
        FloatingActionButton fabExit;
        FloatingActionButton fabFav;
        Button addToCalendar;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMealImage = itemView.findViewById(R.id.mealImage);
            tvMealName = itemView.findViewById(R.id.mealName);
            fabFav = itemView.findViewById(R.id.fab);
            fabExit = itemView.findViewById(R.id.fabexit);
            addToCalendar = itemView.findViewById(R.id.addToCalendarButton);
        }
    }
}
