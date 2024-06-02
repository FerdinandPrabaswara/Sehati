package com.example.sehati;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sehati.adapter.DoctorAdapter;
import com.example.sehati.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class OnlineAppointment extends AppCompatActivity {

    private static final int REQUEST_CODE_CALENDAR = 1;
    private TextView appointmentDate;
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_appointment);

        appointmentDate = findViewById(R.id.tv_appointment_date);
        Button searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineAppointment.this, CalendarAppointment.class);
                startActivityForResult(intent, REQUEST_CODE_CALENDAR);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();
        populateDummyData();

        doctorAdapter = new DoctorAdapter(this, doctorList);
        recyclerView.setAdapter(doctorAdapter);
    }

    private void populateDummyData() {
        doctorList.add(new Doctor("Dr. Sahana V", "Msc in Clinical Psychology", "7:30 PM - 8:30 PM", "Rp. 150.000"));
        doctorList.add(new Doctor("Dr. John Doe", "MD in Medicine", "9:00 AM - 10:00 AM", "Rp. 200.000"));
        doctorList.add(new Doctor("Dr. Jane Smith", "PhD in Neurology", "11:00 AM - 12:00 PM", "Rp. 250.000"));
        doctorList.add(new Doctor("Dr. Emily Davis", "DMD in Dentistry", "2:00 PM - 3:00 PM", "Rp. 180.000"));
        doctorList.add(new Doctor("Dr. Michael Brown", "MD in Cardiology", "4:00 PM - 5:00 PM", "Rp. 220.000"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CALENDAR && resultCode == RESULT_OK) {
            if (data != null) {
                String selectedDate = data.getStringExtra("selected_date");
                appointmentDate.setText(selectedDate);
            }
        }
    }
}
