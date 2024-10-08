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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplanner.Model.Network.NetworkUtil;
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
import com.example.foodplanner.Presenter.UpdateMealsPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.MealSelectionDialogFragment;
import com.example.foodplanner.View.Menu.Adapters.DaysAdapter;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.example.foodplanner.View.Menu.Interfaces.OnAddClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MealPlannerFragment extends Fragment implements MealView, OnAddClickListener {

    private RecyclerView rvDaysOfWeek;
    private DaysAdapter daysAdapter;
    private List<String> daysOfWeek;
    private List<List<MealEntity>> weeklyMeals;
    private MealPresenter presenter;
    private UpdateMealsPresenter updateMealsPresenter;
    private ProgressBar progressBar;
    private TextView tvStartPlanning;
    private Button btnStartPlan;
    private List<Integer> selectedDays;
    int pos;
    Button createPlan;
    MealRepository repo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class)));
        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        updateMealsPresenter = new UpdateMealsPresenter( new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(getContext())),
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
        createPlan = view.findViewById(R.id.btn_create_plan);

        daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        weeklyMeals = new ArrayList<>();
        for (int i = 0; i < daysOfWeek.size(); i++) {
            weeklyMeals.add(new ArrayList<>());
        }

        daysAdapter = new DaysAdapter(getContext(), daysOfWeek, weeklyMeals,updateMealsPresenter);
        rvDaysOfWeek.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaysOfWeek.setAdapter(daysAdapter);

        daysAdapter.setOnAddClickListener(this); // Set the OnAddClickListener
        showInitialView();

        btnStartPlan.setOnClickListener(v -> {
            showDaySelectionDialog();
        });

        createPlan.setOnClickListener(v -> {
            showPlanningView();
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
            updateMealsPresenter.isMealExists(meal.getIdMeal(), exists -> {
                        if (exists) {
                            getActivity().runOnUiThread(() ->
                                    Snackbar.make(view, meal.getStrMeal() + " removed from favorites", Snackbar.LENGTH_SHORT).show()
                            );
                            updateMealsPresenter.deleteMeal(meal);
                        } else {
                            getActivity().runOnUiThread(() ->
                                    Snackbar.make(view, meal.getStrMeal() + " added to favorites", Snackbar.LENGTH_SHORT).show()
                            );
                            updateMealsPresenter.insertMeal(meal);
                        }
                    });
                }
        );

        daysAdapter.setOnMealClickListener(meal -> {

                    Bundle bundle = new Bundle();
                    bundle.putString("meal_name", meal.getStrMeal());
                    Navigation.findNavController(view).navigate(R.id.action_mealPlannerFragment_to_mealDetailsFragment, bundle);
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


        updateMealsPresenter.getMondayMeals().observe(getViewLifecycleOwner(), observer);
        updateMealsPresenter.getTuesdayMeals().observe(getViewLifecycleOwner(), observer);
        updateMealsPresenter.getWednesdayMeals().observe(getViewLifecycleOwner(), observer);
        updateMealsPresenter.getThursdayMeals().observe(getViewLifecycleOwner(), observer);
        updateMealsPresenter.getFridayMeals().observe(getViewLifecycleOwner(), observer);
        updateMealsPresenter.getSaturdayMeals().observe(getViewLifecycleOwner(), observer);
        updateMealsPresenter.getSundayMeals().observe(getViewLifecycleOwner(), observer);
    }

    private void loadMealsFromDatabase() {
        showLoading(true); // Show progress bar while loading

        updateMealsPresenter.getMondayMeals().observe(getViewLifecycleOwner(), mondays -> {
            if (mondays != null) {
                List<MealEntity> mondayMeals = new ArrayList<>();
                for (Monday monday : mondays) {
                    mondayMeals.add(new MealEntity(monday.getMealId(), monday.getMealName(), monday.getStrMealThumb()));
                }
                weeklyMeals.set(0, mondayMeals);
                daysAdapter.notifyItemChanged(0);
            }
        });

        updateMealsPresenter.getTuesdayMeals().observe(getViewLifecycleOwner(), tuesdays -> {
            if (tuesdays != null) {
                List<MealEntity> tuesdayMeals = new ArrayList<>();
                for (Tuesday tuesday : tuesdays) {
                    tuesdayMeals.add(new MealEntity(tuesday.getMealId(), tuesday.getMealName(), tuesday.getStrMealThumb()));
                }
                weeklyMeals.set(1, tuesdayMeals);
                daysAdapter.notifyItemChanged(1);
            }
        });

        updateMealsPresenter.getWednesdayMeals().observe(getViewLifecycleOwner(), wednesdays -> {
            if (wednesdays != null) {
                List<MealEntity> wednesdayMeals = new ArrayList<>();
                for (Wednesday wednesday : wednesdays) {
                    wednesdayMeals.add(new MealEntity(wednesday.getMealId(), wednesday.getMealName(), wednesday.getStrMealThumb()));
                }
                weeklyMeals.set(2, wednesdayMeals);
                daysAdapter.notifyItemChanged(2);
            }
        });

        updateMealsPresenter.getThursdayMeals().observe(getViewLifecycleOwner(), thursdays -> {
            if (thursdays != null) {
                List<MealEntity> thursdayMeals = new ArrayList<>();
                for (Thursday thursday : thursdays) {
                    thursdayMeals.add(new MealEntity(thursday.getMealId(), thursday.getMealName(), thursday.getStrMealThumb()));
                }
                weeklyMeals.set(3, thursdayMeals);
                daysAdapter.notifyItemChanged(3);
            }
        });

        updateMealsPresenter.getFridayMeals().observe(getViewLifecycleOwner(), fridays -> {
            if (fridays != null) {
                List<MealEntity> fridayMeals = new ArrayList<>();
                for (Friday friday : fridays) {
                    fridayMeals.add(new MealEntity(friday.getMealId(), friday.getMealName(), friday.getStrMealThumb()));
                }
                weeklyMeals.set(4, fridayMeals);
                daysAdapter.notifyItemChanged(4);
            }
        });

        updateMealsPresenter.getSaturdayMeals().observe(getViewLifecycleOwner(), saturdays -> {
            if (saturdays != null) {
                List<MealEntity> saturdayMeals = new ArrayList<>();
                for (Saturday saturday : saturdays) {
                    saturdayMeals.add(new MealEntity(saturday.getMealId(), saturday.getMealName(), saturday.getStrMealThumb()));
                }
                weeklyMeals.set(5, saturdayMeals);
                daysAdapter.notifyItemChanged(5);
            }
        });

        updateMealsPresenter.getSundayMeals().observe(getViewLifecycleOwner(), sundays -> {
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

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
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
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public void onAddClick(int position) {
        pos = position;
        // Show a dialog to choose between random or specific meal
        showMealChoiceDialog();
    }

    private void showMealChoiceDialog() {
        AtomicReference<List<MealEntity>> mealList = new AtomicReference<>(new ArrayList<>());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialogTheme);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_choose_meal, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnRandomMeal = dialogView.findViewById(R.id.btn_random_meal);
        Button btnChooseMeal = dialogView.findViewById(R.id.btn_choose_meal);
        Button btnChooseFromFavs = dialogView.findViewById(R.id.btn_favourite_meal);

        if(!NetworkUtil.isNetworkConnected(getContext()))
        {
            btnChooseMeal.setVisibility(View.GONE);
            btnRandomMeal.setVisibility(View.GONE);
        }
        updateMealsPresenter.getFavMeals().observe(getActivity(), meals -> {
            if (meals != null) {
                // Convert LiveData<List<MealEntity>> to List<MealEntity>
                mealList.set(new ArrayList<>(meals));
            }
        });

        btnRandomMeal.setOnClickListener(v -> {
            presenter.addRandomMealForDay(pos);
            dialog.dismiss();
        });

        btnChooseMeal.setOnClickListener(v -> {
            presenter.getAllMeals();
            dialog.dismiss();
        });
        btnChooseFromFavs.setOnClickListener(v -> {
            mealSelectionDialog(mealList.get());
            dialog.dismiss();
        });
        dialog.show();
    }


    private void showInitialView() {
        tvStartPlanning.setVisibility(View.VISIBLE);
        btnStartPlan.setVisibility(View.VISIBLE);
        createPlan.setVisibility(View.VISIBLE);
        rvDaysOfWeek.setVisibility(View.GONE);
    }

    private void showPlanningView() {
        tvStartPlanning.setVisibility(View.GONE);
        btnStartPlan.setVisibility(View.GONE);
        createPlan.setVisibility(View.GONE);
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
                        updateMealsPresenter.insertMondayMeal(monday);
                        break;
                    case "Tuesday":
                        Tuesday tuesday = new Tuesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        updateMealsPresenter.insertTuesdayMeal(tuesday);
                        break;
                    case "Wednesday":
                        Wednesday wednesday = new Wednesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        updateMealsPresenter.insertWednesdayMeal(wednesday);
                        break;
                    case "Thursday":
                        Thursday thursday = new Thursday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        updateMealsPresenter.insertThursdayMeal(thursday);
                        break;
                    case "Friday":
                        Friday friday = new Friday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        updateMealsPresenter.insertFridayMeal(friday);
                        break;
                    case "Saturday":
                        Saturday saturday = new Saturday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        updateMealsPresenter.insertSaturdayMeal(saturday);
                        break;
                    case "Sunday":
                        Sunday sunday = new Sunday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        updateMealsPresenter.insertSundayMeal(sunday);
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
    public void showMeals(List<MealEntity> meals) {
        mealSelectionDialog(meals);
    }

    public void mealSelectionDialog(List<MealEntity> meals)
    {
        if (meals != null) {
            MealSelectionDialogFragment dialog = new MealSelectionDialogFragment(meals, selectedMeal -> {
                addMealToPlanAndRoom(selectedMeal);

            });
            dialog.show(getParentFragmentManager(), "MealSelectionDialogFragment");
        }
    }
    @Override
    public void addMeal(MealEntity meal) {
        addMealToPlanAndRoom(meal);
    }

    public void addMealToPlanAndRoom(MealEntity meal)
    {
        switch (daysOfWeek.get(pos)) {
            case "Monday":
                Monday monday = new Monday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertMondayMeal(monday);
                break;
            case "Tuesday":
                Tuesday tuesday = new Tuesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertTuesdayMeal(tuesday);
                break;
            case "Wednesday":
                Wednesday wednesday = new Wednesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertWednesdayMeal(wednesday);
                break;
            case "Thursday":
                Thursday thursday = new Thursday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertThursdayMeal(thursday);
                break;
            case "Friday":
                Friday friday = new Friday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertFridayMeal(friday);
                break;
            case "Saturday":
                Saturday saturday = new Saturday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertSaturdayMeal(saturday);
                break;
            case "Sunday":
                Sunday sunday = new Sunday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                updateMealsPresenter.insertSundayMeal(sunday);
                break;
        }
        weeklyMeals.get(pos).add(meal);
        daysAdapter.notifyItemChanged(pos);
    }

    @Override
    public void showError(String errorMessage) {}

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
