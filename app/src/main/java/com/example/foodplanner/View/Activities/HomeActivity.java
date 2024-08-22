package com.example.foodplanner.View.Activities;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Model.Repository.Repository.DataRepository;
import com.example.foodplanner.Model.Network.NetworkChangeReceiver;
import com.example.foodplanner.Model.Repository.Repository.OnMealsLoadedListener;
import com.example.foodplanner.Model.POJO.CategoryResponse;
import com.example.foodplanner.Model.POJO.IngredientResponse;
import com.example.foodplanner.Model.Repository.DB.FavoriteMealDatabase;
import com.example.foodplanner.Model.Repository.MealDB.MealDao;
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
import com.example.foodplanner.Presenter.MealPresenterImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Auth.AuthActivity;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.example.foodplanner.View.Menu.Interfaces.MealView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MealView {

    DrawerLayout drawerLayout;
    TextView nameTextView;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    DataRepository dataRepository;

    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    SharedPreferences prefs;
    NavController navController;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    AuthModel authService;
    MealPresenterImpl presenter;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Register network receiver

        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, filter);

        ////////////////////////////////////////
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        email = prefs.getString("email", "Guest");
        presenter = new MealPresenterImpl(this, new MealRepository(new MealLocalDataSourceImpl(FavoriteMealDatabase.getInstance(this)),
                new MealRemoteDataSource(RetrofitClient.getClient().create(MealApi.class))));
        dataRepository = new DataRepository();
        //////////////////////////

//        if(!email.equals("Guest"))
//        {
//            loadMealsFromfirebase();
//        }

        //dataRepository.loadMeals(email);

        authService = new AuthModel(this);
        prefs = getSharedPreferences("FoodPlannerPrefs", MODE_PRIVATE);
        NavigationView navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.main);

        View headerView = navigationView.getHeaderView(0);
        nameTextView = headerView.findViewById(R.id.name_tv);
        progressBar = findViewById(R.id.progressBar2);

        nameTextView.setText(email);

