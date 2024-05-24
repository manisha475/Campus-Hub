package com.example.p2.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.p2.R;

import java.util.ArrayList;
import java.util.List;

public class notesactivity extends AppCompatActivity {

    Spinner yearSpinner, semesterSpinner;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesactivity);

        yearSpinner = findViewById(R.id.yearSpinner);
        semesterSpinner = findViewById(R.id.semesterSpinner);
        submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedYear = yearSpinner.getSelectedItem().toString();
                String selectedSemester = semesterSpinner.getSelectedItem().toString();

                Intent intent = new Intent(notesactivity.this, AllPdfsActivity.class);
                intent.putExtra("year", selectedYear);
                intent.putExtra("semester", selectedSemester);
                startActivity(intent);
            }
        });

        populateYearSpinner();
        populateSemesterSpinner();
    }

    private void populateYearSpinner() {
        List<String> years = new ArrayList<>();
        years.add("1");
        years.add("2");
        years.add("3");
        years.add("4");

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }

    private void populateSemesterSpinner() {
        List<String> semesters = new ArrayList<>();
        semesters.add("Sem1");
        semesters.add("Sem2");

        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesters);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);
    }
}
