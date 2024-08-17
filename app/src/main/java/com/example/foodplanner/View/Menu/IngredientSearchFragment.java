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
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;

import java.util.List;

public class IngredientSearchFragment extends Fragment implements MealView{
    private MealPresenter presenter;
    private RecyclerView recyclerView;
    private IngredientAdapter adapter;
    private SearchView searchViewIngredient;
    //private TextView textViewError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with the action to navigate to another fragment
                Navigation.findNavController(requireView()).navigate(R.id.action_ingredientSearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchViewIngredient = view.findViewById(R.id.searchViewIngredient);
        searchViewIngredient.setIconifiedByDefault(false); // Ensure SearchView is always expanded
        //textViewError = view.findViewById(R.id.textViewError);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        adapter = new IngredientAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        presenter = new MealPresenterImpl(this, mealApi);
        presenter.getIngredients();

        searchViewIngredient.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getIngredientsBySubstring(query.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
//                    textViewError.setVisibility(View.VISIBLE);
//                    textViewError.setText("No ingredients found");
                } else {
//                    textViewError.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    presenter.getIngredientsBySubstring(newText.trim());
                }
                return false;
            }
        });

        adapter.setOnIngredientClickListener(ingredient -> {
            // Navigate to MealsFragment
            if (getActivity() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("ingredient_name", ingredient.getName());
                Navigation.findNavController(view).navigate(R.id.action_ingredientSearchFragment_to_mealsFragment, bundle);
            }
        });
    }

    @Override
    public void showMeal(MealEntity meal) {
        // Handle meal display
    }

    @Override
    public void showMealDetails(MealEntity meal) {
        // Handle meal details display
    }

    @Override
    public void showError(String message) {
//        textViewError.setText(message);
//        textViewError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {
        // Handle category display
    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        // Handle meals display
    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {
        adapter.setIngredients(ingredients);
        recyclerView.setVisibility(View.VISIBLE);
//        textViewError.setVisibility(View.GONE);
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        // Handle meals by category
    }
}
