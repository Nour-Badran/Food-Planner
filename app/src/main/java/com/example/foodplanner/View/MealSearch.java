package com.example.foodplanner.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodplanner.Model.FavoriteMealDatabase;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;

import java.util.List;

public class MealSearch extends AppCompatActivity implements MealView {
    private RecyclerView recyclerView;
    private MealPresenter presenter;
    private EditText editTextMealName;
    private TextView textViewError;
    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_search);

        editTextMealName = findViewById(R.id.editTextMealName);
        textViewError = findViewById(R.id.textViewError);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        recyclerView = findViewById(R.id.recyclerViewMeals);
        adapter = new MealAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        FavoriteMealDatabase appDatabase = Room.databaseBuilder(getApplicationContext(),
                FavoriteMealDatabase.class, "mealsdb").build();
        presenter = new MealPresenterImpl(this, mealApi, appDatabase);

        buttonSearch.setOnClickListener(v -> {
            String mealName = editTextMealName.getText().toString().trim();
            if (!mealName.isEmpty()) {
                presenter.searchMeals(mealName);
            } else {
                showError("Please enter a meal name");
            }
        });
    }

    @Override
    public void showMeal(MealEntity meal) {
        // Optionally handle displaying a single meal
    }

    @Override
    public void showMealDetails(MealEntity meal) {
        // Optionally handle displaying meal details
    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        adapter.setMeals(meals);
        if (meals.isEmpty()) {
            showError("No meals found 2");
        } else {
            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
            textViewError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        textViewError.setText(message);
        textViewError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE); // Hide RecyclerView if there's an error

    }
}
