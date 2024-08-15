package com.example.foodplanner.View.Menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        editTextMealName = view.findViewById(R.id.editTextMealName);
        editTextMealName.setText("");
        textViewError = view.findViewById(R.id.textViewError);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        adapter = new MealAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        presenter = new MealPresenterImpl(this, mealApi);
        presenter.getAllMeals();

        editTextMealName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                presenter.searchMeals(editTextMealName.getText().toString().trim());
                return false;
            }
        });

        buttonSearch.setOnClickListener(v -> {
            String mealName = editTextMealName.getText().toString().trim();
            if (!mealName.isEmpty()) {
                presenter.searchMeals(mealName);
            } else {
                showError("No meals found");
            }
        });

        adapter.setOnMealClickListener(meal -> {
            // Navigate to MealsFragment
            if (getActivity() != null) {
                //Toast.makeText(getActivity(), category.getName(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("meal_name", meal.getStrMeal());
                Navigation.findNavController(view).navigate(R.id.action_nameSearchFragment_to_mealDetailsFragment,bundle);
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
        textViewError.setText(message);
        textViewError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        adapter.setMeals(meals);
        recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
        textViewError.setVisibility(View.GONE);
    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {

    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}