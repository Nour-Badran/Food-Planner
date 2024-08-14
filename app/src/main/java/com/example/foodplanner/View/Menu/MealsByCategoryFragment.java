package com.example.foodplanner.View.Menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;

import java.util.List;

public class MealsByCategoryFragment extends Fragment implements MealView{

    String categoryName;
    String countryName;
    MealPresenterImpl presenter;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    TextView txtCategoryName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name");
            countryName = getArguments().getString("meal_name");
        }
        if(countryName==null)
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Replace with the action to navigate to another fragment
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_categorySearchFragment);
                }
            });
        }
        else
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Replace with the action to navigate to another fragment
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_countrySearchFragment);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        txtCategoryName = view.findViewById(R.id.categoryNameId);
        if(categoryName!=null)
        {
            txtCategoryName.setText(categoryName + " Meals");
        }
        else
            txtCategoryName.setText(countryName + " Meals");

        adapter = new MealAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        presenter = new MealPresenterImpl(this, mealApi);
        if(categoryName!=null)
        {
            presenter.getMealsByCategory(categoryName);
        }
        else
        {
            presenter.getMealByArea(countryName);
        }

        adapter.setOnMealClickListener(meal -> {
            if (getActivity() != null) {
                //Toast.makeText(getActivity(), meal.getStrMeal(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("meal_name", meal.getStrMeal());
                Navigation.findNavController(view).navigate(R.id.action_mealsFragment_to_mealDetailsFragment,bundle);
            }
        });
        //presenter.getCategories();
    }

    @Override
    public void showMeal(MealEntity meal) {

    }

    @Override
    public void showMealDetails(MealEntity meal) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}