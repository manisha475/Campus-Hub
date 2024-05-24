package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.example.p2.utilities.FirebaseUtilities;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailEditText,passwordEditText;
    Button login;
    FirebaseUtilities firebaseUtilities;

    TextView registerhere;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerhere = findViewById(R.id.registerhere);
        login = findViewById(R.id.login);


        firebaseUtilities = new FirebaseUtilities(this);
        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.length() == 0 || !email.contains("@")){
                    emailEditText.setError("Enter valid Email");
                    return;
                }

                if (password.length() < 6){
                    passwordEditText.setError("Password should be 6 characters");
                    return;
                }

                firebaseUtilities.login(email,password);

            }
        });
    }
}