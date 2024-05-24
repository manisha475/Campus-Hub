package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ImageView and ProgressBar by their IDs
        ImageView imageView = findViewById(R.id.imageView);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Start the RegisterActivity after a delay
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(new Intent(MainActivity.this, panel.class));
                        // Finish the MainActivity so that it's not kept in the back stack
                        finish();
                    }
                }, 3000); // 3000 milliseconds = 3 seconds delay
    }
}
