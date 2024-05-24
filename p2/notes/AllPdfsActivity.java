package com.example.p2.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPdfsActivity extends AppCompatActivity {

    private RecyclerView pdfsRecyclerView;
    private PdfAdapter pdfAdapter;
    private List<PdfFile> pdfFiles;
    private DatabaseReference mDatabase;
    private String userBranch; // Variable to store the user branch
    private String semester;
    private TextView branchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pdfs);
        branchTextView = findViewById(R.id.text);

        pdfsRecyclerView = findViewById(R.id.pdfsRecyclerView);
        pdfFiles = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String year = "";
        semester = "";
        if (intent != null && intent.hasExtra("year") && intent.hasExtra("semester")) {
            year = intent.getStringExtra("year");
            semester = intent.getStringExtra("semester");
        }
        getCurrentUserBranch();

        pdfAdapter = new PdfAdapter(this, pdfFiles);
        pdfsRecyclerView.setAdapter(pdfAdapter);
        pdfsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getCurrentUserBranch() {
        // Get the current user from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get the UID of the current user
            String uid = currentUser.getUid();

            // Construct the reference to the current user's branch in the database
            DatabaseReference userRef = mDatabase.child("users").child(uid);

            // Read the data from the database
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Retrieve the user's branch from the database
                    userBranch = dataSnapshot.child("branch").getValue(String.class);
                    branchTextView.setText(userBranch);
                    // Fetch PDF files from the database based on the user's branch
                    fetchPdfFiles();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors that occur during the read operation
                }
            });
        }
    }

    private void fetchPdfFiles() {
        String pdfsPath = "PDFs/" + userBranch + "/" + semester;
        DatabaseReference pdfsRef = mDatabase.child(pdfsPath);

        pdfsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot pdfSnapshot : dataSnapshot.getChildren()) {
                    String pdfId = pdfSnapshot.getKey();
                    String fileName = pdfSnapshot.child("name").getValue(String.class);
                    String downloadUrl = pdfSnapshot.child("url").getValue(String.class);

                    pdfFiles.add(new PdfFile(fileName, downloadUrl));
                }

                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the read operation
            }
        });
    }
}
