// newsAdapter.java

package com.example.p2.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class newsadapter extends RecyclerView.Adapter<newsadapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String firebaseId);
    }

    private List<newsitem> newsItemList;
    private Context context;
    private OnItemClickListener listener;

    public newsadapter(List<newsitem> newsItemList, Context context, OnItemClickListener listener) {
        this.newsItemList = newsItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        newsitem currentItem = newsItemList.get(position);
        holder.headlineTextView.setText(currentItem.getHeadline());
        holder.postDateTextView.setText(currentItem.getPostDate());
        Picasso.get().load(currentItem.getImageUrl()).into(holder.newsItemImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(currentItem.getFirebaseId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsItemImageView;
        TextView headlineTextView;
        TextView postDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsItemImageView = itemView.findViewById(R.id.newsitem);
            headlineTextView = itemView.findViewById(R.id.Headline);
            postDateTextView = itemView.findViewById(R.id.PostDate);
        }
    }
}
