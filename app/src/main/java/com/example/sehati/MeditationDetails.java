package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MeditationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_details);

        String name = getIntent().getStringExtra("NAME");
        String description = getIntent().getStringExtra("DESC");
        String imageUrl = getIntent().getStringExtra("IMAGE_URL"); // Receiving the image URL

        TextView tvName = findViewById(R.id.meditationTitle);
        TextView tvDesc = findViewById(R.id.meditationAuthor);
        ImageView ivImage = findViewById(R.id.meditationImage);

        tvName.setText(name);
        tvDesc.setText(description);

        Glide.with(this).load(imageUrl).into(ivImage);
    }
}