package com.example.foodplanner.View.Auth;

public interface AuthView {
    void showLoading();
    void hideLoading();
    void showToast(String message);
    void navigateToHome();
    void setEmailError(String error);
    void setPasswordError(String error);
}
