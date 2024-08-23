package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

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

    public void saveLoginState(String email) {
        model.saveLoginState(email);
    }

    public void clearLoginState() {
        model.clearLoginState();
    }

    public String getCurrentUserId() {
        return model.getCurrentUserId();
    }

    public boolean isLoggedIn() {
        return model.isLoggedIn();
    }

    public String getEmail() {
        return model.getEmail();
    }

    public void signOut() {
        clearLoginState();
        view.showToast("Signed Out");
        FirebaseAuth.getInstance().signOut();
        view.navigateToSignUp();
    }
}
