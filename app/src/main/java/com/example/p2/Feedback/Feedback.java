package com.example.p2.Feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.p2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.p2.R;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {

    private EditText feedbackEditText;
    private String feedbackValue = ""; // Variable to store feedback value
    private String feedbackCategory = ""; // Variable to store feedback category
    private String userFeedback = ""; // Variable to store user feedback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize views
        feedbackEditText = findViewById(R.id.feedbox);
        Button sendButton = findViewById(R.id.submitButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackToFirebase();
            }
        });
        ImageView i1 = findViewById(R.id.i1);
        ImageView i2 = findViewById(R.id.i2);
        ImageView i3 = findViewById(R.id.i3);
        ImageView i4 = findViewById(R.id.i4);
        ImageView i5 = findViewById(R.id.i5);
        Button b1 = findViewById(R.id.b1);
        Button b2 = findViewById(R.id.b2);
        Button b3 = findViewById(R.id.b3);

        // Set click listener for all ImageViews
        View.OnClickListener imageViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine which ImageView was clicked and store feedback value
                if (v.getId() == R.id.i1) {
                    feedbackValue = "Excellent";
                } else if (v.getId() == R.id.i2) {
                    feedbackValue = "Good";
                } else if (v.getId() == R.id.i3) {
                    feedbackValue = "Fair";
                } else if (v.getId() == R.id.i4) {
                    feedbackValue = "Poor";
                } else if (v.getId() == R.id.i5) {
                    feedbackValue = "Bad";
                }
            }
        };

        // Set click listener for all ImageViews
        i1.setOnClickListener(imageViewClickListener);
        i2.setOnClickListener(imageViewClickListener);
        i3.setOnClickListener(imageViewClickListener);
        i4.setOnClickListener(imageViewClickListener);
        i5.setOnClickListener(imageViewClickListener);

        // Set click listener for all buttons
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine which button was clicked and store feedback category
                if (v.getId() == R.id.b1) {
                    feedbackCategory = "Suggestion";
                } else if (v.getId() == R.id.b2) {
                    feedbackCategory = "Something is not quite right";
                } else if (v.getId() == R.id.b3) {
                    feedbackCategory = "Compliment";
                }

                // Get user feedback from the EditText
                userFeedback = feedbackEditText.getText().toString();
            }
        };

        // Set click listener for all buttons
        b1.setOnClickListener(buttonClickListener);
        b2.setOnClickListener(buttonClickListener);
        b3.setOnClickListener(buttonClickListener);
    }


    private void sendFeedbackToFirebase() {
        // Retrieve Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail(); // Retrieve user email
            String userId = currentUser.getUid();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String registrationNumber = dataSnapshot.child("registrationNumber").getValue().toString();
                        uploadFeedbackToFirebase(userEmail, registrationNumber);
                    } else {
                        // Handle case where user data is not found
                        Toast.makeText(Feedback.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error
                    Toast.makeText(Feedback.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadFeedbackToFirebase(String userEmail, String registrationNumber) {
        // Generate a unique ID for the feedback entry
        String feedbackId = FirebaseDatabase.getInstance().getReference("Feedback").push().getKey();

        // Get user feedback from the EditText
        userFeedback = feedbackEditText.getText().toString();

        // Store data into Firebase Realtime Database
        DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("feedback").child(feedbackId);
        feedbackRef.child("feedbackValue").setValue(feedbackValue);
        feedbackRef.child("feedbackCategory").setValue(feedbackCategory);
        feedbackRef.child("userFeedback").setValue(userFeedback);
        feedbackRef.child("userEmail").setValue(userEmail);
        feedbackRef.child("userRegNo").setValue(registrationNumber)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Show a success message using a Toast
                            Toast.makeText(Feedback.this, "Feedback Recorded successfully", Toast.LENGTH_SHORT).show();
                            // Clear the feedback text box
                            feedbackEditText.setText("");
                        } else {
                            // Show an error message if upload fails
                            Toast.makeText(Feedback.this, "Failed to upload feedback", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
