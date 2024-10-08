package com.example.foodplanner.View.Menu.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.Presenter.LoggedInPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.Presenter.UpdateMealsPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Interfaces.OnFabClickListener;
import com.example.foodplanner.View.Menu.Interfaces.OnMealClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealEntity> meals = new ArrayList<>();
    private OnMealClickListener onMealClickListener;
    private OnFabClickListener onFabClickListener;
    private List<MealEntity> filteredMeals = new ArrayList<>();
    boolean loggedIn;
    LoggedInPresenter loggedInPresenter;
    private Context context;
    private UpdateMealsPresenter updateMealsPresenter;
    public MealAdapter(Context context, LoggedInPresenter loggedInPresenter, UpdateMealsPresenter updateMealsPresenter) {
        this.context = context;
        this.loggedInPresenter = loggedInPresenter;
        this.updateMealsPresenter = updateMealsPresenter;
    }
    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealEntity meal = filteredMeals.get(position);
        holder.mealName.setText(meal.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11)
                .into(holder.mealImage);

        int defaultColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
        holder.fab.setBackgroundTintList(ColorStateList.valueOf(defaultColor));

        // Check if meal exists in the database
        updateMealsPresenter.isMealExists(meal.getIdMeal(), exists -> {
            int color = exists ? ContextCompat.getColor(holder.itemView.getContext(), R.color.areaBackgroundColor) : defaultColor;
            holder.fab.setBackgroundTintList(ColorStateList.valueOf(color));
        });

        loggedIn = loggedInPresenter.isLoggedIn();

        holder.fabAdd.setOnClickListener(v -> {
            if(loggedIn)
            {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());
                View bottomSheetView = LayoutInflater.from(v.getContext()).inflate(
                        R.layout.bottom_sheet_select_day,
                        (ViewGroup) v.getParent(), false);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                bottomSheetView.findViewById(R.id.btnMonday).setOnClickListener(view -> {
                    updateMealsPresenter.insertMondayMeal(new Monday(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Monday!", Snackbar.LENGTH_SHORT).show();
                });

                bottomSheetView.findViewById(R.id.btnTuesday).setOnClickListener(view -> {
                    updateMealsPresenter.insertTuesdayMeal(new Tuesday(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Tuesday!", Snackbar.LENGTH_SHORT).show();
                });

                bottomSheetView.findViewById(R.id.btnWednesday).setOnClickListener(view -> {
                    updateMealsPresenter.insertWednesdayMeal(new Wednesday(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Wednesday!", Snackbar.LENGTH_SHORT).show();
                });

                bottomSheetView.findViewById(R.id.btnThursday).setOnClickListener(view -> {
                    updateMealsPresenter.insertThursdayMeal(new Thursday(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Thursday!", Snackbar.LENGTH_SHORT).show();
                });

                bottomSheetView.findViewById(R.id.btnFriday).setOnClickListener(view -> {
                    updateMealsPresenter.insertFridayMeal(new Friday(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Friday!", Snackbar.LENGTH_SHORT).show();
                });

                bottomSheetView.findViewById(R.id.btnSaturday).setOnClickListener(view -> {
                    updateMealsPresenter.insertSaturdayMeal(new Saturday(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Saturday!", Snackbar.LENGTH_SHORT).show();
                });

                bottomSheetView.findViewById(R.id.btnSunday).setOnClickListener(view -> {
                    updateMealsPresenter.insertSundayMeal(new Sunday(
                            meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb()));
                    bottomSheetDialog.dismiss();
                    Snackbar.make(v, "Meal added to Sunday!", Snackbar.LENGTH_SHORT).show();
                });
            }
            else {
                if (context instanceof FragmentActivity) {
                    LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                    bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "LoginBottomSheetFragment");
                }
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
            }
        });

        holder.fab.setOnClickListener(v -> {
            if (onFabClickListener != null) {
                onFabClickListener.onFabClick(meal);
                if(loggedIn)
                {
                    updateMealsPresenter.isMealExists(meal.getIdMeal(), exists -> {
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
        return filteredMeals.size(); // filteredMeals instead of meals
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
        FloatingActionButton fabAdd;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            fab = itemView.findViewById(R.id.fab);
            fabAdd = itemView.findViewById(R.id.fabPlan);
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
