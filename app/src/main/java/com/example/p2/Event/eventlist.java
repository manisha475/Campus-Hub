package com.example.p2.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class eventlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);

        // Retrieve the selected date from the intent
        String selectedDate = getIntent().getStringExtra("MESSAGE");

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsRef = database.getReference("Events");

        // Retrieve event names from Firebase based on the selected date
        eventsRef.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Initialize ArrayList to store event names
                ArrayList<String> eventNames = new ArrayList<>();
                final ArrayList<String> eventIDs = new ArrayList<>(); // Store event IDs

                // Iterate through the dataSnapshot to retrieve event names and IDs
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String eventID = eventSnapshot.getKey();
                    String eventName = eventSnapshot.child("name").getValue(String.class);
                    eventNames.add(eventName);
                    eventIDs.add(eventID);
                }

                // Create the adapter and set it to the list view
                EventAdapter adapter = new EventAdapter(eventlist.this, eventNames);
                ListView eventListView = findViewById(R.id.eventListView);
                eventListView.setAdapter(adapter);

                // Set click listener to the list view
                eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected event ID
                        String selectedEventID = eventIDs.get(position);

                        // Pass the selected date and event ID to the event description page
                        Intent intent = new Intent(eventlist.this, eventdescription.class);
                        intent.putExtra("SELECTED_DATE", selectedDate);
                        intent.putExtra("SELECTED_EVENT_ID", selectedEventID);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}

