package com.example.sehati;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sehati.adapter.MeditationAdapter;
import com.example.sehati.model.MeditationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Meditation extends AppCompatActivity {

    private RecyclerView meditationRecyclerView;
    private MeditationAdapter meditationAdapter;
    private List<MeditationModel> meditationList;
    private static final String TAG = "MeditationActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        meditationRecyclerView = findViewById(R.id.meditationRecyclerView);
        meditationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        meditationList = new ArrayList<>();
        meditationAdapter = new MeditationAdapter(meditationList);
        meditationRecyclerView.setAdapter(meditationAdapter);

        Log.d(TAG, "RecyclerView and Adapter are set");

        fetchMeditationData();
    }

    private void fetchMeditationData() {
        FirebaseDatabase.getInstance().getReference("meditation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meditationList.clear();
                for (DataSnapshot meditationSnapshot : snapshot.getChildren()) {
                    MeditationModel meditationModel = meditationSnapshot.getValue(MeditationModel.class);
                    if (meditationModel != null) {
                        meditationList.add(meditationModel);
                        Log.d(TAG, "Meditation Data: " + meditationModel.getTitle());
                    }
                }
                meditationAdapter.notifyDataSetChanged();
                Log.d(TAG, "Adapter notified, item count: " + meditationAdapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to fetch data", error.toException());
            }
        });
    }
}
