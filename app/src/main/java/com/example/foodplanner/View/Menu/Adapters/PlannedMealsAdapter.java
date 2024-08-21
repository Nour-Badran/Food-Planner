package com.example.foodplanner.View.Menu.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.OnDeleteListener;
import com.example.foodplanner.View.Menu.Interfaces.OnFabClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.MealViewHolder> {

    private final Context context;
    private final List<MealEntity> meals;
    private OnDeleteListener onDeleteListener;
    private int dayIndex;
    MealPresenter presenter;
    private OnFabClickListener onFabClickListener;

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
        holder.fabSwap.setBackgroundTintList(ColorStateList.valueOf(defaultColor));

        // Check if meal exists in the database
        presenter.isMealExists(meal.getIdMeal(), exists -> {
            int color = exists ? ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor) : defaultColor;
            holder.fabSwap.setBackgroundTintList(ColorStateList.valueOf(color));
        });

        holder.fabSwap.setOnClickListener(v -> {
            if (onFabClickListener != null) {
                onFabClickListener.onFabClick(meal);
                presenter.isMealExists(meal.getIdMeal(), exists -> {
                    int color = exists ? defaultColor : ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor);
                    holder.fabSwap.setBackgroundTintList(ColorStateList.valueOf(color));
                });
            }
        });


        holder.fabExit.setOnClickListener(v -> {
            if (onDeleteListener != null) {
                onDeleteListener.onMealDelete(dayIndex, position);  // Pass dayIndex and position
            }
        });

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
        FloatingActionButton fabSwap;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMealImage = itemView.findViewById(R.id.mealImage);
            tvMealName = itemView.findViewById(R.id.mealName);
            fabSwap = itemView.findViewById(R.id.fab);
            fabExit = itemView.findViewById(R.id.fabexit);
        }
    }
}
