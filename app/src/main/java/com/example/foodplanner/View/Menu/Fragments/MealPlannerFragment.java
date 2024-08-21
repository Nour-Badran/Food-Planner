package com.example.foodplanner.View.Menu.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.R;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Adapters.DaysAdapter;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.example.foodplanner.View.Menu.Interfaces.OnAddClickListener;
import com.example.foodplanner.View.Menu.Interfaces.OnFabClickListener;
import com.google.android.material.snackbar.Snackbar;

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
    Boolean flag = true;

    MealRepository repo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class)));
        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
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

        daysAdapter = new DaysAdapter(getContext(), daysOfWeek, weeklyMeals,presenter,repo);
        rvDaysOfWeek.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaysOfWeek.setAdapter(daysAdapter);

        daysAdapter.setOnAddClickListener(this); // Set the OnAddClickListener
        showInitialView();

        btnStartPlan.setOnClickListener(v -> {
            showDaySelectionDialog();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int[] daysChecked = {0}; // Counter for days checked
        final int totalDays = 7; // Total number of days to check
        final boolean[] hasMeals = {false}; // Flag to track if any day has meals

        daysAdapter.setOnFabClickListener(meal -> {
                presenter.isMealExists(meal.getIdMeal(), exists -> {
                    if (exists) {
                        getActivity().runOnUiThread(() ->
                                Snackbar.make(view, meal.getStrMeal() + " removed from favorites", Snackbar.LENGTH_SHORT).show()
                        );
                        presenter.deleteMeal(meal);
                    } else {
                        getActivity().runOnUiThread(() ->
                                Snackbar.make(view, meal.getStrMeal() + " added to favorites", Snackbar.LENGTH_SHORT).show()
                        );
                        presenter.insertMeal(meal);
                    }
                });
            }
        );

        daysAdapter.setOnMealClickListener(meal -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("meal_name", meal.getStrMeal());
                    Navigation.findNavController(view).navigate(R.id.action_mealPlannerFragment_to_mealDetailsFragment,bundle);
                    //Toast.makeText(getContext(), meal.getStrMeal() + " was clicked", Toast.LENGTH_SHORT).show();
                }
        );

        Observer<List<?>> observer = new Observer<List<?>>() {
            @Override
            public void onChanged(List<?> meals) {
                daysChecked[0]++;
                if (!meals.isEmpty()) {
                    hasMeals[0] = true;
                }

                if (daysChecked[0] == totalDays) {
                    // All days have been checked
                    if (hasMeals[0]) {
                        showPlanningView();
                        loadMealsFromDatabase();
                    } else {
                        showInitialView();
                    }
                }
            }
        };


        repo.getMondayMeals().observe(getViewLifecycleOwner(), observer);
        repo.getTuesdayMeals().observe(getViewLifecycleOwner(), observer);
        repo.getWednesdayMeals().observe(getViewLifecycleOwner(), observer);
        repo.getThursdayMeals().observe(getViewLifecycleOwner(), observer);
        repo.getFridayMeals().observe(getViewLifecycleOwner(), observer);
        repo.getSaturdayMeals().observe(getViewLifecycleOwner(), observer);
        repo.getSundayMeals().observe(getViewLifecycleOwner(), observer);
    }
    private void loadMealsFromDatabase() {
        showLoading(true); // Show progress bar while loading

        repo.getMondayMeals().observe(getViewLifecycleOwner(), mondays -> {
            if (mondays != null) {
                List<MealEntity> mondayMeals = new ArrayList<>();
                for (Monday monday : mondays) {
                    mondayMeals.add(new MealEntity(monday.getMealId(), monday.getMealName(), monday.getStrMealThumb()));
                }
                weeklyMeals.set(0, mondayMeals);
                daysAdapter.notifyItemChanged(0);
            }
        });

        repo.getTuesdayMeals().observe(getViewLifecycleOwner(), tuesdays -> {
            if (tuesdays != null) {
                List<MealEntity> tuesdayMeals = new ArrayList<>();
                for (Tuesday tuesday : tuesdays) {
                    tuesdayMeals.add(new MealEntity(tuesday.getMealId(), tuesday.getMealName(), tuesday.getStrMealThumb()));
                }
                weeklyMeals.set(1, tuesdayMeals);
                daysAdapter.notifyItemChanged(1);
            }
        });

        repo.getWednesdayMeals().observe(getViewLifecycleOwner(), wednesdays -> {
            if (wednesdays != null) {
                List<MealEntity> wednesdayMeals = new ArrayList<>();
                for (Wednesday wednesday : wednesdays) {
                    wednesdayMeals.add(new MealEntity(wednesday.getMealId(), wednesday.getMealName(), wednesday.getStrMealThumb()));
                }
                weeklyMeals.set(2, wednesdayMeals);
                daysAdapter.notifyItemChanged(2);
            }
        });

        repo.getThursdayMeals().observe(getViewLifecycleOwner(), thursdays -> {
            if (thursdays != null) {
                List<MealEntity> thursdayMeals = new ArrayList<>();
                for (Thursday thursday : thursdays) {
                    thursdayMeals.add(new MealEntity(thursday.getMealId(), thursday.getMealName(), thursday.getStrMealThumb()));
                }
                weeklyMeals.set(3, thursdayMeals);
                daysAdapter.notifyItemChanged(3);
            }
        });

        repo.getFridayMeals().observe(getViewLifecycleOwner(), fridays -> {
            if (fridays != null) {
                List<MealEntity> fridayMeals = new ArrayList<>();
                for (Friday friday : fridays) {
                    fridayMeals.add(new MealEntity(friday.getMealId(), friday.getMealName(), friday.getStrMealThumb()));
                }
                weeklyMeals.set(4, fridayMeals);
                daysAdapter.notifyItemChanged(4);
            }
        });

        repo.getSaturdayMeals().observe(getViewLifecycleOwner(), saturdays -> {
            if (saturdays != null) {
                List<MealEntity> saturdayMeals = new ArrayList<>();
                for (Saturday saturday : saturdays) {
                    saturdayMeals.add(new MealEntity(saturday.getMealId(), saturday.getMealName(), saturday.getStrMealThumb()));
                }
                weeklyMeals.set(5, saturdayMeals);
                daysAdapter.notifyItemChanged(5);
            }
        });

        repo.getSundayMeals().observe(getViewLifecycleOwner(), sundays -> {
            if (sundays != null) {
                List<MealEntity> sundayMeals = new ArrayList<>();
                for (Sunday sunday : sundays) {
                    sundayMeals.add(new MealEntity(sunday.getMealId(), sunday.getMealName(), sunday.getStrMealThumb()));
                }
                weeklyMeals.set(6, sundayMeals);
                daysAdapter.notifyItemChanged(6);
            }

            // Hide progress bar when all meals are loaded
            showLoading(false);
        });
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
                switch (daysOfWeek.get(dayIndex)) {
                    case "Monday":
                        Monday monday = new Monday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertMondayMeal(monday);
                        break;
                    case "Tuesday":
                        Tuesday tuesday = new Tuesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertTuesdayMeal(tuesday);
                        break;
                    case "Wednesday":
                        Wednesday wednesday = new Wednesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertWednesdayMeal(wednesday);
                        break;
                    case "Thursday":
                        Thursday thursday = new Thursday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertThursdayMeal(thursday);
                        break;
                    case "Friday":
                        Friday friday = new Friday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertFridayMeal(friday);
                        break;
                    case "Saturday":
                        Saturday saturday= new Saturday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertSaturdayMeal(saturday);
                        break;
                    case "Sunday":
                        Sunday sunday = new Sunday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        repo.insertSundayMeal(sunday);
                        break;
                }
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
        switch (daysOfWeek.get(pos)) {
            case "Monday":
                Monday monday = new Monday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertMondayMeal(monday);
                break;
            case "Tuesday":
                Tuesday tuesday = new Tuesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertTuesdayMeal(tuesday);
                break;
            case "Wednesday":
                Wednesday wednesday = new Wednesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertWednesdayMeal(wednesday);
                break;
            case "Thursday":
                Thursday thursday = new Thursday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertThursdayMeal(thursday);
                break;
            case "Friday":
                Friday friday = new Friday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertFridayMeal(friday);
                break;
            case "Saturday":
                Saturday saturday = new Saturday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertSaturdayMeal(saturday);
                break;
            case "Sunday":
                Sunday sunday = new Sunday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                repo.insertSundayMeal(sunday);
                break;
        }
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
