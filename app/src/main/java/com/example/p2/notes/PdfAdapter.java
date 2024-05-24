package com.example.p2.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p2.R;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    private Context mContext;
    private List<PdfFile> mPdfFiles;

    public PdfAdapter(Context context, List<PdfFile> pdfFiles) {
        mContext = context;
        mPdfFiles = pdfFiles;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pdf_item, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        PdfFile pdfFile = mPdfFiles.get(position);
        holder.bind(pdfFile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked PDF file
                PdfFile clickedPdfFile = mPdfFiles.get(holder.getAdapterPosition());

                // Create an intent to start PdfViewerActivity
                Intent intent = new Intent(mContext, PdfViewerActivity.class);
                // Pass the file name and download URL as extras
                intent.putExtra("fileName", clickedPdfFile.getFileName());
                intent.putExtra("downloadUrl", clickedPdfFile.getDownloadUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPdfFiles.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder {

        private ImageView pdfIconImageView;
        private TextView pdfNameTextView;

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfIconImageView = itemView.findViewById(R.id.pdfIconImageView);
            pdfNameTextView = itemView.findViewById(R.id.pdfNameTextView);
        }

        public void bind(PdfFile pdfFile) {
            pdfNameTextView.setText(pdfFile.getFileName());
            // Assuming you have a PDF icon image, you can load it here using Glide or any other image loading library
            Glide.with(mContext)
                    .load(R.drawable.pdficon) // Replace with actual PDF icon resource or URL if applicable
                    .into(pdfIconImageView);
        }
    }
}
