package com.example.p2.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements CAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initializeViews();
        selectedDate = Calendar.getInstance();
        setMonthView();
    }

    private void initializeViews() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(formatMonthYear(selectedDate));
        ArrayList<String> daysInMonth = getDaysInMonth(selectedDate);

        CAdapter calendarAdapter = new CAdapter(daysInMonth, this);
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 7));
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> getDaysInMonth(Calendar date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        Calendar calendar = (Calendar) date.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String formatMonthYear(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return sdf.format(date.getTime());
    }

    public void previousMonthAction(View view) {
        selectedDate.add(Calendar.MONTH, -1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate.add(Calendar.MONTH, 1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.isEmpty()) {
            String message = dayText + " " + formatMonthYear(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Pass the selected date to the eventlist activity
            Intent intent = new Intent(CalendarActivity.this, eventlist.class);
            intent.putExtra("MESSAGE", message);
            startActivity(intent);
        }
    }
}
