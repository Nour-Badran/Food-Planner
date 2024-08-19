package com.example.foodplanner.View.Menu.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.OnFabClickListener;
import com.example.foodplanner.View.Menu.Interfaces.OnMealClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealEntity> meals = new ArrayList<>();
    private OnMealClickListener onMealClickListener;
    private OnFabClickListener onFabClickListener;
    private List<MealEntity> filteredMeals = new ArrayList<>(); // Initialize to an empty list
    private MealPresenterImpl presenter; // Add this line
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";

    private Context context;
    // Add a constructor or setter for the presenter
    public MealAdapter(MealPresenterImpl presenter,Context context) {
        this.presenter = presenter;
        this.context = context;
    }
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

        int defaultColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
        holder.fab.setBackgroundTintList(ColorStateList.valueOf(defaultColor));

        // Check if meal exists in the database
        presenter.isMealExists(meal.getIdMeal(), exists -> {
            int color = exists ? ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor) : defaultColor;
            holder.fab.setBackgroundTintList(ColorStateList.valueOf(color));
        });

//        if (meal.isInDatabase()) {
//            holder.fab.setBackgroundTintList(ColorStateList.valueOf(
//                    holder.itemView.getContext().getResources().getColor(R.color.colorAccent) // Color when meal is in database
//            ));
//        } else {
//            holder.fab.setBackgroundTintList(ColorStateList.valueOf(
//                    holder.itemView.getContext().getResources().getColor(R.color.colorPrimary) // Default color
//            ));
//        }

        holder.itemView.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
            }
        });

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

        holder.fab.setOnClickListener(v -> {
            if (onFabClickListener != null) {
                onFabClickListener.onFabClick(meal);
                if(loggedIn)
                {
                    presenter.isMealExists(meal.getIdMeal(), exists -> {
                        int color = exists ? defaultColor : ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor);
                        holder.fab.setBackgroundTintList(ColorStateList.valueOf(color));
                    });
                }
            }
        });

    }

    public void setOnFabClickListener(OnFabClickListener listener) {
        this.onFabClickListener = listener;
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
        FloatingActionButton fab;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            fab = itemView.findViewById(R.id.fab);
        }
    }
//    public void updateMealState(List<MealEntity> mealsInDb) {
//        for (MealEntity meal : filteredMeals) {
//            // Check if the meal is in the database and update its state
//            boolean exists = false;
//            for (MealEntity dbMeal : mealsInDb) {
//                if (meal.getIdMeal().equals(dbMeal.getIdMeal())) {
//                    exists = true;
//                    break;
//                }
//            }
//            meal.setInDatabase(exists); // Make sure your MealEntity has this field
//        }
//        notifyDataSetChanged();
//    }

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
