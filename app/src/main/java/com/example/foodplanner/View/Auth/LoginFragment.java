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
import com.example.foodplanner.View.Menu.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

public class LoginFragment extends Fragment implements AuthView {

    private Button logIn, guestButton;
    private EditText email, password;
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
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_signup_fragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logIn = view.findViewById(R.id.loginButton);
        email = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
        guestButton = view.findViewById(R.id.guestLoginButton);

        ImageView googleSignIn = view.findViewById(R.id.google);
        googleSignIn.setOnClickListener(v -> googleSignIn());

        guestButton.setOnClickListener(v -> {
            showLoading();
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
            hideLoading();
            showToast("Welcome Guest");
        });

        logIn.setOnClickListener(v -> {
            String emaill = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            presenter.login(emaill, pass);
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