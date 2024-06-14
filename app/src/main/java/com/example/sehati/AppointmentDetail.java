package com.example.sehati;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetail extends AppCompatActivity {
    private static final String TAG = "AppointmentDetail";
    private ImageView backBtn;
    private TextView doctorName, doctorDetail, doctorTime, doctorPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        backBtn = findViewById(R.id.backBtn);
        doctorName = findViewById(R.id.doctorName);
        doctorDetail = findViewById(R.id.doctorDetail);
        doctorTime = findViewById(R.id.doctorTime);
        doctorPrice = findViewById(R.id.doctorPrice);

        Intent intent = getIntent();
        String name = intent.getStringExtra("doctorName");
        String detail = intent.getStringExtra("doctorDetail");
        String time = intent.getStringExtra("doctorTime");
        String price = intent.getStringExtra("doctorPrice");

        // Logging data to check if they are received correctly
        Log.d(TAG, "Doctor Name: " + name);
        Log.d(TAG, "Doctor Detail: " + detail);
        Log.d(TAG, "Doctor Time: " + time);
        Log.d(TAG, "Doctor Price: " + price);


        doctorName.setText(name);
        doctorDetail.setText(detail);
        doctorTime.setText(time);
        doctorPrice.setText(price);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}