package com.example.foodplanner.View.Auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodplanner.Model.Person;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Menu.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.regex.Pattern;

public class signup_fragment extends Fragment {

    // UI Components
    Button signUp, logIn, guestButton;
    EditText username, password, confirmPassword, email;
    ImageView googleAuth;
    ProgressBar progressBar;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;

    // Facebook Sign-In
    private CallbackManager mCallbackManager;

    // Shared Preferences
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    private static final String KEY_EMAIL = "email";

    // Patterns for Validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

    // Google Sign-In Launcher
    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        if (account != null) {
                            firebaseAuthWithGoogle(account);
                        }
                    } catch (ApiException e) {
                        Toast.makeText(getActivity(), "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Google Sign-In Options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // Facebook Callback Manager
        mCallbackManager = CallbackManager.Factory.create();

        // Check if Already Logged In
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

        if (loggedIn) {
            Toast.makeText(getActivity(), "Already Logged in", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI Components
        signUp = view.findViewById(R.id.signUpButton);
        logIn = view.findViewById(R.id.loginButton);
        progressBar = view.findViewById(R.id.progressBar);
        email = view.findViewById(R.id.email);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        username = view.findViewById(R.id.emailID);
        password = view.findViewById(R.id.password);
        guestButton = view.findViewById(R.id.guestButton);
        googleAuth = view.findViewById(R.id.google);
        //facebookAuth = view.findViewById(R.id.facebook);

        // Google Sign-In
        googleAuth.setOnClickListener(v -> googleSignIn());

        // Facebook Sign-In
        //facebookAuth.setOnClickListener(v -> facebookSignIn());

        // Log In Button
        logIn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_signup_fragment_to_loginFragment));

        // Guest Button
        guestButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Welcome Guest", Toast.LENGTH_SHORT).show();
        });

        // Sign Up Button
        signUp.setOnClickListener(v -> performSignUp());
    }

    private void googleSignIn() {
        // Sign out from any previous Google account
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            // After sign out is complete, launch the sign-in intent
            Intent intent = mGoogleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(intent);
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        saveLoginState(account.getEmail());
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Welcome " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Google sign-in failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void performSignUp() {
        progressBar.setVisibility(View.VISIBLE);
        String inputEmail = email.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();
        String inputConfirmPassword = confirmPassword.getText().toString().trim();

        if (!validateForm(inputEmail, inputPassword, inputConfirmPassword)) {
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(getActivity(), task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        saveLoginState(inputEmail); // Save email to SharedPreferences
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Sign-up failed. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Invalid email format.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(getActivity(), "Password must contain at least 6 characters, including letters, numbers, and special characters.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveLoginState(String email) {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

//    private void saveUserToDatabase(String userId) {
//        Person person = new Person(username.getText().toString().trim(), email.getText().toString().trim());
//        database.getReference("Users").child(userId).setValue(person)
//                .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "User saved to database", Toast.LENGTH_SHORT).show())
//                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to save user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
