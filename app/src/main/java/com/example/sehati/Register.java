package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent_regist = new Intent(Register.this, Login.class);
                    intent_regist.putExtra("name", etName.getText().toString());
                    startActivity(intent_regist);
                }

            }
        });

    }
}