package com.example.foodplanner.View.Menu.Fragments;

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

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.Presenter.LoggedInPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.Presenter.UpdateMealsPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Adapters.MealAdapter;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import androidx.appcompat.widget.SearchView;


public class MealsFragment extends Fragment implements MealView {

    String categoryName;
    String countryName;
    String ingredientName;
    MealPresenterImpl presenter;
    UpdateMealsPresenter updateMealsPresenter;
    LoggedInPresenter loggedInPresenter;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    TextView txtCategoryName;
    private SearchView searchViewMeal;
    boolean loggedIn;
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
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_categorySearchFragment);
                }
            });
        }
        else if(countryName!=null)
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_countrySearchFragment);
                }
            });
        }
        else if(ingredientName!=null)
        {
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealsFragment_to_ingredientSearchFragment);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        loggedInPresenter = new LoggedInPresenter(new AuthModel(getContext()));
        loggedIn = loggedInPresenter.isLoggedIn();
        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));

        updateMealsPresenter = new UpdateMealsPresenter(new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));

        adapter = new MealAdapter(requireContext(),loggedInPresenter,updateMealsPresenter);
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
                Bundle bundle = new Bundle();
                bundle.putString("meal_name", meal.getStrMeal());
                Navigation.findNavController(view).navigate(R.id.action_mealsFragment_to_mealDetailsFragment,bundle);
            }
        });


        adapter.setOnFabClickListener(meal -> {
            if(loggedIn)
            {
                updateMealsPresenter.isMealExists(meal.getIdMeal(), exists -> {
                    if (exists) {
                        getActivity().runOnUiThread(() ->
                                Snackbar.make(view, meal.getStrMeal() + " deleted from favorites", Snackbar.LENGTH_SHORT).show()
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
            else
            {
                LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "LoginBottomSheetFragment");
            }
        });
    }

    @Override
    public void showMeal(MealEntity meal) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void addMeal(MealEntity meal) {

    }
}