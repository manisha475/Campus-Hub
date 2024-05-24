package com.example.p2.news;

public class newsitem {
    private String firebaseId; // Firebase ID field
    private String headline;
    private String postDate;
    private String imageUrl;

    // Constructor
    public newsitem(String firebaseId, String headline, String postDate, String imageUrl) {
        this.firebaseId = firebaseId;
        this.headline = headline;
        this.postDate = postDate;
        this.imageUrl = imageUrl;
    }

    // Getter method for Firebase ID
    public String getFirebaseId() {
        return firebaseId;
    }

    // Getter methods for other fields
    public String getHeadline() {
        return headline;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
