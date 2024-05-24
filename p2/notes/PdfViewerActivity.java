package com.example.p2.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.p2.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {

    private InputStream inputStream;
    private PDFView pdfView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);

        String fileName = getIntent().getStringExtra("fileName");
        String downloadUrl = getIntent().getStringExtra("downloadUrl");

        if (downloadUrl != null) {
            loadPdfFromUrl(downloadUrl);
        } else {
            Toast.makeText(this, "Download URL is null", Toast.LENGTH_SHORT).show();
            finish();
        }

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(downloadUrl, fileName);
            }
        });
    }

    private void loadPdfFromUrl(String downloadUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                inputStream = connection.getInputStream();

                runOnUiThread(() -> {
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        pdfView.fromStream(inputStream)
                                .onRender((pages) -> {
                                    if (pages >= 1) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    // Return null as it is expected by the lambda expression
                                }).load();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void downloadPdf(String downloadUrl, String fileName) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downloadUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading PDF...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        // Close InputStream when the activity is destroyed to avoid memory leaks
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy(); // Call super.onDestroy() to ensure proper handling of the lifecycle event
    }
}
