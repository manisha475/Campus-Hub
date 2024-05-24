package com.example.p2.lostandfound;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p2.R;

import java.util.List;

public class lostadapter extends RecyclerView.Adapter<lostadapter.LostViewHolder> {

    private List<lost_item> lostItemList;

    public lostadapter(List<lost_item> lostItemList) {
        this.lostItemList = lostItemList;
    }

    @NonNull
    @Override
    public LostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lost_view, parent, false);
        return new LostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostViewHolder holder, int position) {
        lost_item lostItem = lostItemList.get(position);
        holder.Itemname.setText(lostItem.getItemName());
        holder.ItemDescription.setText(lostItem.getItemDescription());

        // Load image from Firebase Storage using Glide
        Glide.with(holder.itemView.getContext())
                .load(lostItem.getImageUrl())
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.error_image) // Error image if loading fails
                .into(holder.lostitem);

        // Set up button click listener
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                // Pass the item ID to Lostdetails activity
                Intent intent = new Intent(v.getContext(), Lostdetails.class);
                intent.putExtra("itemId", lostItem.getItemId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lostItemList.size();
    }

    public class LostViewHolder extends RecyclerView.ViewHolder {
        public ImageView lostitem;
        public TextView Itemname;
        public TextView ItemDescription;
        public Button actionButton;

        public LostViewHolder(@NonNull View itemView) {
            super(itemView);
            lostitem = itemView.findViewById(R.id.lostitem);
            Itemname = itemView.findViewById(R.id.Itemname);
            ItemDescription = itemView.findViewById(R.id.ItemDescription);
            actionButton = itemView.findViewById(R.id.actionButton);
        }
    }
}
