package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;


import com.example.sehati.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    Button moveBuyMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        moveBuyMedicine = findViewById(R.id.bt_movetobuyMedicine);
        moveBuyMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ketika button di-klik, jalankan explicit intent ke Activity tujuan
                Intent intent = new Intent(Home.this, BuyMedicine.class);
                startActivity(intent);
            }
        });


        binding.relaxMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent relax = new Intent(Home.this, MusicTherapyActivity.class);
                startActivity(relax);
            }
        });



    }
}