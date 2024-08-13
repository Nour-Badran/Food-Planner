package com.example.foodplanner.View.Auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.foodplanner.R;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

//    Button signUp;
    CallbackManager mCallbackManager;
    Button logIn;
    EditText email;
    EditText password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final String PREFS_NAME = "FoodPlannerPrefs";
    private static final String KEY_LOGGED_IN = "loggedIn";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logIn = view.findViewById(R.id.loginButton);
        email = view.findViewById(R.id.emailID);
        password = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate login credentials
                String emaill = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(emaill)) {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required");
                    return;
                }
                if (!EMAIL_PATTERN.matcher(emaill).matches()) {
                    email.setError("Invalid email format");
                    Toast.makeText(getActivity(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(v.VISIBLE);
                mAuth.signInWithEmailAndPassword(emaill,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean(KEY_LOGGED_IN, true);
                        editor.putString("email",emaill);
                        editor.putString("password",pass);
                        editor.apply();
                        progressBar.setVisibility(v.GONE);
                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(v.GONE);
                        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signup_fragment);
//            }
//        });
    }

    private boolean authenticateUser(String username, String password) {
        // Replace this with your actual authentication logic
        // For example, make a network request to your server for authentication
        // Return true if authentication is successful, otherwise false
        return "testuser".equals(username) && "password123".equals(password);
    }
}
