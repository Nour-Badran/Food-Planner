package com.example.foodplanner.View.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Auth.NewMainActivity;
import com.example.foodplanner.View.Menu.HomeActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        LottieAnimationView animationView = findViewById(R.id.splashAnimation);
        animationView.setAnimation(R.raw.comp_1);
        animationView.playAnimation();

        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

            if (loggedIn) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, NewMainActivity.class));
            }
            finish();
        }, 3000); // Delay for splash screen
    }
}