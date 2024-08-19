package com.example.foodplanner.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.View.Auth.AuthActivity;
import com.example.foodplanner.View.Menu.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LoginBottomSheetFragment extends BottomSheetDialogFragment {

    private Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_login_required, container, false);

        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;    }

}