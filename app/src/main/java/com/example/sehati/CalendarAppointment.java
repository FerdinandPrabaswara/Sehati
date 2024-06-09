package com.example.sehati;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarAppointment extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    View submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_appointment);

        calendarView = findViewById(R.id.calendarView);
        submitBtn = findViewById(R.id.button);
        calendar = Calendar.getInstance();
        calendarView.setDate(calendar.getTimeInMillis());

        final TextView selectedDate = findViewById(R.id.selectedDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dateFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
                calendar.set(year, month, dayOfMonth);
                String formattedDate = sdf.format(calendar.getTime());
                selectedDate.setText(formattedDate);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = selectedDate.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_date", date);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}