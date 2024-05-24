package com.example.p2.outnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2.R;

import java.util.List;

public class globalnewsadapter extends RecyclerView.Adapter<globalnewsadapter.NewsViewHolder> {

    private Context context;
    private List<NewsItem> articlelist;

    public globalnewsadapter(Context context, List<NewsItem> newsItemList) {
        this.context = context;
        this.articlelist = newsItemList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.globalnewsitem, parent, false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = articlelist.get(position);
        holder.titleTextView.setText(newsItem.getTitle());
        holder.sourceTextView.setText(newsItem.getSource());

        // Load image using Picasso
        Picasso.get().load(newsItem.getImageUrl()).into(holder.imageView);
    }

    void updateData(List<NewsItem> data)
    {
        articlelist.clear();
        articlelist.addAll(data);
    }


    @Override
    public int getItemCount() {
        return articlelist.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        TextView sourceTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.newsitem);
            titleTextView = itemView.findViewById(R.id.Headline);
            sourceTextView = itemView.findViewById(R.id.Source);
        }
    }
}
