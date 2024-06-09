package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Integer> songs = new ArrayList<>();
        songs.add(0, R.raw.atmosphericbackground);
        songs.add(1, R.raw.relaxingmusic);
        songs.add(2, R.raw.warmrain);

    }



}