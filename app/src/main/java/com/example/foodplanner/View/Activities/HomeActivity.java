package com.example.foodplanner.View.Activities;

import static android.view.View.VISIBLE;


import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Model.Network.NetworkUtil;
import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.Network.NetworkChangeReceiver;
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
import com.example.foodplanner.Model.Repository.Repository.MealRepository;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.Presenter.DataPresenter;
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MealView, AuthView {

    DrawerLayout drawerLayout;
    TextView nameTextView;
    ProgressBar progressBar;
    AuthPresenter authPresenter;
    NavController navController;
    NetworkChangeReceiver networkChangeReceiver;
    MealPresenterImpl presenter;
    String email;
    boolean loggedIn;
    NavigationView navigationView;
    View headerView;
    ActionBar actionBar;
    DataPresenter dataPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Register network receiver
        intitilaizeNetworkReciever();

        authPresenter = new AuthPresenter(this, new AuthModel(getBaseContext()));
        email = authPresenter.getEmail();
        loggedIn = authPresenter.isLoggedIn();
        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(this)),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));

        dataPresenter = new DataPresenter(new DataRepository());

        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.main);

        headerView = navigationView.getHeaderView(0);
        nameTextView = headerView.findViewById(R.id.name_tv);
        progressBar = findViewById(R.id.progressBar2);

        nameTextView.setText(email);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.home_24dp_e8eaed_fill0_wght400_grad0_opsz24);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.randomMeal) {
                        navController.navigate(R.id.randomMeal);
                    }
                    else if(id ==R.id.nameSearchFragment)
                    {
                        if(NetworkUtil.isNetworkConnected(getBaseContext()))
                        {
                            navController.navigate(R.id.nameSearchFragment);
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(id ==R.id.countrySearchFragment)
                    {
                        navController.navigate(R.id.countrySearchFragment);
                    }
                    else if(id ==R.id.categorySearchFragment)
                    {
                        navController.navigate(R.id.categorySearchFragment);
                    }
                    else if(id ==R.id.ingredientSearchFragment)
                    {
                        if(NetworkUtil.isNetworkConnected(getBaseContext()))
                        {
                            navController.navigate(R.id.ingredientSearchFragment);
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(id ==R.id.mealPlannerFragment)
                    {
                        if(loggedIn) {
                            navigateToMealPlannerFragment();
                        }
                        else
                        {
                            showBottomSheet();
                        }
                    }
                    else if(id == R.id.favouritesFragment)
                    {
                        if(loggedIn)
                        {
                            navigateToFavouritesFragment();
                        }
                        else
                        {
                            showBottomSheet();
                        }
                    }
                    else if(id ==R.id.signout)
                    {
                        if(NetworkUtil.isNetworkConnected(getBaseContext()) || !loggedIn)
                        {
                            handleSignOut();
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(id ==R.id.exit)
                    {
                        finish();
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_primary)));
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setElevation(22.0F);
                actionBar.setTitle(destination.getLabel());
                LayoutInflater inflater = LayoutInflater.from(this);
                TextView customTitle = (TextView) inflater.inflate(R.layout.custom_action_bar_title, null);
                actionBar.setCustomView(customTitle);
                actionBar.setDisplayShowCustomEnabled(true);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleSignOut()
    {
        progressBar.setVisibility(VISIBLE);
        if(loggedIn)
        {
            saveToFirebase();
        }
        authPresenter.signOut();
    }

    private void saveToFirebase() {
        presenter.getFavMeals().observe(HomeActivity.this, meals -> {
            if (meals != null) {
                // Convert LiveData<List<MealEntity>> to List<MealEntity>
                List<MealEntity> mealList = new ArrayList<>(meals);
                dataPresenter.saveMealsToFirebase(mealList, email,"Favourites");
            }
        });

        presenter.getMondayMeals().observe(this, mondays -> {
            if (mondays != null) {
                List<MealEntity> mondayMeals = new ArrayList<>();
                for (Monday monday : mondays) {
                    mondayMeals.add(new MealEntity(monday.getMealId(), monday.getMealName(), monday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(mondayMeals,email,"Monday");
            }
        });

        presenter.getTuesdayMeals().observe(this, tuesdays -> {
            if (tuesdays != null) {
                List<MealEntity> tuesdayMeals = new ArrayList<>();
                for (Tuesday tuesday : tuesdays) {
                    tuesdayMeals.add(new MealEntity(tuesday.getMealId(), tuesday.getMealName(), tuesday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(tuesdayMeals,email,"Tuesday");
            }
        });

        presenter.getWednesdayMeals().observe(this, wednesdays -> {
            if (wednesdays != null) {
                List<MealEntity> wednesdayMeals = new ArrayList<>();
                for (Wednesday wednesday : wednesdays) {
                    wednesdayMeals.add(new MealEntity(wednesday.getMealId(), wednesday.getMealName(), wednesday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(wednesdayMeals,email,"Wednesday");
            }
        });

        presenter.getThursdayMeals().observe(this, thursdays -> {
            if (thursdays != null) {
                List<MealEntity> thursdayMeals = new ArrayList<>();
                for (Thursday thursday : thursdays) {
                    thursdayMeals.add(new MealEntity(thursday.getMealId(), thursday.getMealName(), thursday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(thursdayMeals,email,"Thursday");
            }
        });

        presenter.getFridayMeals().observe(this, fridays -> {
            if (fridays != null) {
                List<MealEntity> fridayMeals = new ArrayList<>();
                for (Friday friday : fridays) {
                    fridayMeals.add(new MealEntity(friday.getMealId(), friday.getMealName(), friday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(fridayMeals,email,"Friday");
            }
        });

        presenter.getSaturdayMeals().observe(this, saturdays -> {
            if (saturdays != null) {
                List<MealEntity> saturdayMeals = new ArrayList<>();
                for (Saturday saturday : saturdays) {
                    saturdayMeals.add(new MealEntity(saturday.getMealId(), saturday.getMealName(), saturday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(saturdayMeals,email,"Saturday");
            }
        });

        presenter.getSundayMeals().observe(this, sundays -> {
            if (sundays != null) {
                List<MealEntity> sundayMeals = new ArrayList<>();
                for (Sunday sunday : sundays) {
                    sundayMeals.add(new MealEntity(sunday.getMealId(), sunday.getMealName(), sunday.getStrMealThumb()));
                }
                dataPresenter.saveMealsToFirebase(sundayMeals,email,"Sunday");
            }
        });
    }

    private void intitilaizeNetworkReciever()
    {
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, filter);
    }
    private void navigateToFavouritesFragment()
    {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_host_fragment2, true)
                .setLaunchSingleTop(true) // Prevent re-adding if already at the top
                .build();
        navController.navigate(R.id.favouritesFragment,null, navOptions);
    }
    private void navigateToMealPlannerFragment()
    {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_host_fragment2, true)
                .setLaunchSingleTop(true) // Prevent re-adding if already at the top
                .build();
        navController.navigate(R.id.mealPlannerFragment,null, navOptions);
    }

    private void showBottomSheet()
    {
        LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
        bottomSheet.show(getSupportFragmentManager(), "LoginBottomSheetFragment");
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void navigateToHome(String email) {

    }

    @Override
    public void navigateToSignUp() {
        Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setEmailError(String error) {

    }

    @Override
    public void setPasswordError(String error) {

    }
}
