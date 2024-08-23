package com.example.foodplanner.View.Menu.Fragments.Auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Model.Network.NetworkUtil;
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.MealDB.MealLocalDataSourceImpl;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealApi;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.MealRemoteDataSource;
import com.example.foodplanner.Model.Repository.MealRemoteDataSource.RetrofitClient;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Friday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Saturday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Sunday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Thursday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Tuesday;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Wednesday;
import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Model.Repository.Repository.OnMealsLoadedListener;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.Presenter.DataPresenter;
import com.example.foodplanner.Presenter.MealPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Activities.HomeActivity;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import java.util.ArrayList;
import java.util.List;

public class Signup_Fragment extends Fragment implements AuthView, MealView {

    private Button guestButton, signUp, goToLogin;
    private EditText username, email, password, confirmPassword;
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private AuthPresenter presenter;
    private MealPresenter mealPresenter;
    DataPresenter dataPresenter;

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    try {
                        GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class);
                        if (account != null) {
                            presenter.signInWithGoogle(account);
                        }
                    } catch (ApiException e) {
                        showToast("Google sign-in failed: " + e.getMessage());
                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataPresenter = new DataPresenter(new DataRepository());

        presenter = new AuthPresenter(this, new AuthModel(requireContext()));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mealPresenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(getContext())),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

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
        return inflater.inflate(R.layout.fragment_signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.username);
        signUp = view.findViewById(R.id.signUpButton);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        progressBar = view.findViewById(R.id.progressBar);
        goToLogin = view.findViewById(R.id.loginButton);
        guestButton = view.findViewById(R.id.guestButton);

        ImageView googleSignIn = view.findViewById(R.id.google);
        googleSignIn.setOnClickListener(v -> {
            if(NetworkUtil.isNetworkConnected(getContext()))
            {
                googleSignIn();
            }
            else
            {
                Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            }
        });

        guestButton.setOnClickListener(v -> {
            showLoading();
            loadMealsFromfirebase("Guest");
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
            hideLoading();
        });
        goToLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signup_fragment_to_loginFragment);
        });

        signUp.setOnClickListener(v -> {
            if(NetworkUtil.isNetworkConnected(getContext()))
            {
                String user = username.getText().toString().trim();
                String emaill = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();
                presenter.signUp(user, emaill, pass, confirmPass);
            }
            else
            {
                Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadMealsFromfirebase(String email)
    {
        dataPresenter.loadFromFirebase(email, "Favourites", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    mealPresenter.updateMeals(meals);
                }
                else
                {
                    mealPresenter.updateMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Monday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Monday> mondayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Monday monday = new Monday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        mondayMeals.add(monday);
                    }
                    mealPresenter.updateMondayMeals(mondayMeals);
                } else {
                    mealPresenter.updateMondayMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Tuesday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Tuesday> tuesdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Tuesday tuesday = new Tuesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        tuesdayMeals.add(tuesday);
                    }
                    mealPresenter.updateTuesdayMeals(tuesdayMeals);
                } else {
                    mealPresenter.updateTuesdayMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Wednesday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Wednesday> wednesdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Wednesday wednesday = new Wednesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        wednesdayMeals.add(wednesday);
                    }
                    mealPresenter.updateWednesdayMeals(wednesdayMeals);
                } else {
                    mealPresenter.updateWednesdayMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Thursday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Thursday> thursdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Thursday thursday = new Thursday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        thursdayMeals.add(thursday);
                    }
                    mealPresenter.updateThursdayMeals(thursdayMeals);
                } else {
                    mealPresenter.updateThursdayMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Friday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Friday> fridayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Friday friday = new Friday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        fridayMeals.add(friday);
                    }
                    mealPresenter.updateFridayMeals(fridayMeals);
                } else {
                    mealPresenter.updateFridayMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Saturday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Saturday> saturdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Saturday saturday = new Saturday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        saturdayMeals.add(saturday);
                    }
                    mealPresenter.updateSaturdayMeals(saturdayMeals);
                } else {
                    mealPresenter.updateSaturdayMeals(new ArrayList<>());
                }
            }
        });
        dataPresenter.loadFromFirebase(email, "Sunday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Sunday> sundayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Sunday sunday = new Sunday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        sundayMeals.add(sunday);
                    }
                    mealPresenter.updateSundayMeals(sundayMeals);
                } else {
                    mealPresenter.updateSundayMeals(new ArrayList<>());
                }
            }
        });
    }
    private void googleSignIn() {
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(intent);
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome(String email) {
        loadMealsFromfirebase(email);
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void navigateToSignUp() {

    }

    @Override
    public void setEmailError(String error) {
        email.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        password.setError(error);
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
    public void showMessage(String message) {

    }

    @Override
    public void showCategories(List<CategoryResponse.Category> categories) {

    }

    @Override
    public void showMeals(List<MealEntity> meals) {

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
}




