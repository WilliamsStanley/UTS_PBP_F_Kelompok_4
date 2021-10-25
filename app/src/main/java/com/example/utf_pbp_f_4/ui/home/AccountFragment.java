package com.example.utf_pbp_f_4.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.utf_pbp_f_4.R;
import com.example.utf_pbp_f_4.model.User;
import com.example.utf_pbp_f_4.preferences.UserPreferences;
import com.example.utf_pbp_f_4.ui.auth.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class AccountFragment extends Fragment {
    private TextView tvID, tvUsername, tvEmail;
    private MaterialButton btnLogout;
    private User user;
    private UserPreferences userPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_account, container, false);
        userPreferences = new UserPreferences(getContext());
        tvID = root.findViewById(R.id.tvID);
        tvUsername = root.findViewById(R.id.tvUsername);
        tvEmail = root.findViewById(R.id.tvEmail);
        btnLogout = root.findViewById(R.id.btnLogout);

        user = userPreferences.getUserLogin();

        checkLogin();

        tvID.setText(String.valueOf(user.getId()));
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                Toast.makeText(getContext(), "Log Out", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });

        return root;
    }

    private void checkLogin(){
        if(!userPreferences.checkLogin()){
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }else {
            Toast.makeText(getContext(), "Welcome back "+user.getUsername()+" !", Toast.LENGTH_SHORT).show();
        }
    }
}
