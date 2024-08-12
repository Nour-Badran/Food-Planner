package com.example.foodplanner.View.Auth;

import android.os.Bundle;

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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.foodplanner.Model.Person;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class signup_fragment extends Fragment {

    Button signUp;
    Button logIn;
    EditText username;
    EditText password;
    EditText confirmPassword;
    EditText email;
    RadioButton male;
    RadioButton female;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUp = view.findViewById(R.id.signUpButton);
        logIn = view.findViewById(R.id.loginButton);
        progressBar = view.findViewById(R.id.progressBar);
//        checkBoxPizza = view.findViewById(R.id.checkBoxPizza);
//        checkBoxSushi = view.findViewById(R.id.checkBoxSushi);
//        checkBoxBurgers = view.findViewById(R.id.checkBoxBurgers);
        email = view.findViewById(R.id.email);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        username = view.findViewById(R.id.emailID);
        password = view.findViewById(R.id.password);
        male = view.findViewById(R.id.radioButton);
        female = view.findViewById(R.id.radioButton3);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male.isChecked()) {
                    female.setChecked(false);
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (female.isChecked()) {
                    male.setChecked(false);
                }
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

                if(!male.isChecked() && !female.isChecked())
                {
                    Toast.makeText(getActivity(), "Choose your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(v.VISIBLE);

                Person person = new Person();
                person.username = usernameText;
                person.password = passwordText;
                person.email = emailText;

                if (male.isChecked()) {
                    person.gender = "Male";
                } else if (female.isChecked()) {
                    person.gender = "Female";
                } else {
                    person.gender = "Not specified";
                }

                mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(v.GONE);
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "Sign up successful!", Toast.LENGTH_SHORT).show(); // Use getContext()
                                    // Handle successful sign up (e.g., navigate to another fragment or activity)
                                } else {
                                    Toast.makeText(getContext(), "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show(); // Use getContext()
                                }
                            }
                        });
//                List<String> favoriteMeals = new ArrayList<>();
//                if (checkBoxPizza.isChecked()) {
//                    favoriteMeals.add("Pizza");
//                }
//                if (checkBoxSushi.isChecked()) {
//                    favoriteMeals.add("Sushi");
//                }
//                if (checkBoxBurgers.isChecked()) {
//                    favoriteMeals.add("Burgers");
//                }
//                person.favoriteMeals = favoriteMeals.toArray(new String[0]);

                Bundle bundle = new Bundle();
                bundle.putSerializable("person_key", person);
                //Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_signUpFragment, bundle);
            }
        });
    }
}
