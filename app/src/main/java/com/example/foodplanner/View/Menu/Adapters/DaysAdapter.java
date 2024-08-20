package com.example.foodplanner.View.Menu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.R;

import java.util.List;
public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayViewHolder> {
    private final List<String> daysOfWeek;
    private final Context context;
    private final List<List<MealEntity>> weeklyMeals; // List of lists

    public DaysAdapter(Context context, List<String> daysOfWeek, List<List<MealEntity>> weeklyMeals) {
        this.context = context;
        this.daysOfWeek = daysOfWeek;
        this.weeklyMeals = weeklyMeals;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.tvDayName.setText(daysOfWeek.get(position));

        List<MealEntity> mealsForDay = weeklyMeals.get(position);
        PlannedMealsAdapter plannedMealsAdapter = new PlannedMealsAdapter(context, mealsForDay);
        holder.rvMealsForDay.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvMealsForDay.setAdapter(plannedMealsAdapter);
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName;
        RecyclerView rvMealsForDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tv_day_name);
            rvMealsForDay = itemView.findViewById(R.id.rv_meals_for_day);
        }
    }
}
