package com.example.p2.Event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class eventdescription extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdescription);

        // Receive intent and retrieve selected date and event ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String selectedDate = extras.getString("SELECTED_DATE");
            String selectedEventID = extras.getString("SELECTED_EVENT_ID");

            TextView eventIDTextView = findViewById(R.id.eventdes);
            TextView eventLinkTextView = findViewById(R.id.eventlink);
            ImageView eventImageView = findViewById(R.id.eventimg);


            DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events")
                    .child(selectedDate).child(selectedEventID);
            eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("name").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String link = dataSnapshot.child("link").getValue(String.class);

                    // Set the description and link to their respective TextViews
                    eventIDTextView.setText(description);
                    eventLinkTextView.setText(link);

                    // Make the link clickable
                    eventLinkTextView.setOnClickListener(view -> {
                        Uri uri = Uri.parse(link);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    });
                    String nam = name.toLowerCase();
                    int resId = getResources().getIdentifier(nam, "drawable", getPackageName());
                    eventImageView.setImageResource(resId);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }


    }
}
