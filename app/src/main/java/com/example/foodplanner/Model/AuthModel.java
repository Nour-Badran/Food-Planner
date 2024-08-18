package com.example.foodplanner.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthModel {
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    public AuthModel(Context context) {
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = context.getSharedPreferences("FoodPlannerPrefs", Context.MODE_PRIVATE);
    }

    public void signInWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void createUserWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void signInWithGoogle(GoogleSignInAccount account, OnCompleteListener<AuthResult> listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(listener);
    }

    public void saveLoginState(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putBoolean("loggedIn", true);
        editor.apply();
    }

    public void clearLoginState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("loggedIn", false);
    }
}
