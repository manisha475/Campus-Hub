package com.example.p2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.p2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class uploadnews extends AppCompatActivity {

    private EditText headlineEditText;
    private EditText contentEditText;
    private EditText dateEditText;
    private ImageView imageView;
    private Button selectButton;
    private DatePicker datePicker;
    private Spinner spinner;

    private DatabaseReference newsRef;
    private StorageReference storageReference;

    private static final int GALLERY_REQUEST_CODE = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadnews);

        // Initialize EditText views
        headlineEditText = findViewById(R.id.headline);
        contentEditText = findViewById(R.id.content);
        dateEditText = findViewById(R.id.date);

        // Initialize ImageView and Button
        imageView = findViewById(R.id.imageView);
        selectButton = findViewById(R.id.submit);

        // Initialize DatePicker
        datePicker = new DatePicker(this);
        datePicker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH), null);

        // Initialize Spinner and set content
        spinner = findViewById(R.id.spinner);
        String[] spinnerItems = {"alumni", "clubs", "placements","Innovations"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Initialize Firebase
        newsRef = FirebaseDatabase.getInstance().getReference().child("news");
        storageReference = FirebaseStorage.getInstance().getReference();

        // Set OnClickListener for selectButton
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });

        // Set OnClickListener for select from gallery button
        Button selectFromGalleryButton = findViewById(R.id.selectfromgallery);
        selectFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    // Method to show DatePicker dialog
    public void showDatePicker(View view) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Select Date")
                .setView(datePicker)
                .setPositiveButton("OK", (dialog, which) -> {
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();
                    String selectedDate = day + "/" + month + "/" + year;
                    dateEditText.setText(selectedDate);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    // Method to open gallery for image selection
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    // Handle result of gallery intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    // Method to upload data to Firebase

    private void uploadToFirebase() {
        String headline = headlineEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String category = spinner.getSelectedItem().toString();

        if (!headline.isEmpty() && !content.isEmpty() && !date.isEmpty()) {
            DatabaseReference categoryRef = newsRef.child(category);

            // Generate human-readable ID based on current timestamp
            String newsId = String.valueOf(System.currentTimeMillis());

            if (newsId != null) {
                categoryRef.child(newsId).child("headline").setValue(headline);
                categoryRef.child(newsId).child("content").setValue(content);
                categoryRef.child(newsId).child("date").setValue(date);

                // Upload image if selected
                if (imageView.getDrawable() != null) {
                    uploadImageToFirebase(newsId, category, imageView);
                }

                // Clear EditTexts after uploading
                headlineEditText.setText("");
                contentEditText.setText("");
                dateEditText.setText("");

                // Inform user about successful upload
                showToast("Uploaded successfully");
            }
        } else {
            // Inform user to fill in all fields
            showToast("Please fill in all fields");
        }
    }

    // Method to upload image to Firebase Storage
    private void uploadImageToFirebase(String newsId, String category, ImageView imageView) {
        StorageReference imageRef = storageReference.child(category).child(newsId + ".jpg");

        // Get the image URI from ImageView
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful upload
            showToast("Image upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // Image uploaded successfully, get the download URL
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Save image URL to the Realtime Database
                DatabaseReference imageUrlRef = newsRef.child(category).child(newsId).child("imageUrl");
                imageUrlRef.setValue(uri.toString());
            }).addOnFailureListener(e -> {
                // Handle errors retrieving image URL
                showToast("Failed to get image URL");
            });
        });
    }



    // Method to display a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
