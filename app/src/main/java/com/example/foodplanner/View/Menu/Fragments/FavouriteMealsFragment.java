package com.example.foodplanner.View.Menu.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.Presenter.LoggedInPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.Presenter.UpdateMealsPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Adapters.MealAdapter;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FavouriteMealsFragment extends Fragment {

    private UpdateMealsPresenter updateMealsPresenter;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private SearchView searchViewMeal;
    private LoggedInPresenter loggedInPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        searchViewMeal = view.findViewById(R.id.searchViewMeal);
        searchViewMeal.setIconifiedByDefault(false);

        updateMealsPresenter = new UpdateMealsPresenter(new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));

        loggedInPresenter = new LoggedInPresenter(new AuthModel(getContext()));

        adapter = new MealAdapter(requireContext(),loggedInPresenter,updateMealsPresenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        LiveData<List<MealEntity>> mealList = updateMealsPresenter.getFavMeals();
        mealList.observe(getViewLifecycleOwner(), mealEntities -> {
            adapter.setMeals(mealEntities);
        });
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
                Navigation.findNavController(view).navigate(R.id.action_favouritesFragment_to_mealDetailsFragment,bundle);
            }
        });
        adapter.setOnFabClickListener(meal -> {
            updateMealsPresenter.deleteMeal(meal);
            Snackbar.make(view, meal.getStrMeal() + " deleted from favorites", Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LiveData<List<MealEntity>> mealList = updateMealsPresenter.getFavMeals();
        mealList.observe(getViewLifecycleOwner(), mealEntities -> {
            adapter.setMeals(mealEntities);
        });
    }
}