package com.example.foodplanner.View;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.View.Activities.AuthActivity;
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
        View view = inflater.inflate(R.layout.dialog_login_required, container, false);

        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}