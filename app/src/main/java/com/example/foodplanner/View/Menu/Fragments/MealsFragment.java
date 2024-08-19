package com.example.foodplanner.View.Menu.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.DataBase.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.DataBase.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.POJO.MealEntity;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Adapters.MealAdapter;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;
import androidx.appcompat.widget.SearchView;


public class MealsFragment extends Fragment implements MealView {

    String categoryName;
    String countryName;
    String ingredientName;
    MealPresenterImpl presenter;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    TextView txtCategoryName;
    private SearchView searchViewMeal;
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name");
            countryName = getArguments().getString("meal_name");
            ingredientName = getArguments().getString("ingredient_name");
        }
        if(categoryName!=null)
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Replace with the action to navigate to another fragment
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_categorySearchFragment);
                }
            });
        }
        else if(countryName!=null)
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Replace with the action to navigate to another fragment
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_countrySearchFragment);
                }
            });
        }
        else if(ingredientName!=null)
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Replace with the action to navigate to another fragment
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_ingredientSearchFragment);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals_by_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        txtCategoryName = view.findViewById(R.id.categoryNameId);
        searchViewMeal = view.findViewById(R.id.searchViewMeal);
        searchViewMeal.setIconifiedByDefault(false); // Ensure SearchView is always expanded
        if(categoryName!=null)
        {
            txtCategoryName.setText(categoryName + " Meals");
        }
        else if(countryName!=null)
        {
            txtCategoryName.setText(countryName + " Meals");
        }
        else if(ingredientName!=null)
        {
            txtCategoryName.setText(ingredientName + " Meals");
        }


        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext()).favoriteMealDao()),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        adapter = new MealAdapter(presenter,requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        if(categoryName!=null)
        {
            presenter.getMealsByCategory(categoryName);
        }
        else if(countryName!=null)
        {
            presenter.getMealByArea(countryName);
        }
        else if(ingredientName!=null)
        {
            presenter.getMealsByIngredient(ingredientName);
        }

        searchViewMeal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        adapter.setOnMealClickListener(meal -> {
            if (getActivity() != null) {
                //Toast.makeText(getActivity(), meal.getStrMeal(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("meal_name", meal.getStrMeal());
                Navigation.findNavController(view).navigate(R.id.action_mealsFragment_to_mealDetailsFragment,bundle);
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

        adapter.setOnFabClickListener(meal -> {
            if(loggedIn)
            {
                presenter.isMealExists(meal.getIdMeal(), exists -> {
                    if (exists) {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), meal.getStrMeal() + " deleted from favorites", Toast.LENGTH_SHORT).show()
                        );
                        presenter.deleteMeal(meal);
                    } else {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), meal.getStrMeal() + " added to favorites", Toast.LENGTH_SHORT).show()
                        );
                        presenter.insertMeal(meal);
                    }
                });
            }
            else
            {
                LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "LoginBottomSheetFragment");
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
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {

    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}