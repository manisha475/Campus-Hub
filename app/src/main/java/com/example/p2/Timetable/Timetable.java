package com.example.p2.Timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p2.R;
import com.example.p2.utilities.FirebaseUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Timetable extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimetableAdapter adapter;
    private FirebaseUtilities firebaseUtilities;
    private String currentUserBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        firebaseUtilities = new FirebaseUtilities(this);

        // Fetch current user's branch from Firebase
        fetchUserBranch();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Back Button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Go back to the dashboard activity
            }
        });
    }

    // Method to fetch current user's branch from Firebase
    // Method to fetch current user's branch from Firebase
    private void fetchUserBranch() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Ensure that 'branch' key exists before trying to retrieve its value
                    if (dataSnapshot.hasChild("branch")) {
                        currentUserBranch = dataSnapshot.child("branch").getValue(String.class);
                        if (currentUserBranch != null) {
                            // Set the TextView with id "head" to the current user's branch
                            TextView headTextView = findViewById(R.id.head);
                            headTextView.setText(currentUserBranch);

                            // Set up button click listeners
                            setUpButtonClickListeners();
                        }
                    } else {
                        // Handle case where 'branch' key doesn't exist for the user
                        Toast.makeText(Timetable.this, "Branch information not found for the user", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(Timetable.this, "Failed to fetch user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to set up button click listeners
    private void setUpButtonClickListeners() {
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);

        // Set onClick listeners for buttons
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimetable("monday");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimetable("tuesday");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimetable("wednesday");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimetable("thursday");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimetable("friday");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimetable("saturday");
            }
        });
    }

    // Method to display timetable for the selected day
    private void displayTimetable(String day) {
        firebaseUtilities.getTimetableForDayAndBranch(currentUserBranch, day, new FirebaseUtilities.TimetableCallback() {
            @Override
            public void onTimetableLoaded(List<item_timetable> timetable) {
                adapter = new TimetableAdapter(timetable);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
