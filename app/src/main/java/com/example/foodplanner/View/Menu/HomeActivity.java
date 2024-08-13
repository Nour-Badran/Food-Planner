package com.example.foodplanner.View.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView nameTextView;
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefs = getSharedPreferences("FoodPlannerPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove("password");
//        editor.remove("email");
//        editor.remove("loggedIn");
//        editor.apply();
        NavigationView navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.main);

        View headerView = navigationView.getHeaderView(0);
        nameTextView = headerView.findViewById(R.id.name_tv);
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String email = prefs.getString("email", "Guest");
        nameTextView.setText(email);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.home_24dp_e8eaed_fill0_wght400_grad0_opsz24);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
            NavigationUI.setupWithNavController(navigationView, navController);
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.differentColor)));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // Inflate your menu
        menu.clear();
        return true;
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
        } else if (item.getItemId() == R.id.nameSearchFragment) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
            navController.navigate(R.id.nameSearchFragment, null, new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph2, true) // Clear the back stack
                    .build());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
