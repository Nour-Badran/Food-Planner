package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.View.Auth.AuthView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthPresenter {
    private AuthView view;
    private AuthModel model;

    public AuthPresenter(AuthView view, AuthModel model) {
        this.view = view;
        this.model = model;
    }

    public void login(String email, String password) {
        if (email.isEmpty()) {
            view.setEmailError("Email is required");
            return;
        }
        if (password.isEmpty()) {
            view.setPasswordError("Password is required");
            return;
        }

        view.showLoading();
        model.signInWithEmailAndPassword(email, password, task -> {
            view.hideLoading();
            if (task.isSuccessful()) {
                model.saveLoginState(email);
                view.navigateToHome(email);
                view.showToast("Login Successful");
            } else {
                view.showToast("Login Failed");
            }
        });
    }

    public void signUp(String user, String email, String password, String confirmPassword) {
        if (user.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showToast("Please fill out all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showToast("Passwords do not match.");
            return;
        }

        view.showLoading();
        model.createUserWithEmailAndPassword(email, password, task -> {
            view.hideLoading();
            if (task.isSuccessful()) {
                model.saveLoginState(email);
                view.navigateToHome(email);
                view.showToast("Sign Up Successful");
            } else {
                view.showToast("Sign Up Failed");
            }
        });
    }

    public void signInWithGoogle(GoogleSignInAccount account) {
        model.signInWithGoogle(account, task -> {
            if (task.isSuccessful()) {
                model.saveLoginState(account.getEmail());
                view.navigateToHome(account.getEmail());
                view.showToast("Welcome " + account.getDisplayName());
            } else {
                view.showToast("Google sign-in failed.");
            }
        });
    }

    /*public void checkIfLoggedIn() {
        if (model.isLoggedIn()) {
            view.navigateToHome();
        }
    }*/
}
