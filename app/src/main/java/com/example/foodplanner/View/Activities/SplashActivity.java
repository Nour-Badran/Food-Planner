package com.example.foodplanner.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.Presenter.LoggedInPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;

public class SplashActivity extends AppCompatActivity {
    LoggedInPresenter presenter;
    boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new LoggedInPresenter(new AuthModel(getBaseContext()));
        loggedIn = presenter.isLoggedIn();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        LottieAnimationView animationView = findViewById(R.id.splashAnimation);
        animationView.setAnimation(R.raw.comp_1);
        animationView.playAnimation();

        new Handler().postDelayed(() -> {
            if (loggedIn) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, AuthActivity.class));
            }
            finish();
        }, 3000);
    }
}