package com.example.p2.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Lostdetails extends AppCompatActivity {

    private TextView t1, t2, t3, s1;
    private Button bottomButton;
    private ProgressBar p1;
    private ImageView capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostdetails);

        // Initialize views
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        s1 = findViewById(R.id.s1);
        bottomButton = findViewById(R.id.bottomButton);
        p1 = findViewById(R.id.p1);
        capture = findViewById(R.id.capture);

        // Retrieve the item ID passed from lostadapter
        String itemId = getIntent().getStringExtra("itemId");

        // Retrieve item details from Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("items").child(itemId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get item details
                    String itemName = dataSnapshot.child("itemName").getValue(String.class);
                    String place = dataSnapshot.child("place").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String location = dataSnapshot.child("location").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    // Set item details to TextViews
                    t1.setText(itemName);
                    t2.setText("Found at: "+place);
                    t3.setText(description);
                    s1.setText("Collect From: " + location);

                    // Load item image using Picasso
                    Picasso.get().load(imageUrl).into(capture, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Image loaded successfully
                            // Hide progress bar
                            p1.setVisibility(View.GONE);
                            Toast.makeText(Lostdetails.this, "Item Details Retrived", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception e) {
                            // Image failed to load
                            Toast.makeText(Lostdetails.this, "Failed to load item image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            // Hide progress bar
                            p1.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(Lostdetails.this, "Item details not found", Toast.LENGTH_SHORT).show();
                    // Hide progress bar
                    p1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Lostdetails.this, "Failed to retrieve item details", Toast.LENGTH_SHORT).show();
                // Hide progress bar
                p1.setVisibility(View.GONE);
            }
        });

        // Button click listener
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back
                finish();
            }
        });
    }
}