//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavOptions navOptions = new NavOptions.Builder()
//                        .setPopUpTo(R.id.nav_host_fragment2, true) // Clear back stack up to nav host
//                        .setLaunchSingleTop(true) // Prevent re-adding if already at the top
//                        .build();
//                navController.navigate(R.id.favouritesFragment, null, navOptions);
//            }
//        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.home_24dp_e8eaed_fill0_wght400_grad0_opsz24);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
            //NavigationUI.setupWithNavController(navigationView, navController);
            // Set up navigation item selected listener
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.randomMeal) {
                        // Handle Home action
                        navController.navigate(R.id.randomMeal); // Navigate to home fragment
                    }
                    else if(id ==R.id.nameSearchFragment)
                    {
                        navController.navigate(R.id.nameSearchFragment);
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
                        navController.navigate(R.id.ingredientSearchFragment);
                    }
                    else if(id ==R.id.mealPlannerFragment)
                    {
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);
                        if(loggedIn) {
                            NavOptions navOptions = new NavOptions.Builder()
                                    .setPopUpTo(R.id.nav_host_fragment2, true)
                                    .setLaunchSingleTop(true) // Prevent re-adding if already at the top
                                    .build();
                            navController.navigate(R.id.mealPlannerFragment,null, navOptions);
                        }
                        else
                        {
                            LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                            bottomSheet.show(getSupportFragmentManager(), "LoginBottomSheetFragment");
                        }
                    }
                    else if(id == R.id.favouritesFragment)
                    {
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);
                        if(loggedIn)
                        {
                            NavOptions navOptions = new NavOptions.Builder()
                                    .setPopUpTo(R.id.nav_host_fragment2, true)
                                    .setLaunchSingleTop(true) // Prevent re-adding if already at the top
                                    .build();
                            navController.navigate(R.id.favouritesFragment,null, navOptions);
                        }
                        else
                        {
                            LoginBottomSheetFragment bottomSheet = new LoginBottomSheetFragment();
                            bottomSheet.show(getSupportFragmentManager(), "LoginBottomSheetFragment");
                        }
                    }
                    else if(id ==R.id.signout)
                    {
                        handleSignOut();
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
        SharedPreferences.Editor editor = prefs.edit();
        if(!email.equals("Guest"))
        {
            saveToFirebase();
        }
        progressBar.setVisibility(VISIBLE);
        editor.remove("password");
        editor.remove("email");
        editor.remove("loggedIn");
        editor.apply();
        //FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();

    }
    private void saveToFirebase() {
        MealDao mealDao = FavoriteMealDatabase.getInstance(getBaseContext()).favoriteMealDao();
        mealDao.getAllMeals().observe(HomeActivity.this, meals -> {
            if (meals != null) {
                // Convert LiveData<List<MealEntity>> to List<MealEntity>
                List<MealEntity> mealList = new ArrayList<>(meals);
                dataRepository.saveToFirebase(mealList, email,"Favourites");
            }
        });

        presenter.getMondayMeals().observe(this, mondays -> {
            if (mondays != null) {
                List<MealEntity> mondayMeals = new ArrayList<>();
                for (Monday monday : mondays) {
                    mondayMeals.add(new MealEntity(monday.getMealId(), monday.getMealName(), monday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(mondayMeals,email,"Monday");
            }
        });

        presenter.getTuesdayMeals().observe(this, tuesdays -> {
            if (tuesdays != null) {
                List<MealEntity> tuesdayMeals = new ArrayList<>();
                for (Tuesday tuesday : tuesdays) {
                    tuesdayMeals.add(new MealEntity(tuesday.getMealId(), tuesday.getMealName(), tuesday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(tuesdayMeals,email,"Tuesday");
            }
        });

        presenter.getWednesdayMeals().observe(this, wednesdays -> {
            if (wednesdays != null) {
                List<MealEntity> wednesdayMeals = new ArrayList<>();
                for (Wednesday wednesday : wednesdays) {
                    wednesdayMeals.add(new MealEntity(wednesday.getMealId(), wednesday.getMealName(), wednesday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(wednesdayMeals,email,"Wednesday");
            }
        });

        presenter.getThursdayMeals().observe(this, thursdays -> {
            if (thursdays != null) {
                List<MealEntity> thursdayMeals = new ArrayList<>();
                for (Thursday thursday : thursdays) {
                    thursdayMeals.add(new MealEntity(thursday.getMealId(), thursday.getMealName(), thursday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(thursdayMeals,email,"Thursday");
            }
        });

        presenter.getFridayMeals().observe(this, fridays -> {
            if (fridays != null) {
                List<MealEntity> fridayMeals = new ArrayList<>();
                for (Friday friday : fridays) {
                    fridayMeals.add(new MealEntity(friday.getMealId(), friday.getMealName(), friday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(fridayMeals,email,"Friday");
            }
        });

        presenter.getSaturdayMeals().observe(this, saturdays -> {
            if (saturdays != null) {
                List<MealEntity> saturdayMeals = new ArrayList<>();
                for (Saturday saturday : saturdays) {
                    saturdayMeals.add(new MealEntity(saturday.getMealId(), saturday.getMealName(), saturday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(saturdayMeals,email,"Saturday");
            }
        });

        presenter.getSundayMeals().observe(this, sundays -> {
            if (sundays != null) {
                List<MealEntity> sundayMeals = new ArrayList<>();
                for (Sunday sunday : sundays) {
                    sundayMeals.add(new MealEntity(sunday.getMealId(), sunday.getMealName(), sunday.getStrMealThumb()));
                }
                dataRepository.saveToFirebase(sundayMeals,email,"Sunday");
            }
        });
    }
    public void loadMealsFromfirebase()
    {
        dataRepository.loadFromFirebase(email, "Favourites", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    presenter.updateMeals(meals);
                }
                else
                {
                    presenter.updateMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Monday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Monday> mondayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Monday monday = new Monday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        mondayMeals.add(monday);
                    }
                    presenter.updateMondayMeals(mondayMeals);
                } else {
                    presenter.updateMondayMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Tuesday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Tuesday> tuesdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Tuesday tuesday = new Tuesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        tuesdayMeals.add(tuesday);
                    }
                    presenter.updateTuesdayMeals(tuesdayMeals);
                } else {
                    presenter.updateTuesdayMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Wednesday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Wednesday> wednesdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Wednesday wednesday = new Wednesday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        wednesdayMeals.add(wednesday);
                    }
                    presenter.updateWednesdayMeals(wednesdayMeals);
                } else {
                    presenter.updateWednesdayMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Thursday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Thursday> thursdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Thursday thursday = new Thursday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        thursdayMeals.add(thursday);
                    }
                    presenter.updateThursdayMeals(thursdayMeals);
                } else {
                    presenter.updateThursdayMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Friday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Friday> fridayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Friday friday = new Friday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        fridayMeals.add(friday);
                    }
                    presenter.updateFridayMeals(fridayMeals);
                } else {
                    presenter.updateFridayMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Saturday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Saturday> saturdayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Saturday saturday = new Saturday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        saturdayMeals.add(saturday);
                    }
                    presenter.updateSaturdayMeals(saturdayMeals);
                } else {
                    presenter.updateSaturdayMeals(new ArrayList<>());
                }
            }
        });
        dataRepository.loadFromFirebase(email, "Sunday", new OnMealsLoadedListener() {
            @Override
            public void onMealsLoaded(List<MealEntity> meals) {
                if (meals != null && !meals.isEmpty()) {
                    List<Sunday> sundayMeals = new ArrayList<>();
                    for (MealEntity meal : meals) {
                        Sunday sunday = new Sunday(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
                        sundayMeals.add(sunday);
                    }
                    presenter.updateSundayMeals(sundayMeals);
                } else {
                    presenter.updateSundayMeals(new ArrayList<>());
                }
            }
        });
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
