package com.example.utf_pbp_f_4.ui.auth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utf_pbp_f_4.database.DatabaseClient;
import com.example.utf_pbp_f_4.MainActivity;
import com.example.utf_pbp_f_4.model.User;
import com.example.utf_pbp_f_4.preferences.UserPreferences;
import com.example.utf_pbp_f_4.R;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private MaterialButton btnClear, btnLogin, btnRegister;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userPreferences = new UserPreferences(LoginActivity.this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnClear = findViewById(R.id.btnClear);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        checkLogin();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsername.setText("");
                etPassword.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    attemptLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
                }
            }
        });

    }


    private void attemptLogin(String username, String password){

        class AttemptLogin extends AsyncTask<Void, Void, User> {
            @Override
            protected User doInBackground(Void... voids) {

                User user = DatabaseClient.getInstance(LoginActivity.this)
                        .getDatabase()
                        .userDao()
                        .attemptLogin(username,password);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if(user == null){
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    userPreferences.setUser(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
                }
                checkLogin();

            }

        }

        AttemptLogin attemptLogin = new AttemptLogin();
        attemptLogin.execute();
    }

    private boolean validateForm(){
        if(etUsername.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(LoginActivity.this,"Username Atau Password Kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLogin(){
        if(userPreferences.checkLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

}
