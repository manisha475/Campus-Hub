package com.example.p2.utilities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.p2.LoginActivity;
import com.example.p2.dashboard;
import com.example.p2.Timetable.item_timetable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtilities {

    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;
    private DatabaseReference timetableRef;

    public FirebaseUtilities(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        timetableRef = FirebaseDatabase.getInstance().getReference("timetable");
    }

    public void register(final String email, final String password, final String name, final String registrationNumber, final String year, final String branch) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful, save user data in Firebase Realtime Database
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserRef = usersRef.child(userId); // Use userId as the key
                            currentUserRef.child("name").setValue(name);
                            currentUserRef.child("registrationNumber").setValue(registrationNumber);
                            currentUserRef.child("year").setValue(year);
                            currentUserRef.child("branch").setValue(branch);

                            Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        } else {
                            // Registration failed
                            Toast.makeText(context, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Login successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, dashboard.class);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Method to fetch timetable data for a specific day and branch
    public void getTimetableForDayAndBranch(String branch, String day, final TimetableCallback callback) {
        DatabaseReference branchTimetableRef = timetableRef.child(branch).child(day);
        branchTimetableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<item_timetable> timetable = new ArrayList<>();
                for (DataSnapshot sessionSnapshot : dataSnapshot.getChildren()) {
                    String subject = sessionSnapshot.child("subject").getValue(String.class);
                    String time = sessionSnapshot.child("time").getValue(String.class);
                    if (subject != null && time != null) {
                        timetable.add(new item_timetable(subject, time));
                    }
                }
                callback.onTimetableLoaded(timetable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    // Interface for callback
    public interface TimetableCallback {
        void onTimetableLoaded(List<item_timetable> timetable);
    }
}
