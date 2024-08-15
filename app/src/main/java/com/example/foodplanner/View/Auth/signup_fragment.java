package com.example.foodplanner.View.Auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class signup_fragment extends Fragment {

    Button signUp;
    Button logIn;
    EditText username;
    EditText password;
    EditText confirmPassword;
    EditText email;
    ImageView facebook;

    CallbackManager mCallbackManager;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with the action to navigate to another fragment
                requireActivity().finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);

        if (loggedIn) {
            Toast.makeText(getActivity(), "Already Logged in", Toast.LENGTH_SHORT).show();
            //Navigation.findNavController(requireView()).navigate(R.id.action_signup_fragment_to_mainFragment);
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

        signUp = view.findViewById(R.id.signUpButton);
        logIn = view.findViewById(R.id.loginButton);
        facebook = view.findViewById(R.id.imageId);
        progressBar = view.findViewById(R.id.progressBar);
        email = view.findViewById(R.id.email);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        username = view.findViewById(R.id.emailID);
        password = view.findViewById(R.id.password);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
//                LoginManager.getInstance().logInWithReadPermissions(signup_fragment.this, Arrays.asList("email", "public_profile"));
//                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), "Facebook login canceled", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), "Facebook login failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signup_fragment_to_loginFragment);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String confirmPasswordText = confirmPassword.getText().toString().trim();

                if (usernameText.isEmpty()) {
                    username.setError("Username is required");
                    return;
                }
                if (emailText.isEmpty()) {
                    email.setError("Email is required");
                    return;
                }
                if (passwordText.isEmpty()) {
                    password.setError("Password is required");
                    return;
                }
                if (confirmPasswordText.isEmpty()) {
                    confirmPassword.setError("Confirm Password is required");
                    return;
                }

                if (!EMAIL_PATTERN.matcher(emailText).matches()) {
                    email.setError("Invalid email format");
                    Toast.makeText(getActivity(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PASSWORD_PATTERN.matcher(passwordText).matches()) {
                    password.setError("Password must be at least 6 characters long, contain at least one letter, one number, and one special character");
                    Toast.makeText(getActivity(), "Password must be at least 6 characters long, contain at least one letter, one number, and one special character", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!passwordText.equals(confirmPasswordText)) {
                    confirmPassword.setError("Passwords do not match");
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(v.VISIBLE);

                Person person = new Person();
                person.username = usernameText;
                person.password = passwordText;
                person.email = emailText;


                mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("person_key", person);
                                    Toast.makeText(getContext(), "Sign up successful!", Toast.LENGTH_SHORT).show(); // Use getContext()
                                    Navigation.findNavController(v).navigate(R.id.action_signup_fragment_to_loginFragment);
                                    progressBar.setVisibility(v.GONE);

                                    // Handle successful sign up (e.g., navigate to another fragment or activity)
                                } else {
                                    Toast.makeText(getContext(), "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show(); // Use getContext()
                                }
                            }
                        });


            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Facebook sign-up successful!", Toast.LENGTH_SHORT).show();
                            // Save login state
                            SharedPreferences.Editor editor = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
                            editor.putBoolean(KEY_LOGGED_IN, true);
                            editor.apply();
                            //Navigation.findNavController(requireView()).navigate(R.id.action_signup_fragment_to_mainFragment);
                        } else {
                            Toast.makeText(getContext(), "Facebook sign-up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

