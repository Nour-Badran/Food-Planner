package com.example.foodplanner.View;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;

import java.util.List;

public class MealSelectionDialogFragment extends DialogFragment {

    private List<MealEntity> meals;
    private OnMealSelectedListener listener;

    public MealSelectionDialogFragment(List<MealEntity> meals, OnMealSelectedListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Meal")
                .setItems(meals.stream().map(MealEntity::getStrMeal).toArray(String[]::new), (dialog, which) -> {
                    if (listener != null) {
                        listener.onMealSelected(meals.get(which));
                    }
                });
        return builder.create();
    }

    public interface OnMealSelectedListener {
        void onMealSelected(MealEntity meal);
    }
}

