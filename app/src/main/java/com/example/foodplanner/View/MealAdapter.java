package com.example.foodplanner.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealEntity> meals = new ArrayList<>();

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealEntity meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());

        // Load image using Glide or another image loading library
        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .into(holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
        }
    }
}
