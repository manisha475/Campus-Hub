package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView nameTextView, registrationNumberTextView, yearTextView, branchTextView;
    DatabaseReference usersRef;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.nameTextView);
        registrationNumberTextView = findViewById(R.id.registrationNumberTextView);
        yearTextView = findViewById(R.id.yearTextView);
        branchTextView = findViewById(R.id.branchTextView);
        progressBar = findViewById(R.id.progressBar);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String registrationNumber = dataSnapshot.child("registrationNumber").getValue().toString();
                        String year = dataSnapshot.child("year").getValue().toString();
                        String branch = dataSnapshot.child("branch").getValue().toString();

                        nameTextView.setText("Name: " + name);
                        registrationNumberTextView.setText("Registration Number: " + registrationNumber);
                        yearTextView.setText("Year: " + year);
                        branchTextView.setText("Branch: " + branch);

                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to dashboard activity
                Intent intent = new Intent(ProfileActivity.this, dashboard.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout user and navigate to login activity
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it when pressing back from login
            }
        });
    }
}
