package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.example.p2.utilities.FirebaseUtilities;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText nameEditText, registrationNumberEditText, yearEditText, branchEditText, emailEditText, passwordEditText;
    Button register;
    Spinner branchSpinner;
    FirebaseUtilities firebaseUtilities;

    TextView loginHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.nameEditText);
        registrationNumberEditText = findViewById(R.id.registrationNumberEditText);
        yearEditText = findViewById(R.id.yearEditText);
        branchSpinner = findViewById(R.id.branchSpinner);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        register = findViewById(R.id.register);
        loginHere = findViewById(R.id.loginHere);

        firebaseUtilities = new FirebaseUtilities(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branches_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(adapter);

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String registrationNumber = registrationNumberEditText.getText().toString();
                String year = yearEditText.getText().toString();
                String branch = branchSpinner.getSelectedItem().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    nameEditText.setError("Enter your name");
                    return;
                }

                if (TextUtils.isEmpty(registrationNumber)) {
                    registrationNumberEditText.setError("Enter your registration number");
                    return;
                }

                if (TextUtils.isEmpty(year)) {
                    yearEditText.setError("Enter your year");
                    return;
                }

                if (TextUtils.isEmpty(branch)) {
                    branchEditText.setError("Enter your branch");
                    return;
                }

                if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Enter valid Email");
                    return;
                }

                if (password.length() < 6) {
                    passwordEditText.setError("Password should be at least 6 characters");
                    return;
                }

                firebaseUtilities.register(email, password, name, registrationNumber, year, branch);
            }
        });
    }
}
