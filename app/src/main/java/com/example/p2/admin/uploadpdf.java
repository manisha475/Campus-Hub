package com.example.p2.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class uploadpdf extends AppCompatActivity {

    private Button selectPdfButton, uploadButton;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    Spinner branchSpinner,spinner;
    private static final int PDF_REQUEST_CODE = 1;
    private Uri selectedPdfUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpdf);

        selectPdfButton = findViewById(R.id.select_pdf_button);
        uploadButton = findViewById(R.id.upload_button);
        branchSpinner = findViewById(R.id.spinner);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        spinner = findViewById(R.id.semester);

        List<String> items = new ArrayList<>();
        items.add("Sem1");
        items.add("Sem2");


        // Create an ArrayAdapter using the list of items and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PDFs");

        selectPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdfFile();
            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.branches_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(adapter1);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPdfUri != null) {
                    uploadPdfFile();
                } else {
                    Toast.makeText(uploadpdf.this, "Please select a PDF file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectPdfFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PDF_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedPdfUri = data.getData();
        }
    }

    private void uploadPdfFile() {
        progressDialog.show();

        // Get the original name of the PDF file
        String pdfName = getFileName(selectedPdfUri);

        // Get the selected branch and semester from the spinner
        String selectedBranch = branchSpinner.getSelectedItem().toString();
        String selectedSemester = spinner.getSelectedItem().toString(); // Corrected spinner name

        // Create the storage reference with the selected branch and semester
        StorageReference branchRef = storageReference.child("PDF").child(selectedBranch).child(selectedSemester); // Adjusted storage path

        // Create a reference for the PDF file under the selected branch and semester
        final StorageReference filePath = branchRef.child(pdfName);

        filePath.putFile(selectedPdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Get the download URL
                                String pdfUrl = uri.toString();

                                // Create a Map to store PDF details
                                Map<String, Object> pdfDetails = new HashMap<>();
                                pdfDetails.put("name", pdfName); // Use original PDF name
                                pdfDetails.put("url", pdfUrl);

                                // Upload PDF details to the database under selectedBranch
                                databaseReference.child(selectedBranch).child(selectedSemester).push().setValue(pdfDetails)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(uploadpdf.this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(uploadpdf.this, "PDF upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(uploadpdf.this, "PDF upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    // Method to get the original file name from Uri
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) { // Check if the column index is valid
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
