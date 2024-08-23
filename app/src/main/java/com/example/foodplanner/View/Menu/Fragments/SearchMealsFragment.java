package com.example.foodplanner.View.Menu.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Adapters.MealAdapter;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SearchMealsFragment extends Fragment implements MealView, AuthView {

    private RecyclerView recyclerView;
    private MealPresenterImpl presenter;
    private AuthPresenter authPresenter;
    private MealAdapter adapter;
    private boolean loggedIn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.action_nameSearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchViewMeal = view.findViewById(R.id.searchViewMeal);
        searchViewMeal.setIconifiedByDefault(false); // Ensure SearchView is always expanded
        recyclerView = view.findViewById(R.id.recyclerViewMeals);

        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        authPresenter = new AuthPresenter(this, new AuthModel(getContext()));
        loggedIn = authPresenter.isLoggedIn();
        adapter = new MealAdapter(presenter,requireContext(),authPresenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        presenter.getAllMeals();

        searchViewMeal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchMeals(query.trim());
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
                Navigation.findNavController(view).navigate(R.id.action_nameSearchFragment_to_mealDetailsFragment, bundle);
            }
        });

        adapter.setOnFabClickListener(meal -> {
            if(loggedIn)
            {
                presenter.isMealExists(meal.getIdMeal(), exists -> {
                    if (exists) {
                        getActivity().runOnUiThread(() ->
                                Snackbar.make(view, meal.getStrMeal() + " deleted from favorites", Snackbar.LENGTH_SHORT).show()
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
    public void showMealDetails(MealEntity meal) {

    }
    @Override
    public void showMessage(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() ->
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
            );
        }
    }
    @Override
    public void showError(String message) {
//        textViewError.setText(message);
//        textViewError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        adapter.setMeals(meals);
        recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
//        textViewError.setVisibility(View.GONE);
    }

    @Override
    public void addMeal(MealEntity meal) {

    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {

    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void navigateToHome(String email) {

    }

    @Override
    public void navigateToSignUp() {

    }

    @Override
    public void setEmailError(String error) {

    }

    @Override
    public void setPasswordError(String error) {

    }
}