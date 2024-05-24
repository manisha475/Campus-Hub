package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.p2.admin.admindasboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class adlogin extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adlogin);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.login);

        // Set OnClickListener on loginButton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve email and password from EditText fields
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if email matches the specified pattern
                if (!email.equals("adminmit@gmail.com")) {
                    Toast.makeText(adlogin.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if password matches the specified pattern
                if (!password.equals("mit2025")) {
                    Toast.makeText(adlogin.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If both email and password are correct, redirect to admin dashboard
                redirectToAdminDashboard();
            }
        });
    }

    private void redirectToAdminDashboard() {
        // Create an Intent to navigate to the admin dashboard activity
        Intent intent = new Intent(adlogin.this, admindasboard.class);
        startActivity(intent);
        // Finish the current activity to prevent the user from returning to the login screen by pressing the back button
        finish();
    }
}
