package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.p2.notes.notesactivity;
import com.example.p2.Event.CalendarActivity;
import com.example.p2.Feedback.Feedback;
import com.example.p2.Timetable.Timetable;
import com.example.p2.lostandfound.lostandfound;
import com.example.p2.news.News;
import com.example.p2.outnews.MainActivity;
import com.example.p2.portal.portal;

public class dashboard extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageView profileIcon = findViewById(R.id.profileIcon);
        ImageView timetableIcon = findViewById(R.id.timetableIcon);
        ImageView notesIcon = findViewById(R.id.notesIcon);
        ImageView eventsIcon = findViewById(R.id.eventsIcon);
        ImageView newsIocn = findViewById(R.id.newsIcon);
        ImageView lostandfoundIcon = findViewById(R.id.lostandfoundIcon);
        ImageView feedbackIcon = findViewById(R.id.feedbackIcon);
        ImageView portal = findViewById(R.id.portalIcon);
        ImageView globalnews = findViewById(R.id.global);

        profileIcon.setOnClickListener(this);
        timetableIcon.setOnClickListener(this);
        notesIcon.setOnClickListener(this);
        eventsIcon.setOnClickListener(this);
        newsIocn.setOnClickListener(this);
        lostandfoundIcon.setOnClickListener(this);
        feedbackIcon.setOnClickListener(this);
        portal.setOnClickListener(this);
        globalnews.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.profileIcon) {
            intent = new Intent(this, ProfileActivity.class);
        } else if (v.getId() == R.id.timetableIcon) {
            intent = new Intent(this, Timetable.class);
        } else if (v.getId() == R.id.notesIcon) {
            intent = new Intent(this, notesactivity.class);
        } else if (v.getId() == R.id.eventsIcon) {
            intent = new Intent(this, CalendarActivity.class);
        } else if (v.getId() == R.id.newsIcon) {
            intent = new Intent(this, News.class);
        } else if (v.getId() == R.id.lostandfoundIcon) {
            intent = new Intent(this, lostandfound.class);
        } else if (v.getId() == R.id.feedbackIcon) {
            intent = new Intent(this, Feedback.class);
        } else if (v.getId() == R.id.portalIcon) {
            intent = new Intent(this, portal.class);
        } else if (v.getId() == R.id.global) {
            intent = new Intent(this, MainActivity.class);
        }


        if (intent != null) {
            startActivity(intent);
        }
    }
}
