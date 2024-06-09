package com.example.sehati;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sehati.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.mindrot.jbcrypt.BCrypt;

public class Register extends AppCompatActivity {

    Button btnRegist;
    EditText etName, etEmail, etPassword;
    ImageView ivShowHidePassword;

    private boolean isPasswordVisible = false;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName              = findViewById(R.id.et_name);
        etEmail             = findViewById(R.id.et_email);
        etPassword          = findViewById(R.id.et_password);
        btnRegist           = findViewById(R.id.btn_regist);
        ivShowHidePassword   = findViewById(R.id.iv_show_hide_password);

        auth                = FirebaseAuth.getInstance();
        firebaseDatabase    = FirebaseDatabase.getInstance();
        databaseReference   = firebaseDatabase.getReference("User");

        user                = new User();

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name     = etName.getText().toString();
                String email    = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(name, email, password);
                }
            }
        });
        ivShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowHidePassword.setImageResource(R.drawable.hide_password);
                } else {
                    // Show password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    ivShowHidePassword.setImageResource(R.drawable.show_password);
                }
                // Move cursor to the end of the text
                etPassword.setSelection(etPassword.length());
                isPasswordVisible = !isPasswordVisible;
            }
        });
    }

    private void registerUser(String name, String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    Log.d(TAG, "User registered with ID: " + firebaseUser.getUid());

                    // Hash the password before saving to the database
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                    // Save user data into realtime database
                    User userData = new User(name,email, hashedPassword);

                    databaseReference.child(firebaseUser.getUid()).setValue(userData);

                    Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    intent.putExtra("email", email); // Mengirim email ke halaman login
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(Register.this, "This email address is already in use", Toast.LENGTH_SHORT).show();
                        etEmail.setError("Email is already registered");
                        etEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(Register.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                        etEmail.setError("Invalid email format");
                        etEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Register.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}



