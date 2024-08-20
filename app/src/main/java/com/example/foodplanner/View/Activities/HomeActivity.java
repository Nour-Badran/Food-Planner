package com.example.foodplanner.View.Activities;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
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

import com.example.foodplanner.R;
import com.example.foodplanner.View.Auth.AuthActivity;
import com.example.foodplanner.View.LoginBottomSheetFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView nameTextView;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    SharedPreferences prefs;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //floatingActionButton = findViewById(R.id.floatingActionButton);
        prefs = getSharedPreferences("FoodPlannerPrefs", MODE_PRIVATE);
        NavigationView navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.main);

        View headerView = navigationView.getHeaderView(0);
        nameTextView = headerView.findViewById(R.id.name_tv);
        progressBar = findViewById(R.id.progressBar2);
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String email = prefs.getString("email", "Guest");
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
                        progressBar.setVisibility(VISIBLE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove("password");
                        editor.remove("email");
                        editor.remove("loggedIn");
                        editor.apply();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
                        startActivity(intent);
                        finish(); // Finish HomeActivity if you don't want the user to return to it
                        //progressBar.setVisibility(v.GONE);
                        //finish();
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


}
