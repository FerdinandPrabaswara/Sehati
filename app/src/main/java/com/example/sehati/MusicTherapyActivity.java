package com.example.sehati;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sehati.databinding.ActivityMusicTherapyBinding;
import com.example.sehati.model.MusicTherapy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MusicTherapyActivity extends AppCompatActivity {

    private ActivityMusicTherapyBinding binding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicTherapyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("musicTherapies");

        // Example CRUD operations
        createMusicTherapy(new MusicTherapy("photoUrl", "Relaxing Music", "Author Name", "3:45"));
        readMusicTherapy("musicTherapyId");
        updateMusicTherapy("musicTherapyId", new MusicTherapy("newPhotoUrl", "New Title", "New Author", "4:00"));
        deleteMusicTherapy("musicTherapyId");
    }

    private void createMusicTherapy(MusicTherapy musicTherapy) {
        String id = databaseReference.push().getKey();
        if (id != null) {
            databaseReference.child(id).setValue(musicTherapy).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "MusicTherapy created successfully");
                } else {
                    Log.w(TAG, "Failed to create MusicTherapy", task.getException());
                }
            });
        }
    }

    private void readMusicTherapy(String id) {
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MusicTherapy musicTherapy = snapshot.getValue(MusicTherapy.class);
                if (musicTherapy != null) {
                    Log.d(TAG, "MusicTherapy data: " + musicTherapy.getTitle());
                } else {
                    Log.w(TAG, "MusicTherapy not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read MusicTherapy", error.toException());
            }
        });
    }

    private void updateMusicTherapy(String id, MusicTherapy musicTherapy) {
        databaseReference.child(id).setValue(musicTherapy).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "MusicTherapy updated successfully");
            } else {
                Log.w(TAG, "Failed to update MusicTherapy", task.getException());
            }
        });
    }

    private void deleteMusicTherapy(String id) {
        databaseReference.child(id).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "MusicTherapy deleted successfully");
            } else {
                Log.w(TAG, "Failed to delete MusicTherapy", task.getException());
            }
        });
    }
}
