package com.example.veterinaryambulance.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinaryambulance.R;
import com.example.veterinaryambulance.data.datasources.databases.DatabaseHelper;
import com.example.veterinaryambulance.data.repositories.VeterinarianRepository;
import com.example.veterinaryambulance.domain.models.VeterinarianDTO;
import com.example.veterinaryambulance.domain.repositories.IVeterinarianRepository;
import com.example.veterinaryambulance.domain.usecases.implementation.VeterinarianUseCase;
import com.example.veterinaryambulance.domain.usecases.interfaces.IVeterinarianUseCase;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, resetButton;
    private IVeterinarianUseCase veterinarianUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDependencies();
        initializeUIElements();
        setupListeners();
    }

    private void initializeDependencies() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        IVeterinarianRepository veterinarianRepository = new VeterinarianRepository(dbHelper);
        veterinarianUseCase = new VeterinarianUseCase(veterinarianRepository);
    }

    private void initializeUIElements() {
        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        resetButton = findViewById(R.id.reset_button);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        resetButton.setOnClickListener(v -> resetFields());
    }

    private void handleLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showToast("Please enter both username and password");
            return;
        }

        try {
            VeterinarianDTO veterinarianDTO = veterinarianUseCase.login(username, password);

            if (veterinarianDTO != null) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("veterinarianDTO", veterinarianDTO);
                startActivity(intent);
                finish();
            } else {
                showToast("Invalid username or password");
            }
        } catch (Exception e) {
            showToast("An error occurred during login");
        }
    }

    private void resetFields() {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}