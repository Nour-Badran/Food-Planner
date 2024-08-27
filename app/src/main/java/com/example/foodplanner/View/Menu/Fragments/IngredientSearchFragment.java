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
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.IngredientPresenter;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Adapters.IngredientAdapter;
import com.example.foodplanner.View.Menu.Interfaces.IngredientView;
import com.example.foodplanner.View.Menu.Interfaces.MealView;

import java.util.List;

public class IngredientSearchFragment extends Fragment implements IngredientView {
    //private MealPresenter presenter;
    private IngredientPresenter ingredientPresenter;
    private RecyclerView recyclerView;
    private IngredientAdapter adapter;
    private SearchView searchViewIngredient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.action_ingredientSearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchViewIngredient = view.findViewById(R.id.searchViewIngredient);
        searchViewIngredient.setIconifiedByDefault(false); // Ensure SearchView is always expanded
        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        adapter = new IngredientAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        ingredientPresenter = new IngredientPresenter(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        ingredientPresenter.getIngredients();

        searchViewIngredient.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ingredientPresenter.getIngredientsBySubstring(query.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    ingredientPresenter.getIngredientsBySubstring(newText.trim());
                }
                return false;
            }
        });

        adapter.setOnIngredientClickListener(ingredient -> {
            if (getActivity() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("ingredient_name", ingredient.getName());
                Navigation.findNavController(view).navigate(R.id.action_ingredientSearchFragment_to_mealsFragment, bundle);
            }
        });
    }
    @Override
    public void showError(String message) {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {
        adapter.setIngredients(ingredients);
        recyclerView.setVisibility(View.VISIBLE);}

}