// NewsActivity.java

package com.example.p2.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity implements newsadapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private newsadapter newsAdapter;
    private List<newsitem> newsItemList;
    private ProgressBar progressBar;
    private String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsItemList = new ArrayList<>();
        newsAdapter = new newsadapter(newsItemList, this, this);
        recyclerView.setAdapter(newsAdapter);

        Button placementsButton = findViewById(R.id.placements);
        Button alumniButton = findViewById(R.id.alumni);
        Button clubsButton = findViewById(R.id.clubs);
        Button innovationsButton = findViewById(R.id.innovations);

        clubsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNews("clubs");
                cat = "clubs";
            }
        });

        innovationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNews("Innovations");
                cat = "Innovations";
            }
        });

        alumniButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNews("alumni");
                cat = "alumni";
            }
        });

        placementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNews("placements");
                cat = "placements";
            }
        });
    }

    private void displayNews(String category) {
        progressBar.setVisibility(View.VISIBLE);
        newsItemList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newsRef = database.getReference("news/" + category);
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int itemCount = (int) dataSnapshot.getChildrenCount(); // Get the total number of items
                int loadedCount = 0; // Counter for loaded items
                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    String firebaseId = newsSnapshot.getKey();
                    String headline = newsSnapshot.child("headline").getValue(String.class);
                    String content = newsSnapshot.child("content").getValue(String.class);
                    String date = newsSnapshot.child("date").getValue(String.class);
                    String imageUrl = newsSnapshot.child("imageUrl").getValue(String.class);
                    String post = "Posted on: " + date;
                    newsItemList.add(new newsitem(firebaseId, headline, post, imageUrl));
                    loadedCount++; // Increment loaded count
                    // Check if all items are loaded
                    if (loadedCount == itemCount) {
                        newsAdapter.notifyDataSetChanged(); // Notify adapter once all items are loaded
                        progressBar.setVisibility(View.GONE); // Hide progress bar
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(String firebaseId) {
        Intent intent = new Intent(this, newsfeed.class);
        intent.putExtra("firebaseId", firebaseId);
        intent.putExtra("category", cat);
        startActivity(intent);
    }
}
