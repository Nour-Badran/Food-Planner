package com.example.foodplanner.View.Menu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.CategoryResponse;
import com.example.foodplanner.Model.IngredientResponse;
import com.example.foodplanner.Model.MealApi;
import com.example.foodplanner.Model.MealEntity;
import com.example.foodplanner.Model.RetrofitClient;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class RandomMealFragment extends Fragment implements MealView {

    private MealPresenter presenter;
    ImageView mealImage;
    ImageView next;
    ImageView back;
    TextView mealName;
    TextView mealCategory;
    TextView mealArea;
    List<MealEntity> mealsList;
    int currentIndex = 0;
    private FloatingActionButton fab;
//    private LinearLayout fabMenuContainer;
//    private boolean isFabMenuOpen = false;
    private int currentFabColor;
    ChipGroup chipGroup;
    RecyclerView recyclerView;
    MealAdapter mealAdapter;
    CategoryAdapter categoryAdapter;

    IngredientAdapter ingredientAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealsList = new ArrayList<>();
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with the action to navigate to another fragment
                requireActivity().finish();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random_meal, container, false);
    }

    private void addChipToGroup(String chipName)
    {
        Chip chip = new Chip(getContext());
        chip.setText(chipName);
        chip.setTextSize(20);
        chip.setBackgroundResource(R.drawable.rounded_edit_text);
        chip.setOnClickListener(v -> {
            switch (chipName) {
                case "Categories":
                    recyclerView.setAdapter(categoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    presenter.getCategories();
                    break;
//                case "Meals":
//                    mealAdapter = new MealAdapter();
//                    recyclerView.setAdapter(mealAdapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    presenter.getAllMeals();
//                    break;
//                case "Ingredients":
//                    ingredientAdapter = new IngredientAdapter();
//                    recyclerView.setAdapter(ingredientAdapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    presenter.getIngredients();
//                    break;
                default:
                    break;
            }
        });
        chipGroup.addView(chip);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MealApi mealApi = RetrofitClient.getClient().create(MealApi.class);
        presenter = new MealPresenterImpl(this, mealApi);
        presenter.loadRandomMeal();
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        chipGroup = view.findViewById(R.id.chipGroup);
        mealCategory = view.findViewById(R.id.mealCategory);
        mealArea = view.findViewById(R.id.mealArea);
        next = view.findViewById(R.id.next);
        back = view.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        recyclerView = view.findViewById(R.id.recyclerView);

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnCategoryClickListener(category -> {
            // Navigate to MealsFragment
            if (getActivity() != null) {
                //Toast.makeText(getActivity(), category.getName(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("category_name", category.getName());
                Navigation.findNavController(view).navigate(R.id.action_randomMeal_to_mealsFragment,bundle);
            }
        });
        addChipToGroup("Categories");
//        addChipToGroup("Meals");
//        addChipToGroup("Ingredients");
        currentFabColor = ContextCompat.getColor(getContext(), R.color.blue_primary);

        fab = view.findViewById(R.id.fab);
        animateFabColor(ContextCompat.getColor(getContext(), R.color.gray));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to favourites!", Toast.LENGTH_SHORT).show();
                int newColor = (currentFabColor == ContextCompat.getColor(getContext(), R.color.gray))
                        ? ContextCompat.getColor(getContext(), R.color.areaBackgroundColor)
                        : ContextCompat.getColor(getContext(), R.color.gray);
                animateFabColor(newColor);
            }
        });
//        fabMenuContainer = view.findViewById(R.id.fab_menu_container);

//        fab.setOnClickListener(v -> {
//            if (isFabMenuOpen) {
//                closeFabMenu();
//                fab.setImageResource(R.drawable.bookmark_24dp_e8eaed_fill0_wght400_grad0_opsz24);
//                animateFabColor(ContextCompat.getColor(getContext(), R.color.gray));
//            } else {
//                openFabMenu();
//                fab.setImageResource(R.drawable.close_24dp_e8eaed_fill0_wght400_grad0_opsz24);
//                animateFabColor(ContextCompat.getColor(getContext(), R.color.blue_primary)); // Example color for opened state
//            }
//            isFabMenuOpen = !isFabMenuOpen;
//        });

        // Handle menu FAB clicks
