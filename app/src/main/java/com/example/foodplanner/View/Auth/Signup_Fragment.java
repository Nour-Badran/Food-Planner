package com.example.foodplanner.View.Auth;

import android.content.Intent;
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

import com.example.foodplanner.Model.AuthModel.AuthModel;
import com.example.foodplanner.Presenter.AuthPresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.Activities.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

public class Signup_Fragment extends Fragment implements AuthView {

    private Button guestButton, signUp, goToLogin;
    private EditText username, email, password, confirmPassword;
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private AuthPresenter presenter;

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    try {
                        GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class);
                        if (account != null) {
                            presenter.signInWithGoogle(account);
                        }
                    } catch (ApiException e) {
                        showToast("Google sign-in failed: " + e.getMessage());
                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenter(this, new AuthModel(requireContext()));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.username);
        signUp = view.findViewById(R.id.signUpButton);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        progressBar = view.findViewById(R.id.progressBar);
        goToLogin = view.findViewById(R.id.loginButton);
        guestButton = view.findViewById(R.id.guestButton);

        ImageView googleSignIn = view.findViewById(R.id.google);
        googleSignIn.setOnClickListener(v -> googleSignIn());

        guestButton.setOnClickListener(v -> {
            showLoading();
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
            hideLoading();
        });
        goToLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signup_fragment_to_loginFragment);
        });

        signUp.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String emaill = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();
            presenter.signUp(user, emaill, pass, confirmPass);
        });
    }

    private void googleSignIn() {
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(intent);
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void setEmailError(String error) {
        email.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        password.setError(error);
    }
}




