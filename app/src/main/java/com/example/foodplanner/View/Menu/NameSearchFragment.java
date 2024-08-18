package com.example.foodplanner.View.Menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;

import java.util.List;

public class NameSearchFragment extends Fragment implements MealView {

    private RecyclerView recyclerView;
    private MealPresenter presenter;
    private EditText editTextMealName;
    private TextView textViewError;
    private MealAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with the action to navigate to another fragment
                Navigation.findNavController(requireView()).navigate(R.id.action_nameSearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchViewMeal = view.findViewById(R.id.searchViewMeal);
        searchViewMeal.setIconifiedByDefault(false); // Ensure SearchView is always expanded
        //textViewError = view.findViewById(R.id.textViewError);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        adapter = new MealAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        presenter = new MealPresenterImpl(this, mealApi);
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
    }

    @Override
    public void showMeal(MealEntity meal) {

    }

    @Override
    public void showMealDetails(MealEntity meal) {

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
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {

    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}