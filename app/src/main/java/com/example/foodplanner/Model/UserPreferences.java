package com.example.foodplanner.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_LOGGED_IN = "user_logged_in";

    private SharedPreferences preferences;

    public UserPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isUserLoggedIn() {
        return preferences.getBoolean(KEY_USER_LOGGED_IN, false);
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_USER_LOGGED_IN, loggedIn);
        editor.apply();
    }
}
