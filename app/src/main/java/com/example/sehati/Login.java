package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin    = findViewById(R.id.btn_login);
        etEmail     = findViewById(R.id.et_email);
        etPassword  = findViewById(R.id.et_password);

//        for now use:
//        email   : sehati@student.ub.ac.id
//        pass    : rahasiakita

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email    = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Bundle name     = getIntent().getExtras();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if(email.equals("sehati@student.ub.ac.id") && password.equals("rahasiakita")){
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent_login = new Intent(Login.this, Home.class);
                    intent_login.putExtra("name", name);
                    startActivity(intent_login);
                } else {
                    Toast.makeText(Login.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}