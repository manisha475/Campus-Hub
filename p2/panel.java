package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

import com.example.p2.admin.admindasboard;
import com.example.p2.outnews.MainActivity;

public class panel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        // Find the admin ImageView
        ImageView adminImageView = findViewById(R.id.admin);

        // Set OnClickListener on admin ImageView
        adminImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to AdminDashboard activity
                Intent intent = new Intent(panel.this, adlogin.class);
                startActivity(intent);
            }
        });

        // Find the student ImageView
        ImageView studentImageView = findViewById(R.id.student);

        // Set OnClickListener on student ImageView
        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to RegisterActivity
                Intent intent = new Intent(panel.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
