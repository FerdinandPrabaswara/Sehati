package com.example.sehati;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmMedicine extends AppCompatActivity {
    TextView total_harga;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_medicine);
        Intent intent = getIntent();
        total_harga = findViewById(R.id.tv_amount_cost_medicine);
        total_harga.setText(intent.getStringExtra("harga_total"));

    }
}
