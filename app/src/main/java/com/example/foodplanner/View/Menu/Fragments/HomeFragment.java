package com.example.foodplanner.View.Menu.Fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Model.Network.NetworkUtil;
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
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Adapters.CategoryAdapter;
import com.example.foodplanner.View.Menu.Adapters.IngredientAdapter;
import com.example.foodplanner.View.Menu.Adapters.MealAdapter;
import com.example.foodplanner.View.Menu.Interfaces.MealExistCallback;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements MealView, AuthView {

    private MealPresenter presenter;
    ImageView mealImage;
    ImageView next;
    ImageView back;
    TextView mealName;
    TextView mealCategory;
    TextView mealArea;
    List<MealEntity> mealsList;
    int currentIndex = -1;
    private FloatingActionButton fab;
    private int currentFabColor;
    ChipGroup chipGroup;
    RecyclerView recyclerView;
    MealAdapter mealAdapter;
    CategoryAdapter categoryAdapter;
    IngredientAdapter ingredientAdapter;
    AuthPresenter authPresenter;
    boolean loggedIn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealsList = new ArrayList<>();
        authPresenter = new AuthPresenter(this, new AuthModel(getContext()));
        loggedIn = authPresenter.isLoggedIn();
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_meal, container, false);
    }

    private void addChipToGroup(String chipName)
    {
        Chip chip = new Chip(getContext());
        chip.setText(chipName);
        chip.setTextSize(28);
        chip.setBackgroundResource(R.drawable.rounded_edit_text);
        chip.setOnClickListener(v -> {
            switch (chipName) {
                case "Categories":
                    if (chip.getTag() == null) {
                        chip.setTag("Open");
                        recyclerView.setAdapter(categoryAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setTranslationY(-75);
                        recyclerView.animate()
                                .translationY(0)
                                .setDuration(300)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(null);
                        presenter.getCategories();
                    } else if (chip.getTag().equals("Open")) {
                        chip.setTag(null);
                        recyclerView.animate()
                                .translationY(-75)
                                .setDuration(300)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        recyclerView.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationStart(Animator animation) {}

                                    @Override
                                    public void onAnimationCancel(Animator animation) {}

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {}
                                });
                    }
                    break;
            }
        });

        chipGroup.addView(chip);
    }

    private void updateFabColor(String mealId) {
        doesMealExist(mealId, exists -> {
            getActivity().runOnUiThread(() -> {
                int color;

                if (exists) {
                    color = ContextCompat.getColor(getContext(), R.color.areaBackgroundColor);
                } else {
                    color = ContextCompat.getColor(getContext(), R.color.gray);
                }
                fab.setBackgroundTintList(ColorStateList.valueOf(color));
            });
        });
    }

    private void updateFabColorAfterUpdate(String mealId) {
        doesMealExist(mealId, exists -> {
            getActivity().runOnUiThread(() -> {
                int color;

                if (exists) {
                    color = ContextCompat.getColor(getContext(), R.color.gray);
                } else {
                    color = ContextCompat.getColor(getContext(), R.color.areaBackgroundColor);
                }
                animateFabColor(color);
            });
        });
    }
    private void doesMealExist(String mealId, MealExistCallback callback) {
        presenter.isMealExists(mealId, exists -> {
            callback.onResult(exists);
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        chipGroup = view.findViewById(R.id.chipGroup);
        mealCategory = view.findViewById(R.id.mealCategory);
        mealArea = view.findViewById(R.id.mealArea);
        next = view.findViewById(R.id.next);
        back = view.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView);

        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        presenter.loadRandomMeal();

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnCategoryClickListener(category -> {
            if (getActivity() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("category_name", category.getName());
                Navigation.findNavController(view).navigate(R.id.action_randomMeal_to_mealsFragment,bundle);
            }
        });
        addChipToGroup("Categories");

        currentFabColor = ContextCompat.getColor(getContext(), R.color.gray);
        animateFabColor(ContextCompat.getColor(getContext(), R.color.gray));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loggedIn)
                {
                    if(NetworkUtil.isNetworkConnected(getContext()) && !mealsList.isEmpty())
                    {
                        doesMealExist(mealsList.get(currentIndex).getIdMeal(), exists -> {
                            getActivity().runOnUiThread(() -> {
                                if (exists) {
                                    getActivity().runOnUiThread(() ->
                                            Toast.makeText(getContext(), mealsList.get(currentIndex).getStrMeal() + " deleted from favorites", Toast.LENGTH_SHORT).show()
                                    );
                                    presenter.deleteMeal(mealsList.get(currentIndex));
                                } else {
                                    getActivity().runOnUiThread(() ->
                                            Toast.makeText(getContext(), mealsList.get(currentIndex).getStrMeal() + " added to favorites", Toast.LENGTH_SHORT).show()
                                    );
                                    presenter.insertMeal(mealsList.get(currentIndex));
                                }
                                updateFabColorAfterUpdate(mealsList.get(currentIndex).getIdMeal());
                            });
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                    bottomSheet.show(getActivity().getSupportFragmentManager(), "LoginBottomSheetFragment");
                }
            }
        });

        next.setOnClickListener(v -> {
            if(NetworkUtil.isNetworkConnected(getContext()) && !mealsList.isEmpty() && mealImage!=null)
            {
                if (currentIndex < mealsList.size() - 1) {
                    currentIndex++;
                    updateFabColor(mealsList.get(currentIndex).getIdMeal());
                } else {
                    presenter.loadRandomMeal();
                }
                animateMealSlide(false);
                animateFabSlide(false);
                back.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(v -> {
            if(NetworkUtil.isNetworkConnected(getContext()) && mealImage!=null)
            {
                if (currentIndex > 0) {
                    currentIndex--;
                    animateMealZoom();
                    if (currentIndex == 0) {
                        back.setVisibility(View.INVISIBLE);
                    }
                }
                updateFabColor(mealsList.get(currentIndex).getIdMeal());
                fabAnimateMealZoom();
            }
            else {
                Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            }
        });

        mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullMealName = mealName.getText().toString();
                String mealNameWithoutPrefix = fullMealName.replace("Meal Name: ", "").trim();
                Bundle bundle = new Bundle();
                bundle.putString("meal_name", mealNameWithoutPrefix);
                currentIndex = -1;
                mealsList.clear();
                NavController navController = Navigation.findNavController(view);

                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(mealImage, "shared_image_transition")
                        .build();

                navController.navigate(R.id.action_randomMeal_to_mealDetailsFragment, bundle, null, extras);
            }
        });
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
    @Override
    public void showMeal(MealEntity meal) {
        currentIndex++;
        updateFabColor(meal.getIdMeal());
        mealsList.add(meal);
        mealName.setText("Meal Name: " + meal.getStrMeal());
        mealCategory.setText("Category: " + meal.getStrCategory());
        mealArea.setText("Country: " + meal.getStrArea());
        Glide.with(this).load(meal.getStrMealThumb()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11).into(mealImage);
    }

    public void showStoredMeal(MealEntity meal) {
        mealName.setText("Meal Name: " + meal.getStrMeal());
        mealCategory.setText("Category: " + meal.getStrCategory());
        mealArea.setText("Country: " + meal.getStrArea());
        Glide.with(this).load(meal.getStrMealThumb()).apply(new RequestOptions())
                .placeholder(R.drawable.img_11).into(mealImage);
    }

    @Override
    public void showMealDetails(MealEntity meal) {}

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
    public void addMeal(MealEntity meal) {}

    @Override
    public void showIngredients(List<IngredientResponse.Ingredient> ingredients) {
        ingredientAdapter.setIngredients(ingredients);
    }

    @Override
    public void getMealsByCategory(String categoryName) {}

    private void animateFabSlide(final boolean isNext) {
        float translationX = isNext ? fab.getWidth() : -fab.getWidth();
        fab.animate()
                .translationX(translationX)
                .alpha(0f)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fab.setTranslationX(-translationX); // Move it off-screen in the opposite direction
                        fab.animate()
                                .translationX(0)
                                .alpha(1f)
                                .setDuration(200)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
    }

    private void animateMealSlide(final boolean isNext) {
        float translationX = isNext ? mealImage.getWidth() : -mealImage.getWidth();
        mealImage.animate()
                .translationX(translationX)
                .alpha(0f)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mealImage.setTranslationX(-translationX); // Move it off-screen in the opposite direction
                        if (currentIndex < mealsList.size()) {
                            showStoredMeal(mealsList.get(currentIndex)); // Update the content
                        }
                        mealImage.animate()
                                .translationX(0)
                                .alpha(1f)
                                .setDuration(200)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
    }
    private void animateMealZoom() {
        mealImage.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .alpha(0f)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (currentIndex < mealsList.size()) {
                            showStoredMeal(mealsList.get(currentIndex)); // Update the content

                        }
                        mealImage.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(200)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
    }
    private void fabAnimateMealZoom() {
        fab.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .alpha(0f)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (currentIndex < mealsList.size()) {
                            showStoredMeal(mealsList.get(currentIndex)); // Update the content

                        }
                        fab.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(200)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showToast(String message) {}

    @Override
    public void navigateToHome(String email) {}

    @Override
    public void navigateToSignUp() {}

    @Override
    public void setEmailError(String error) {}

    @Override
    public void setPasswordError(String error) {}
}