package com.example.p2.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.p2.R;

public class lostandfound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostandfound);

        // Button to find lost items
        Button findItemsButton = findViewById(R.id.button);

        // Set OnClickListener for the button
        findItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Direct the user to the lostItems activity
                Intent intent = new Intent(lostandfound.this, lostItems.class);
                startActivity(intent);
            }
        });

        // ImageView for lost and found
        ImageView lfImageView = findViewById(R.id.lf);

        lfImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Direct the user to the uploadfound activity
                Intent intent = new Intent(lostandfound.this, uploadfound.class);
                startActivity(intent);
            }
        });
    }
}
