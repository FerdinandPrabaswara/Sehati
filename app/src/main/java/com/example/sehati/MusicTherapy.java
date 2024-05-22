package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sehati.databinding.ActivityMusicTherapyBinding;

public class MusicTherapy extends AppCompatActivity {

    ActivityMusicTherapyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicTherapyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}