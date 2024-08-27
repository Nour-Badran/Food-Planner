package com.example.foodplanner.Presenter;

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.View.Menu.Interfaces.HomeView;

public class LoggedInPresenter {
    private AuthModel model;

    public LoggedInPresenter(AuthModel model) {
        this.model = model;
    }

    public boolean isLoggedIn() {
        return model.isLoggedIn();
    }
}
