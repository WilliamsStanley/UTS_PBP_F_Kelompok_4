package com.example.utf_pbp_f_4.ui.auth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utf_pbp_f_4.database.DatabaseClient;
import com.example.utf_pbp_f_4.model.User;
import com.example.utf_pbp_f_4.preferences.UserPreferences;
import com.example.utf_pbp_f_4.R;
import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etUsername, etPassword;
    private MaterialButton btnRegister, btnLogin;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userPreferences = new UserPreferences(RegisterActivity.this);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    register(etUsername.getText().toString().trim(), etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
                }
            }
        });
    }

    private void register(String username, String email ,String password){

        class RegisterUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);

                DatabaseClient.getInstance(RegisterActivity.this)
                        .getDatabase()
                        .userDao()
                        .registerUser(user);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                clearField();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }

        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();
    }

    private void clearField(){
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
    }


    private boolean validateForm(){
        if(etUsername.getText().toString().trim().isEmpty() || etEmail.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(RegisterActivity.this,"Empty field detected",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