//        view.findViewById(R.id.fab_option1).setOnClickListener(v -> {
//            Toast.makeText(getActivity(), "First", Toast.LENGTH_SHORT).show();
//            if (isFabMenuOpen) {
//                closeFabMenu();
//                fab.setImageResource(R.drawable.bookmark_24dp_e8eaed_fill0_wght400_grad0_opsz24);
//                animateFabColor(ContextCompat.getColor(getContext(), R.color.gray));
//            } else {
//                openFabMenu();
//                fab.setImageResource(R.drawable.close_24dp_e8eaed_fill0_wght400_grad0_opsz24);
//                animateFabColor(ContextCompat.getColor(getContext(), R.color.blue_primary)); // Example color for opened state
//            }
//            isFabMenuOpen = !isFabMenuOpen;
//        });

//        view.findViewById(R.id.fab_option2).setOnClickListener(v -> {
//            Toast.makeText(getActivity(), "Second", Toast.LENGTH_SHORT).show();
//            if (isFabMenuOpen) {
//                closeFabMenu();
//                fab.setImageResource(R.drawable.bookmark_24dp_e8eaed_fill0_wght400_grad0_opsz24);
//                animateFabColor(ContextCompat.getColor(getContext(), R.color.gray));
//            } else {
//                openFabMenu();
//                fab.setImageResource(R.drawable.close_24dp_e8eaed_fill0_wght400_grad0_opsz24);
//                animateFabColor(ContextCompat.getColor(getContext(), R.color.blue_primary)); // Example color for opened state
//            }
//            isFabMenuOpen = !isFabMenuOpen;
//        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex<mealsList.size()-1)
                {
                    currentIndex++;
                    showStoredMeal(mealsList.get(currentIndex));
                }
                else
                {
                    presenter.loadRandomMeal();
                    currentIndex++;
                }
                back.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex>0)
                {
                    currentIndex--;
                    showStoredMeal(mealsList.get(currentIndex));
                    if(currentIndex==0)
                    {
                        back.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullMealName = mealName.getText().toString();
                String mealNameWithoutPrefix = fullMealName.replace("Meal Name: ", "").trim();
                Bundle bundle = new Bundle();
                bundle.putString("meal_name", mealNameWithoutPrefix);

                NavController navController = Navigation.findNavController(view);

                // Create FragmentNavigator.Extras
                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(mealImage, "shared_image_transition")
                        .build();

                // Navigate with extras
                navController.navigate(R.id.action_randomMeal_to_mealDetailsFragment, bundle, null, extras);
            }
        });
    }
    private FragmentNavigator.Extras getTransitionOptions() {
        ImageView sharedImage = requireView().findViewById(R.id.mealImage);
        return new FragmentNavigator.Extras.Builder()
                .addSharedElement(sharedImage, "shared_image_transition")
                .build();
    }
    private void animateFabColor(int newColor) {
        int startColor = currentFabColor;
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(startColor, newColor);
        colorAnimator.setDuration(300);
        colorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimator.addUpdateListener(animator -> {
            int animatedColor = (int) animator.getAnimatedValue();
            fab.setBackgroundTintList(ColorStateList.valueOf(animatedColor));
        });
        colorAnimator.start();
        currentFabColor = newColor;
    }
//    private void openFabMenu() {
//        fabMenuContainer.setVisibility(View.VISIBLE);
//        fabMenuContainer.animate()
//                .translationY(0)
//                .setDuration(300)
//                .start();
//    }
//
//    private void closeFabMenu() {
//        fabMenuContainer.animate()
//                .translationY(fabMenuContainer.getHeight())
//                .setDuration(300)
//                .withEndAction(() -> fabMenuContainer.setVisibility(View.GONE))
//                .start();
//    }
    @Override
    public void showMeal(MealEntity meal) {
        mealsList.add(meal);
        mealName.setText("Meal Name: " + meal.getStrMeal());
        mealCategory.setText("Category: " + meal.getStrCategory());
        mealArea.setText("Area: " + meal.getStrArea());
        Glide.with(this).load(meal.getStrMealThumb()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11).into(mealImage);
    }

    public void showStoredMeal(MealEntity meal)
    {
        mealName.setText("Meal Name: " + meal.getStrMeal());
        mealCategory.setText("Category: " + meal.getStrCategory());
        mealArea.setText("Area: " + meal.getStrArea());
        Glide.with(this).load(meal.getStrMealThumb()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11).into(mealImage);
    }
    @Override
    public void showMealDetails(MealEntity meal) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {
        categoryAdapter.setCategories(categories);
    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        mealAdapter.setMeals(meals);

    }

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {
        ingredientAdapter.setIngredients(ingredients);
    }

    @Override
    public void getMealsByCategory(String categoryName) {

    }
}