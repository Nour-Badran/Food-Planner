package com.example.foodplanner.View.Auth;

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
import android.widget.Toast;
import com.example.foodplanner.R;

public class LoginFragment extends Fragment {

    Button signUp;
    Button logIn;
    EditText username;
    EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        signUp = view.findViewById(R.id.signupButton);
        logIn = view.findViewById(R.id.loginButton);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate login credentials
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    username.setError("Username is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required");
                    return;
                }


                boolean isAuthenticated = authenticateUser(user, pass);

                if (isAuthenticated) {
                    // Navigate to another fragment or activity on successful login
                    //Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
                } else {
                    // Show error message on failed login
                    Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signup_fragment);
            }
        });
    }

    private boolean authenticateUser(String username, String password) {
        // Replace this with your actual authentication logic
        // For example, make a network request to your server for authentication
        // Return true if authentication is successful, otherwise false
        return "testuser".equals(username) && "password123".equals(password);
    }
}
