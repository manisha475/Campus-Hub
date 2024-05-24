package com.example.p2.portal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.p2.R;

public class portal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        ImageView slcmImageView = findViewById(R.id.slcm);
        ImageView sisImageView = findViewById(R.id.sis);
        ImageView marenaImageView = findViewById(R.id.marena);
        ImageView hostelImageView = findViewById(R.id.hostel);
        ImageView examImagaView = findViewById(R.id.exam);


        slcmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slcm();
            }
        });


        sisImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sis();
            }
        });


        marenaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                marena();
            }
        });


        examImagaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exam();
            }
        });


        hostelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hostel();
            }
        });



    }

    // Method to open the SLCM website
    private void slcm() {
        String url = "https://slcm.manipal.edu/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    private void sis() {
        String url = "https://sis.manipal.edu/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    private void marena() {
        String url = "https://payment.manipal.edu/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void hostel() {
        String url = "https://hostel.manipal.edu/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void exam() {
        String url = "https://manipal.examcloud.in/slogin.php";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
