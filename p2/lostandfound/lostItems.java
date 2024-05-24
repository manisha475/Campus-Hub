package com.example.p2.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.p2.R;
import com.example.p2.lostandfound.lost_item;
import com.example.p2.lostandfound.lostadapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class lostItems extends AppCompatActivity {

    private RecyclerView recyclerView;
    private lostadapter lostAdapter;
    private List<lost_item> lostItemList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_items);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lostItemList = new ArrayList<>();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Retrieve data from Firebase
        database.getReference("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String itemId = itemSnapshot.getKey(); // Get the item ID
                    String itemName = itemSnapshot.child("itemName").getValue(String.class);
                    String date = itemSnapshot.child("date").getValue(String.class);
                    String imageUrl = itemSnapshot.child("imageUrl").getValue(String.class); // Retrieve image URL
                    String itemDescription = "Posted on: " + date;
                    lostItemList.add(new lost_item(itemId, itemName, itemDescription, imageUrl)); // Pass item ID to lost_item constructor
                }
                // Set up RecyclerView Adapter after fetching data
                lostAdapter = new lostadapter(lostItemList);
                recyclerView.setAdapter(lostAdapter);
                // Hide progress bar
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
}
