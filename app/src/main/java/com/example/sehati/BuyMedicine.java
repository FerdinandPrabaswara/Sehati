package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuyMedicine extends AppCompatActivity {
    Button buy_medicine;
    TextView cost_medicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);
        buy_medicine = findViewById(R.id.bt_buy_medicine);
        cost_medicine = findViewById(R.id.tv_amountMedicine);

        buy_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(BuyMedicine.this,ConfirmMedicine.class);
                intent.putExtra("harga_total",cost_medicine.getText().toString());
                startActivity(intent);
            }
        });
    }
}