package com.example.sehati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button btnRegist;
    EditText etName, etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName      = findViewById(R.id.et_name);
        etEmail     = findViewById(R.id.et_email);
        etPassword  = findViewById(R.id.et_password);
        btnRegist   = findViewById(R.id.btn_regist);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name     = etName.getText().toString();
                String email    = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create user in Firebase Authentication
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Menyimpan nama ke realtime database
                                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        // Menyimpan nama ke realtime database di dalam tabel user dengan user ID
                                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                                        currentUserDb.child("name").setValue(name);

                                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        Intent intent_regist = new Intent(Register.this, Login.class);
                                        intent_regist.putExtra("name", name);
                                        startActivity(intent_regist);
                                    } else {
                                        Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }


}