package com.example.foodplanner.View.Menu.Fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.TransitionInflater;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide; // Add this dependency for loading images
import com.bumptech.glide.request.RequestOptions;
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
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Adapters.IngredientAdapter;
import com.example.foodplanner.View.Menu.Interfaces.MealExistCallback;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealDetailsFragment extends Fragment implements MealView {
    private String mealName;
    private MealPresenterImpl presenter;
    private TextView mealTitle, mealOrigin, mealIngredients, mealSteps, steps;
    private WebView webView;
    FloatingActionButton fab;
    MealEntity currentMeal;
    private ImageView mealImage, countryImage;
    private RecyclerView recyclerView;
    LoggedInPresenter loggedInPresenter;
    UpdateMealsPresenter updateMealsPresenter;
    IngredientAdapter adapter;
    boolean loggedIn;
    List<IngredientResponse.Ingredient> ingredientImageUrls;

    public static final Map<String, Integer> COUNTRY_DRAWABLES = new HashMap<>();
    static {
        COUNTRY_DRAWABLES.put("American", R.drawable.american);
        COUNTRY_DRAWABLES.put("British", R.drawable.british);
        COUNTRY_DRAWABLES.put("Canadian", R.drawable.canadian);
        COUNTRY_DRAWABLES.put("Chinese", R.drawable.chinese);
        COUNTRY_DRAWABLES.put("Croatian", R.drawable.croatian);
        COUNTRY_DRAWABLES.put("Dutch", R.drawable.dutch);
        COUNTRY_DRAWABLES.put("Egyptian", R.drawable.egyptian);
        COUNTRY_DRAWABLES.put("Filipino", R.drawable.filipino);
        COUNTRY_DRAWABLES.put("French", R.drawable.french);
        COUNTRY_DRAWABLES.put("Greek", R.drawable.greek);
        COUNTRY_DRAWABLES.put("Indian", R.drawable.indian);
        COUNTRY_DRAWABLES.put("Irish", R.drawable.irish);
        COUNTRY_DRAWABLES.put("Italian", R.drawable.italian);
        COUNTRY_DRAWABLES.put("Jamaican", R.drawable.jamaican);
        COUNTRY_DRAWABLES.put("Japanese", R.drawable.japanese);
        COUNTRY_DRAWABLES.put("Kenyan", R.drawable.kenyan);
        COUNTRY_DRAWABLES.put("Malaysian", R.drawable.malaysian);
        COUNTRY_DRAWABLES.put("Mexican", R.drawable.mexican);
        COUNTRY_DRAWABLES.put("Moroccan", R.drawable.moroccan);
        COUNTRY_DRAWABLES.put("Polish", R.drawable.polish);
        COUNTRY_DRAWABLES.put("Portuguese", R.drawable.portuguese);
        COUNTRY_DRAWABLES.put("Russian", R.drawable.russian);
        COUNTRY_DRAWABLES.put("Spanish", R.drawable.spanish);
        COUNTRY_DRAWABLES.put("Thai", R.drawable.thai);
        COUNTRY_DRAWABLES.put("Tunisian", R.drawable.tunisian);
        COUNTRY_DRAWABLES.put("Turkish", R.drawable.turkish);
        COUNTRY_DRAWABLES.put("Ukrainian", R.drawable.ukrainian);
        COUNTRY_DRAWABLES.put("Vietnamese", R.drawable.vietnamese);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealName = getArguments().getString("meal_name");
        }
        loggedInPresenter = new LoggedInPresenter(new AuthModel(getContext()));
        loggedIn = loggedInPresenter.isLoggedIn();

        ingredientImageUrls = new ArrayList<>();

        setEnterTransition(new Slide(Gravity.RIGHT));
        setExitTransition(new Slide(Gravity.LEFT));
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.transition_shared_element));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealTitle = view.findViewById(R.id.mealName);
        mealOrigin = view.findViewById(R.id.mealOrigin);
        mealIngredients = view.findViewById(R.id.mealIngredients);
        mealSteps = view.findViewById(R.id.mealSteps);
        steps = view.findViewById(R.id.steps);
        webView = view.findViewById(R.id.webview);
        mealImage = view.findViewById(R.id.mealImage);
        countryImage = view.findViewById(R.id.imageView);
        recyclerView = view.findViewById(R.id.recyclerView2);
        fab = view.findViewById(R.id.fab);

        adapter = new IngredientAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mealTitle.setText(mealName);

        updateMealsPresenter = new UpdateMealsPresenter(new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));

        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(requireContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));

        presenter.searchMeals(mealName);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loggedIn)
                {
                    if(currentMeal!=null)
                    {
                        doesMealExist(currentMeal.getIdMeal(), exists -> {
                            getActivity().runOnUiThread(() -> {
                                String message;

                                if (exists) {
                                    getActivity().runOnUiThread(() ->
                                            Snackbar.make(view, currentMeal.getStrMeal() + " deleted from favorites", Snackbar.LENGTH_SHORT).show()
                                    );
                                    updateMealsPresenter.deleteMeal(currentMeal);
                                } else {
                                    getActivity().runOnUiThread(() ->
                                            Snackbar.make(view, currentMeal.getStrMeal() + " added from favorites", Snackbar.LENGTH_SHORT).show()
                                    );
                                    updateMealsPresenter.insertMeal(currentMeal);
                                }
                                updateFabColorAfterUpdate(currentMeal.getIdMeal());
                            });
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                    bottomSheet.show(getActivity().getSupportFragmentManager(), "LoginBottomSheetFragment");
                }
            }
        });
    }

    @Override
    public void showMeal(MealEntity meal) {
        // Implement this method if you need to show a single meal
    }

    private void doesMealExist(String mealId, MealExistCallback callback) {
        updateMealsPresenter.isMealExists(mealId, exists -> {
            callback.onResult(exists);
        });
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

                fab.setBackgroundTintList(ColorStateList.valueOf(color));
            });
        });
    }
    public void showMealDetails(MealEntity meal) {
        if (meal != null) {
            currentMeal = meal;
            updateFabColor(meal.getIdMeal());
            mealTitle.setText(meal.getStrMeal());
            mealOrigin.setText("Origin: " + meal.getStrArea());

            checkCountry(meal.getStrArea());

            String instructions = meal.getStrInstructions();
            if (instructions != null && !instructions.isEmpty()) {
                String[] stepsArray = instructions.split("\r\n|\n");
                StringBuilder stepsText = new StringBuilder();
                int stepNumber = 1;
                int totalSteps = stepsArray.length;

                for (int i = 0; i < totalSteps; i++) {
                    String step = stepsArray[i].trim();
                    if (!step.isEmpty()) {
                        stepsText.append("Step ").append(stepNumber).append(": ").append(step);
                        if (i < totalSteps - 1) {
                            stepsText.append("\n----------------------------------------------------------\n");
                        }
                        stepNumber++;
                    }
                }
                stepNumber--;
                mealSteps.setText(stepsText.toString());
                steps.append(": " + stepNumber);
            }


            Glide.with(this).load(meal.getStrMealThumb()).apply(new RequestOptions())
                    .placeholder(R.drawable.img_11).into(mealImage);

            StringBuilder ingredientsText = new StringBuilder("Ingredients:\n");

            for (int i = 1; i <= 20; i++) {
                String ingredient = null;
                String measure = null;

                switch (i) {
                    case 1:
                        ingredient = meal.getStrIngredient1();
                        measure = meal.getStrMeasure1();
                        break;
                    case 2:
                        ingredient = meal.getStrIngredient2();
                        measure = meal.getStrMeasure2();
                        break;
                    case 3:
                        ingredient = meal.getStrIngredient3();
                        measure = meal.getStrMeasure3();
                        break;
                    case 4:
                        ingredient = meal.getStrIngredient4();
                        measure = meal.getStrMeasure4();
                        break;
                    case 5:
                        ingredient = meal.getStrIngredient5();
                        measure = meal.getStrMeasure5();
                        break;
                    case 6:
                        ingredient = meal.getStrIngredient6();
                        measure = meal.getStrMeasure6();
                        break;
                    case 7:
                        ingredient = meal.getStrIngredient7();
                        measure = meal.getStrMeasure7();
                        break;
                    case 8:
                        ingredient = meal.getStrIngredient8();
                        measure = meal.getStrMeasure8();
                        break;
                    case 9:
                        ingredient = meal.getStrIngredient9();
                        measure = meal.getStrMeasure9();
                        break;
                    case 10:
                        ingredient = meal.getStrIngredient10();
                        measure = meal.getStrMeasure10();
                        break;
                    case 11:
                        ingredient = meal.getStrIngredient11();
                        measure = meal.getStrMeasure11();
                        break;
                    case 12:
                        ingredient = meal.getStrIngredient12();
                        measure = meal.getStrMeasure12();
                        break;
                    case 13:
                        ingredient = meal.getStrIngredient13();
                        measure = meal.getStrMeasure13();
                        break;
                    case 14:
                        ingredient = meal.getStrIngredient14();
                        measure = meal.getStrMeasure14();
                        break;
                    case 15:
                        ingredient = meal.getStrIngredient15();
                        measure = meal.getStrMeasure15();
                        break;
                    case 16:
                        ingredient = meal.getStrIngredient16();
                        measure = meal.getStrMeasure16();
                        break;
                    case 17:
                        ingredient = meal.getStrIngredient17();
                        measure = meal.getStrMeasure17();
                        break;
                    case 18:
                        ingredient = meal.getStrIngredient18();
                        measure = meal.getStrMeasure18();
                        break;
                    case 19:
                        ingredient = meal.getStrIngredient19();
                        measure = meal.getStrMeasure19();
                        break;
                    case 20:
                        ingredient = meal.getStrIngredient20();
                        measure = meal.getStrMeasure20();
                        break;
                }

                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredientsText.append(measure).append(" ").append(ingredient).append("\n");

                    IngredientResponse.Ingredient ingredient1= new IngredientResponse.Ingredient();
                    ingredient1.setName(ingredient);
                    ingredient1.setMeasure(measure);
                    ingredientImageUrls.add(ingredient1);
                }
            }
            adapter.setIngredients(ingredientImageUrls);
            mealIngredients.append(" " + ingredientImageUrls.size());
        }

        // Load video
        String videoUrl = meal.getStrYoutube();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            String embedUrl = "https://www.youtube.com/embed/" + extractVideoId(videoUrl);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            /*
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                    if (fullscreenContainer != null) {
                        return;
                    }

                    fullscreenContainer = new FrameLayout(getActivity());
                    fullscreenContainer.addView(view);
                    getActivity().setContentView(fullscreenContainer);
                    customViewCallback = callback;
                }

                @Override
                public void onHideCustomView() {
                    if (fullscreenContainer == null) {
                        return;
                    }

                    getActivity().setContentView(R.layout.fragment_meal_details); // Restore your original layout
                    fullscreenContainer.removeAllViews();
                    fullscreenContainer = null;
                    customViewCallback.onCustomViewHidden();
                }
            });*/
            webView.loadUrl(embedUrl);
        } else {
            Toast.makeText(getActivity(), "Video URL not available", Toast.LENGTH_SHORT).show();
        }
    }

    private String extractVideoId(String url) {
        String videoId = "";
        String[] parts = url.split("v=");
        if (parts.length > 1) {
            videoId = parts[1];
            int ampersandIndex = videoId.indexOf("&");
            if (ampersandIndex != -1) {
                videoId = videoId.substring(0, ampersandIndex);
            }
        }
        return videoId;
    }
    @Override
    public void showError(String message) {
        if(message!=null)
        {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMeals(List<MealEntity> meals) {
        if (meals != null && !meals.isEmpty()) {
            showMealDetails(meals.get(0));
        } else {
            showError("No meal details found");
        }
    }

    @Override
    public void addMeal(MealEntity meal) {

    }

    public void checkCountry(String origin) {
        Integer drawableRes = COUNTRY_DRAWABLES.get(origin);
        if (drawableRes != null) {
            countryImage.setImageResource(drawableRes);
        }
    }
}
