package com.example.foodplanner.View.Menu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealEntity> meals = new ArrayList<>();
    private OnMealClickListener onMealClickListener;
    private List<MealEntity> filteredMeals = new ArrayList<>(); // Initialize to an empty list

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealEntity meal = filteredMeals.get(position); // Use filteredMeals instead of meals
        holder.mealName.setText(meal.getStrMeal());

        // Load image using Glide or another image loading library
        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.mealImage);
        holder.itemView.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
            }
        });
    }

    public void setOnMealClickListener(OnMealClickListener listener) {
        this.onMealClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return filteredMeals.size(); // Use filteredMeals instead of meals
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
        this.filteredMeals = new ArrayList<>(meals);
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

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<MealEntity> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(meals);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (MealEntity meal : meals) {
                        if (meal.getStrMeal().toLowerCase().contains(filterPattern)) {
                            filteredList.add(meal);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredMeals.clear();
                filteredMeals.addAll((List<MealEntity>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
