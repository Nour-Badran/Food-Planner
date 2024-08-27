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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.UpdateMealsPresenter;
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
    UpdateMealsPresenter updateMealsPresenter;
    private OnFabClickListener onFabClickListener;
    private OnMealClickListener onMealClickListener;

    public PlannedMealsAdapter(Context context, List<MealEntity> meals, int dayIndex,UpdateMealsPresenter updateMealsPresenter) {
        this.context = context;
        this.meals = meals;
        this.dayIndex = dayIndex;
        this.updateMealsPresenter = updateMealsPresenter;
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
        holder.tvMealName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.ivMealImage);

        int defaultColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
        holder.fabFav.setBackgroundTintList(ColorStateList.valueOf(defaultColor));

        // Check if meal exists in the database
        updateMealsPresenter.isMealExists(meal.getIdMeal(), exists -> {
            int color = exists ? ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor) : defaultColor;
            holder.fabFav.setBackgroundTintList(ColorStateList.valueOf(color));
        });

        holder.fabFav.setOnClickListener(v -> {
            if (onFabClickListener != null) {
                onFabClickListener.onFabClick(meal);
                updateMealsPresenter.isMealExists(meal.getIdMeal(), exists -> {
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

                    TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                            (timeView, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                showReminderTimePicker(meal, calendar);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true); //
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showReminderTimePicker(MealEntity meal, Calendar eventCalendar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Set Reminder");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_set_reminder, null);
        builder.setView(dialogView);

        NumberPicker numberPickerHours = dialogView.findViewById(R.id.numberPickerHours);
        NumberPicker numberPickerMinutes = dialogView.findViewById(R.id.numberPickerMinutes);
        NumberPicker numberPickerSeconds = dialogView.findViewById(R.id.numberPickerSeconds);

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);

        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);

        numberPickerSeconds.setMinValue(0);
        numberPickerSeconds.setMaxValue(59);

        builder.setPositiveButton("OK", (dialog, which) -> {
            int hours = numberPickerHours.getValue();
            int minutes = numberPickerMinutes.getValue();
            int seconds = numberPickerSeconds.getValue();

            int totalSeconds = hours * 3600 + minutes * 60 + seconds;
            addEventToCalendar(meal, eventCalendar, totalSeconds);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }


    private void addEventToCalendar(MealEntity meal, Calendar calendar, int reminderSeconds) {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_CALENDAR}, 100);
            return;
        }

        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, meal.getStrMeal());
        values.put(CalendarContract.Events.DESCRIPTION, "Meal Planning");
        values.put(CalendarContract.Events.EVENT_LOCATION, "Home");
        values.put(CalendarContract.Events.DTSTART, calendar.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, calendar.getTimeInMillis() + 60 * 60 * 1000);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        if (uri != null) {
            long eventID = Long.parseLong(uri.getLastPathSegment());

            ContentValues reminderValues = new ContentValues();
            reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventID);
            reminderValues.put(CalendarContract.Reminders.MINUTES, reminderSeconds / 60);
            reminderValues.put(CalendarContract.Reminders.MINUTES, reminderSeconds / 60);
            reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);

            Uri reminderUri = cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues);
            if (reminderUri != null) {
                Log.d("Calendar", "Event added with ID: " + eventID + ", Reminder set.");
                Toast.makeText(context, "Event added with ID: " + eventID + " Meal: " + meal.getStrMeal(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
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
