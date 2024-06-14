package com.example.sehati;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Profile extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivProfilePhoto;
    private TextView tvName, tvEmail;
    private Button btnUploadPhoto, btnDownloadPhoto;
    private Uri imageUri;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        btnUploadPhoto = findViewById(R.id.btn_upload_photo);
        btnDownloadPhoto = findViewById(R.id.btn_download_photo);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("User").child(currentUser.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("profile_photos").child(currentUser.getUid() + ".jpg");

        // Memuat data profil pengguna saat aplikasi dijalankan
        loadUserProfile();

        // Menambahkan listener untuk tombol unggah foto
        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka pemilih berkas untuk memilih gambar
                openFileChooser();
            }
        });

        // Menambahkan listener untuk tombol download foto
        btnDownloadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengunduh foto profil
                downloadProfilePhoto();
            }
        });
    }

    private void loadUserProfile() {
        // Mengambil data profil pengguna dari Firebase Realtime Database
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Mendapatkan nama dan email dari data snapshot
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    // Menampilkan nama dan email pada TextView
                    tvName.setText(name);
                    tvEmail.setText(email);

                    // Memuat foto profil pengguna
                    loadProfilePhoto();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Menampilkan pesan kesalahan jika gagal memuat data
                Toast.makeText(Profile.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProfilePhoto() {
        // Mendapatkan URL foto profil dari Firebase Storage
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Menggunakan Glide untuk memuat foto profil ke ImageView
                Glide.with(Profile.this).load(uri).into(ivProfilePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Jika gagal, menampilkan gambar default
                ivProfilePhoto.setImageResource(R.drawable.profile);
            }
        });
    }

    private void openFileChooser() {
        // Membuka pemilih berkas untuk memilih gambar
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Mendapatkan URI gambar yang dipilih
            imageUri = data.getData();
            // Mengunggah berkas gambar ke Firebase Storage
            uploadFile();
        }
    }

    private void uploadFile() {
        if (imageUri != null) {
            // Menampilkan progress dialog selama proses unggah
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Mengunggah gambar ke Firebase Storage
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Menyembunyikan progress dialog dan menampilkan pesan sukses
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            // Memuat ulang foto profil
                            loadProfilePhoto();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Menyembunyikan progress dialog dan menampilkan pesan kesalahan
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            // Menampilkan progres unggahan dalam persen
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            // Menampilkan pesan jika tidak ada berkas yang dipilih
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadProfilePhoto() {
        // Mendapatkan URL foto profil dari Firebase Storage
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Membuka koneksi ke URL untuk mengunduh berkas
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Membuat koneksi HTTP
                            HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();
                            connection.connect();
                            // Memeriksa jika koneksi berhasil
                            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Profile.this, "Failed to download photo", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            // Mendapatkan input stream dari koneksi
                            InputStream inputStream = connection.getInputStream();

                            // Menyimpan berkas ke penyimpanan publik menggunakan MediaStore
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ProfilePhotos");
                            values.put(MediaStore.Images.Media.DISPLAY_NAME, currentUser.getUid() + ".jpg");
                            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

                            ContentResolver resolver = getContentResolver();
                            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                            if (imageUri == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Profile.this, "Failed to create new MediaStore record.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }

                            OutputStream outputStream = resolver.openOutputStream(imageUri);
                            if (outputStream == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Profile.this, "Failed to open output stream.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }

                            // Menulis data dari input stream ke output stream
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }

                            // Menutup stream
                            outputStream.close();
                            inputStream.close();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Profile.this, "Photo downloaded successfully.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Profile.this, "Failed to download photo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
