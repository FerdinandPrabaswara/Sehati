package com.example.sehati;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sehati.adapter.MusicTherapyAdapter;
import com.example.sehati.databinding.ActivityMusicTherapyBinding;
import com.example.sehati.model.MusicTherapy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MusicTherapyActivity extends AppCompatActivity {

    private ActivityMusicTherapyBinding binding;
    private DatabaseReference databaseReference;
    private List<MusicTherapy> musicTherapyList;
    private MusicTherapyAdapter musicTherapyAdapter;
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicTherapyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
//        mAuth = FirebaseAuth.getInstance();

        // Initialize RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        musicTherapyList = new ArrayList<>();
        musicTherapyAdapter = new MusicTherapyAdapter(this, musicTherapyList);
        binding.recyclerView.setAdapter(musicTherapyAdapter);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("musicTherapies");

        // Check authentication status
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            // User is authenticated, proceed with database access
//            fetchMusicTherapyData();
//        } else {
//            // User is not authenticated, handle accordingly (e.g., prompt for login)
//            Toast.makeText(this, "Please log in to access data", Toast.LENGTH_SHORT).show();
//            // Redirect user to login activity or prompt for login
//            // Example: startActivity(new Intent(this, LoginActivity.class));
//        }
    }

    private void fetchMusicTherapyData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                musicTherapyList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MusicTherapy musicTherapy = dataSnapshot.getValue(MusicTherapy.class);
                    musicTherapyList.add(musicTherapy);
                }
                musicTherapyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MusicTherapyActivity.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Failed to fetch data: " + error.getMessage());
            }
        });
    }
}
