package com.example.sehati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sehati.adapter.MedicineAdapter;
import com.example.sehati.model.Medicine;

import java.util.ArrayList;



public class BuyMedicine extends AppCompatActivity {
    RecyclerView recyclerView;
    MedicineAdapter medicineRecylerAdapter;
    ArrayList<Medicine> _MedicineList;
    Button buy_medicine;
    TextView tvAmountMedicine;
    ArrayList<Integer> totalHargaObat = new ArrayList<Integer>();

    ImageView addMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);
        buy_medicine = findViewById(R.id.bt_buy_medicine);
        tvAmountMedicine = findViewById(R.id.tv_amountMedicine);
        loadData();
        initRecylerView();

        buy_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BuyMedicine.this,ConfirmMedicine.class);
                intent.putExtra("harga_total",tvAmountMedicine.getText().toString());
                startActivity(intent);
            }
        });
    }
    private void initRecylerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView_Medicine);
        medicineRecylerAdapter = new MedicineAdapter(_MedicineList,this);
        recyclerView.setAdapter(medicineRecylerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void loadData(){
        _MedicineList = new ArrayList<Medicine>();
        _MedicineList.add(new Medicine("Obat Tidur","12000"));
        _MedicineList.add(new Medicine("Obat Perut","12000"));
        _MedicineList.add(new Medicine("Obat Kepala","10000"));
    }
    public void setTotalHarga(int totalHarga) {
        totalHargaObat.add(totalHarga);
        tvAmountMedicine.setText(String.valueOf(calculate(totalHargaObat)));
    }
    public Integer calculate(ArrayList<Integer> a) {
        int sum = 0;
        int lastValue = 0;

        for (int i = 0; i < a.size(); i++) {
            sum += a.get(i);


            if (i == a.size() - 2) {
                lastValue = sum;
            }
        }
        return sum - lastValue;
    }
}