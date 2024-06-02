// RelaxMusicActivity.java
package com.example.sehati;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class RelaxMusic extends AppCompatActivity {

    private ImageView imgMusic;
    private TextView titleMusic, authorMusic, durationMusic;
    private AudioManager mAudioManager;
    private ImageButton play;
    private SeekBar mSeekBarTime, mSeekBarVol;
    private static MediaPlayer mMediaPlayer;
    private Runnable runnable;
    private int currentIndex = 0;
    private HashMap<String, Integer> songTitleToIndexMap;
    private Button btHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_music);

        titleMusic = findViewById(R.id.musicTitle);
        authorMusic = findViewById(R.id.musicAuthor);
        durationMusic = findViewById(R.id.musicDuration);
        play = findViewById(R.id.playButton);
        mSeekBarTime = findViewById(R.id.seekBar);
        btHome = findViewById(R.id.btHome);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Integer> songs = new ArrayList<>();
        songs.add(0, R.raw.atmosphericbackground);
        songs.add(1, R.raw.relaxingmusic);
        songs.add(2, R.raw.warmrain);

        // Map song titles to their indices
        songTitleToIndexMap = new HashMap<>();
        songTitleToIndexMap.put("Atmospheric Background", 0);
        songTitleToIndexMap.put("Relaxing Music", 1);
        songTitleToIndexMap.put("Warm Rain", 2);

        // Get data from intent
        String title = getIntent().getStringExtra("MUSIC_THERAPY_TITLE");
        String author = getIntent().getStringExtra("MUSIC_THERAPY_AUTHOR");
        String duration = getIntent().getStringExtra("MUSIC_THERAPY_DURATION");

        // Set data to views
        titleMusic.setText(title);
        authorMusic.setText(author);
        durationMusic.setText(duration);

        // Determine the song index based on the title
        if (songTitleToIndexMap.containsKey(title)) {
            currentIndex = songTitleToIndexMap.get(title);
        } else {
            currentIndex = 0; // Default to the first song if the title is not found
        }

        // Initialize MediaPlayer with the correct song
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable.iconplay);
                } else {
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable.iconpause);
                }

                songNames();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer != null) {
                    try {
                        if (mMediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHome = new Intent(RelaxMusic.this, Home.class);
                startActivity(backHome);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSeekBarTime.setProgress(msg.what);
        }
    };

    private void songNames() {
        // Placeholder method - if you need additional logic for song names, add here
    }
}
