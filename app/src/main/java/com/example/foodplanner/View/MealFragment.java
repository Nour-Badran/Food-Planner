package com.example.foodplanner.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.R;

public class MealFragment extends Fragment {
    private MealEntity meal;

    public MealFragment(MealEntity meal) {
        this.meal = meal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView mealImage = view.findViewById(R.id.mealImage);
        TextView mealName = view.findViewById(R.id.mealName);
        //TextView mealDesc = view.findViewById(R.id.mealDescription);
        TextView mealCategory = view.findViewById(R.id.mealCategory);
        TextView mealArea = view.findViewById(R.id.mealArea);

        mealName.setText("Meal Name: " + meal.getStrMeal());
        //mealDesc.setText(meal.getStrInstructions());
        mealCategory.setText("Category: " + meal.getStrCategory()); // Assuming getStrCategory() is defined in MealEntity
        mealArea.setText("Area: " + meal.getStrArea()); // Assuming getStrArea() is defined in MealEntity

        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
    }
}
