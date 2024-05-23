package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MeditationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_details);

        String name = getIntent().getStringExtra("NAME");
        String description = getIntent().getStringExtra("DESC");
        int image = getIntent().getIntExtra("IMAGE", 0);

        TextView tvName = findViewById(R.id.MeditationDetails_Title);
        TextView tvDesc = findViewById(R.id.MeditationDetails_Description);
        ImageView imgMeditation = findViewById(R.id.imgView_Meditation);

        tvName.setText(name);
        tvDesc.setText(description);
        imgMeditation.setImageResource(image);

    }
}