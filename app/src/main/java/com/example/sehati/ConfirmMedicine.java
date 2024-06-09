package com.example.sehati;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmMedicine extends AppCompatActivity {
    TextView total_harga;
    TextView obat_1,obat_2,obat_3,harga_1,harga_2,harga_3;

    Button confirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_medicine);
        Intent intent = getIntent();
        total_harga = findViewById(R.id.tv_amount_cost_medicine);
        obat_1 = findViewById(R.id.obat1);
        obat_2 = findViewById(R.id.obat2);
        obat_3 = findViewById(R.id.obat3);
        harga_1 = findViewById(R.id.harga1);
        harga_2 = findViewById(R.id.harga2);
        harga_3 = findViewById(R.id.harga3);
        confirm = findViewById(R.id.bt_confirmMedicine);
        total_harga.setText(intent.getStringExtra("harga_total"));
        obat_1.setText(intent.getStringExtra("obat1"));
        obat_2.setText(intent.getStringExtra("obat2"));
        obat_3.setText(intent.getStringExtra("obat3"));
        harga_1.setText(intent.getStringExtra("harga1"));
        harga_2.setText(intent.getStringExtra("harga2"));
        harga_3.setText(intent.getStringExtra("harga3"));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ConfirmMedicine.this,Home.class);
                startActivity(intent1);
            }
        });
    }
}
