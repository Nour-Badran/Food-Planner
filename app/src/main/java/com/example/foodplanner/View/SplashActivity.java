package com.example.foodplanner.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Auth.NewMainActivity;

public class SplashActivity extends AppCompatActivity {
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
            startActivity(new Intent(SplashActivity.this, NewMainActivity.class));
            finish();
        }, 3000); // Delay for splash screen
    }
}