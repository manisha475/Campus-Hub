package com.example.p2.outnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.p2.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private globalnewsadapter newsAdapter;
    private List<NewsItem> articlelist;


    LinearProgressIndicator progressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);
        progressIndicator = findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        articlelist = new ArrayList<>();

        setRecyclerView();
        getNews();
    }

    void setRecyclerView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new globalnewsadapter(this, articlelist);
        recyclerView.setAdapter(newsAdapter);

    }

    void ChangeInProgress(boolean show)
    {
        if(show)
        {
            progressIndicator.setVisibility(View.VISIBLE);
        }
        else {
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }

    void getNews() {
        ChangeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("66cd55b7d68a4ad588e7807e2272aa28");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(() -> {
                            ChangeInProgress(false);
                            List<Article> articles = response.getArticles();
                            articlelist.clear(); // Clear existing data
                            for (Article article : articles) {
                                articlelist.add(new NewsItem(article)); // Convert Article to NewsItem
                            }
                            newsAdapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ChangeInProgress(false);
                        Log.e("NewsAPI", "Failed to fetch news: " + throwable.getMessage());
                    }
                }
        );
    }

}