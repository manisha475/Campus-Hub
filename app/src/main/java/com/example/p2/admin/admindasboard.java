package com.example.p2.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.p2.R;

public class admindasboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindasboard);

        // Find the ImageView for upload pdf
        ImageView uploadPdfImageView = findViewById(R.id.uploadpdficon);
        // Find the ImageView for upload news
        ImageView uploadNewsImageView = findViewById(R.id.uploadnews);

        // Set OnClickListener on the upload pdf ImageView
        uploadPdfImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the upload pdf activity
                Intent intent = new Intent(admindasboard.this, uploadpdf.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener on the upload news ImageView
        uploadNewsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the upload news activity
                Intent intent = new Intent(admindasboard.this, uploadnews.class);
                startActivity(intent);
            }
        });
    }
}
