package com.example.foodplanner.View.Menu.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Model.Repository.DataBase.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.DataBase.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Adapters.DaysAdapter;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.example.foodplanner.View.Menu.Interfaces.OnAddClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealPlannerFragment extends Fragment implements MealView, OnAddClickListener {

    private RecyclerView rvDaysOfWeek;
    private DaysAdapter daysAdapter;
    private List<String> daysOfWeek;
    private List<List<MealEntity>> weeklyMeals;
    private MealPresenter presenter;
    private ProgressBar progressBar;
    private TextView tvStartPlanning;
    private Button btnStartPlan;
    private List<Integer> selectedDays;
    int pos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealPresenterImpl(this, new MealRepository(
                new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext()).favoriteMealDao()),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_planner, container, false);
        rvDaysOfWeek = view.findViewById(R.id.rv_days_of_week);
        progressBar = view.findViewById(R.id.progress_bar);
        tvStartPlanning = view.findViewById(R.id.tv_start_planning);
        btnStartPlan = view.findViewById(R.id.btn_start_plan);

        daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        weeklyMeals = new ArrayList<>();
        for (int i = 0; i < daysOfWeek.size(); i++) {
            weeklyMeals.add(new ArrayList<>());
        }

        daysAdapter = new DaysAdapter(getContext(), daysOfWeek, weeklyMeals);
        rvDaysOfWeek.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaysOfWeek.setAdapter(daysAdapter);

        daysAdapter.setOnAddClickListener(this); // Set the OnAddClickListener
        showInitialView();

        btnStartPlan.setOnClickListener(v -> {
            showDaySelectionDialog();
        });


        return view;
    }
    private void loadMealsForSelectedDays(List<Integer> selectedDays) {
        showPlanningView();
        for (Integer dayIndex : selectedDays) {
            for (int j = 0; j < 3; j++) {
                presenter.loadRandomMealForDay(dayIndex);
            }
        }
    }

    public void showDaySelectionDialog() {
        String[] daysArray = daysOfWeek.toArray(new String[0]);
        boolean[] checkedDays = new boolean[daysOfWeek.size()];

        // Populate checkedDays based on previously saved selections if needed

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Days")
                .setMultiChoiceItems(daysArray, checkedDays, (dialog, which, isChecked) -> {
                    // Handle selection changes if needed
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    // Save selected days and load meals
                    selectedDays = new ArrayList<>();
                    for (int i = 0; i < checkedDays.length; i++) {
                        if (checkedDays[i]) {
                            selectedDays.add(i);
                        }
                    }
                    loadMealsForSelectedDays(selectedDays);
                })
                .setNegativeButton("Cancel", null);
        builder.create().show();
    }


    public void onAddClick(int position) {
        // Add a random meal to the selected day
        pos = position;
        presenter.addRandomMealForDay(position); // Adjust this method if necessary
    }
    private void showInitialView() {
        tvStartPlanning.setVisibility(View.VISIBLE);
        btnStartPlan.setVisibility(View.VISIBLE);
        rvDaysOfWeek.setVisibility(View.GONE);
    }

    private void showPlanningView() {
        tvStartPlanning.setVisibility(View.GONE);
        btnStartPlan.setVisibility(View.GONE);
        rvDaysOfWeek.setVisibility(View.VISIBLE);
    }
    @Override
    public void showMeal(MealEntity meal) {
        if (selectedDays == null) {
            return; // No days selected
        }

        for (Integer dayIndex : selectedDays) {
            if (weeklyMeals.get(dayIndex).size() < 3) {
                weeklyMeals.get(dayIndex).add(meal);
                daysAdapter.notifyItemChanged(dayIndex);
                // Hide progress bar when all meals are loaded
                if (areAllSelectedMealsLoaded()) {
                    showLoading(false);
                }
                return;
            }
        }
    }


    private boolean areAllSelectedMealsLoaded() {
        // Check if selected days have at least 3 meals each
        if (selectedDays == null) {
            return false; // No days selected
        }
        for (Integer dayIndex : selectedDays) {
            if (weeklyMeals.get(dayIndex).size() < 3) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void showMealDetails(MealEntity meal) {
        // Implement if needed
    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        // Handle display of a list of meals if needed
    }

    @Override
    public void addMeal(MealEntity meal) {
        weeklyMeals.get(pos).add(meal);
        daysAdapter.notifyItemChanged(pos);
    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {
        // Implement if needed
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        // Implement if needed
    }

    @Override
    public void showError(String errorMessage) {
        // Handle errors
    }

    @Override
    public void showMessage(String message) {
        // Implement if needed
    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {
        // Implement if needed
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
