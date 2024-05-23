package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Meditation extends AppCompatActivity implements MeditationRecyclerViewInterface {

    ArrayList<MeditationModel> meditationModelArrayList = new ArrayList<>();

    int[] meditationImages = {R.drawable.morning_sun,
                              R.drawable.reenergize_afternoon,
                              R.drawable.calm_night};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        RecyclerView recyclerView = findViewById(R.id.meditationRecyclerView);

        setMeditationModels();

        MeditationRecyclerViewAdapter adapter = new MeditationRecyclerViewAdapter(this,
                                                meditationModelArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setMeditationModels() {
        String[] meditationNames = getResources().getStringArray(R.array.meditation_music_txt);
        String[] meditationDescriptions = getResources().getStringArray(R.array.meditation_desc_txt);

        for (int i = 0; i < meditationNames.length; i++) {
            meditationModelArrayList.add(new MeditationModel(meditationNames[i],
                                             meditationDescriptions[i], meditationImages[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Meditation.this, MeditationDetails.class);

        intent.putExtra("NAME", meditationModelArrayList.get(position).getMeditationName());
        intent.putExtra("DESC", meditationModelArrayList.get(position).getMeditationDescription());
        intent.putExtra("IMAGE", meditationModelArrayList.get(position).getImage());

        startActivity(intent);
    }
}