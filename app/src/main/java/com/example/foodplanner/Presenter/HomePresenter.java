package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.View.Menu.Interfaces.AuthView;
import com.example.foodplanner.View.Menu.Interfaces.HomeView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePresenter {
    private HomeView view;
    private AuthModel model;

    public HomePresenter(HomeView view, AuthModel model) {
        this.view = view;
        this.model = model;
    }

    public boolean isLoggedIn() {
        return model.isLoggedIn();
    }

    public String getEmail() {
        return model.getEmail();
    }

    public void signOut() {
        model.clearLoginState();
        view.showToast("Signed Out");
        FirebaseAuth.getInstance().signOut();
        view.navigateToSignUp();
    }
}
