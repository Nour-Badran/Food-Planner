package com.example.foodplanner.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.foodplanner.Model.FavoriteMealDatabase;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MealView {
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private MealPresenter presenter;
    ImageView next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent(MainActivity.this, MealSearch.class);
                startActivity(outIntent);
            }
        });
//        recyclerView = findViewById(R.id.mealRecyclerView);
//        adapter = new MealAdapter();
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        FavoriteMealDatabase appDatabase = Room.databaseBuilder(getApplicationContext(),
                FavoriteMealDatabase.class, "mealsdb").build();
        presenter = new MealPresenterImpl(this, mealApi);
        presenter.loadRandomMeal();
    }

    @Override
    public void showMeal(MealEntity meal) {
        // Log the meal data
        Log.d("MainActivity", "Meal received: " + meal.getStrMeal());

        // Show meal of the day in a prominent way
//        MealFragment mealFragment = new MealFragment(meal);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, mealFragment)
//                .commit();
    }

    @Override
    public void showMealDetails(MealEntity meal) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMeals(List<MealEntity> meals) {

    }
}
