package com.example.foodplanner.View.Menu.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.MealViewHolder> {

    private final Context context;
    private final List<MealEntity> meals;

    public PlannedMealsAdapter(Context context, List<MealEntity> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealEntity meal = meals.get(position);
        Log.d("MealsAdapter", "Binding meal: " + meal.getStrMeal());
        holder.tvMealName.setText(meal.getStrMeal());

        // Load meal image using Glide or any image loading library
//        Glide.with(context).load(meal.getStrMealThumb()).into(holder.ivMealImage);
        holder.fab.setImageResource(R.drawable.swap_calls_24dp_e8eaed_fill0_wght400_grad0_opsz24);
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.ivMealImage);
    }

    @Override
    public int getItemCount() {
        Log.d("MealsAdapter", "Binding meal: " + meals.size());

        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMealImage;
        TextView tvMealName;
        FloatingActionButton fab;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMealImage = itemView.findViewById(R.id.mealImage);
            tvMealName = itemView.findViewById(R.id.mealName);
            fab = itemView.findViewById(R.id.fab);

        }
    }
}
