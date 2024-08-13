package com.example.foodplanner.View.Menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;

import java.util.List;


public class RandomMealFragment extends Fragment implements MealView {

    private MealPresenter presenter;
    ImageView mealImage;
    TextView mealName;
    TextView mealCategory;
    TextView mealArea;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        presenter = new MealPresenterImpl(this, mealApi);
        presenter.loadRandomMeal();
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        mealCategory = view.findViewById(R.id.mealCategory);
        mealArea = view.findViewById(R.id.mealArea);
    }

    @Override
    public void showMeal(MealEntity meal) {
        mealName.setText("Meal Name: " + meal.getStrMeal());
        mealCategory.setText("Category: " + meal.getStrCategory());
        mealArea.setText("Area: " + meal.getStrArea());
        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
    }

    @Override
    public void showMealDetails(MealEntity meal) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {

    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}