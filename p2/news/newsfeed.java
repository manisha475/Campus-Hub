// newsfeed.java

package com.example.p2.news;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class newsfeed extends AppCompatActivity {

    private ImageView newsImageView;
    private TextView headlineTextView;
    private TextView contentTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        newsImageView = findViewById(R.id.newsimg);
        headlineTextView = findViewById(R.id.headline);
        contentTextView = findViewById(R.id.feed);
        progressBar = findViewById(R.id.progressBar);

        // Retrieve firebaseId and category passed from NewsActivity
        String firebaseId = getIntent().getStringExtra("firebaseId");
        String category = getIntent().getStringExtra("category");

        // Retrieve news details from Firebase using the firebaseId
        DatabaseReference newsRef = FirebaseDatabase.getInstance().getReference("news/" + category).child(firebaseId);
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String headline = dataSnapshot.child("headline").getValue(String.class);
                    String content = dataSnapshot.child("content").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    // Set the headline and content in TextViews
                    headlineTextView.setText(headline);
                    contentTextView.setText(content);

                    // Load image using Picasso with progress indication
                    progressBar.setVisibility(View.VISIBLE);
                    Picasso.get().load(imageUrl).into(newsImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            // Handle error
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
