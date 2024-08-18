package com.example.foodplanner.View.Menu.Fragments;

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

import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.MealModel;
import com.example.foodplanner.Model.MealModelImpl;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Adapters.CategoryAdapter;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class CategorySearchFragment extends Fragment implements MealView {
    private MealPresenter presenter;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.action_categorySearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        MealModel mealModel = new MealModelImpl(mealApi);
        presenter = new MealPresenterImpl(this, mealModel);
        presenter.getCategories();

        adapter.setOnCategoryClickListener(category -> {
            // Navigate to MealsFragment
            if (getActivity() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("category_name", category.getName());
                Navigation.findNavController(view).navigate(R.id.action_categorySearchFragment_to_mealsFragment,bundle);
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

    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {
        adapter.setCategories(categories);

    }

    @Override
    public void showMeals(List<MealEntity> meals) {

    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {

    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}