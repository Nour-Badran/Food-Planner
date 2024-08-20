package com.example.foodplanner.View.Menu.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealPlannerFragment extends Fragment implements MealView {

    private RecyclerView rvDaysOfWeek;
    private DaysAdapter daysAdapter;
    private List<String> daysOfWeek;
    private List<List<MealEntity>> weeklyMeals;
    private MealPresenter presenter;
    private ProgressBar progressBar;

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

        daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        weeklyMeals = new ArrayList<>();
        for (int i = 0; i < daysOfWeek.size(); i++) {
            weeklyMeals.add(new ArrayList<>());
        }

        daysAdapter = new DaysAdapter(getContext(), daysOfWeek, weeklyMeals);
        rvDaysOfWeek.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaysOfWeek.setAdapter(daysAdapter);

        showLoading(true);
        loadRandomMealsForWeek();

        return view;
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void loadRandomMealsForWeek() {
        for (int i = 0; i < daysOfWeek.size(); i++) {
            int finalI = i;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                for (int j = 0; j < 3; j++) {
                    presenter.loadRandomMealForDay(finalI);
                }
            }, i * 500);
        }
    }


    @Override
    public void showMeal(MealEntity meal) {
        for (int i = 0; i < weeklyMeals.size() - 2; i++) {
            if (weeklyMeals.get(i).size() < 3) { // Assuming 3 meals per day
                weeklyMeals.get(i).add(meal);
                daysAdapter.notifyItemChanged(i);
                // Hide progress bar when all meals are loaded
                if (isAllMealsLoaded()) {
                    showLoading(false);
                }
                return;
            }
        }
    }

    private boolean isAllMealsLoaded() {
        // Ensure the list has at least 5 days
        if (weeklyMeals.size() < 5) {
            return false;
        }

        // Check if the first 5 days have at least 3 meals each
        for (int i = 0; i < 5; i++) {
            if (weeklyMeals.get(i).size() < 3) {
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
}

