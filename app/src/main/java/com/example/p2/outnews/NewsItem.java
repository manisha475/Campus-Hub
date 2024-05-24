package com.example.p2.outnews;

import com.kwabenaberko.newsapilib.models.Article;

public class NewsItem {
    private String title;
    private String source;
    private String imageUrl;

    public NewsItem(Article article) {
        this.title = article.getTitle();
        this.source = article.getSource().getName();
        this.imageUrl = article.getUrlToImage(); // Assuming this is the image URL field in Article
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
