package com.example.sehati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.sehati.adapter.MedicineAdapter;
import com.example.sehati.model.Medicine;

import java.util.ArrayList;
import java.util.Objects;


public class BuyMedicine extends AppCompatActivity {
    RecyclerView recyclerView;
    MedicineAdapter medicineRecylerAdapter;
    ArrayList<Medicine> _MedicineList;
    Button buy_medicine;
    TextView tvAmountMedicine;
    ArrayList<Integer> totalHargaObat = new ArrayList<Integer>();
    FirebaseDatabase database;
    ImageView addMedicine;
    private int totalHarga = 0;
    String _namaObat,_hargaObat;

    ImageView add;
    private String obat1,obat2,obat3,harga1,harga2,harga3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);
        buy_medicine = findViewById(R.id.bt_buy_medicine);
        tvAmountMedicine = findViewById(R.id.tv_amountMedicine);
        FirebaseApp.initializeApp(BuyMedicine.this);
        database = FirebaseDatabase.getInstance();
        add = findViewById(R.id.iv_pushMedicine);
        loadData();
//        initRecylerView();

        buy_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BuyMedicine.this,ConfirmMedicine.class);
                intent.putExtra("harga_total",tvAmountMedicine.getText().toString());
//                intent.putExtra("nama_obat",_namaObat);
//                intent.putExtra("harga_obat",_hargaObat);
                intent.putExtra("obat1",obat1);
                intent.putExtra("obat2",obat2);
                intent.putExtra("obat3",obat3);
                intent.putExtra("harga1",harga1);
                intent.putExtra("harga2",harga2);
                intent.putExtra("harga3",harga3);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(BuyMedicine.this).inflate(R.layout.add_medicine_dialog,null);
                TextInputLayout titleLayout, contectLayout;
                titleLayout = view1.findViewById(R.id.titleLayout);
                contectLayout = view1.findViewById(R.id.contentLayout);
                TextInputEditText titleET, contentET;
                titleET = view1.findViewById(R.id.titleET);
                contentET = view1.findViewById(R.id.contentET);
                AlertDialog alertDialog = new AlertDialog.Builder(BuyMedicine.this)
                        .setTitle("Add")
                        .setView(view1)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(titleET.getText().toString().isEmpty())) {
                                    titleLayout.setError("This field is required");
                                }else if(Objects.requireNonNull(contentET.getText().toString().isEmpty())){
                                    contectLayout.setError("This field is required");
                                } else{
                                    ProgressDialog dialog1 = new ProgressDialog(BuyMedicine.this);
                                    dialog1.setMessage("Storing in Databas .......");
                                    dialog1.show();
                                    Medicine medicine = new Medicine();
                                    medicine.set_nameMedicine(titleET.getText().toString());
                                    medicine.set_priceMedicine(contentET.getText().toString());
                                    database.getReference().child("medicine").push().setValue(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog1.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(BuyMedicine.this,"Saved Succes",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog1.dismiss();
                                            Toast.makeText(BuyMedicine.this,"There was an error",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();;

            }
        });
    }
//    private void initRecylerView(){
//        RecyclerView recyclerView = findViewById(R.id.recyclerView_Medicine);
//        medicineRecylerAdapter = new MedicineAdapter(_MedicineList,this);
//        recyclerView.setAdapter(medicineRecylerAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
    private void loadData(){
//        _MedicineList = new ArrayList<Medicine>();
//        _MedicineList.add(new Medicine("Obat Tidur","12000"));
//        _MedicineList.add(new Medicine("Obat Perut","12000"));
//        _MedicineList.add(new Medicine("Obat Kepala","10000"));
        RecyclerView recyclerView = findViewById(R.id.recyclerView_Medicine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database.getReference().child("medicine").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Medicine> _MedicineList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Medicine medicine = dataSnapshot.getValue(Medicine.class);
                    Objects.requireNonNull(medicine).setKey(dataSnapshot.getKey());
                    _MedicineList.add(medicine);
                }

                medicineRecylerAdapter = new MedicineAdapter(_MedicineList,BuyMedicine.this);
                recyclerView.setAdapter(medicineRecylerAdapter);
                medicineRecylerAdapter.setOnItemClickListener(new MedicineAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Medicine medicine) {
                        View view = LayoutInflater.from(BuyMedicine.this).inflate(R.layout.add_medicine_dialog,null);
                        TextInputLayout titleLayout, contentLayout;
                        TextInputEditText titleET, contentET;
                        titleET = view.findViewById(R.id.titleET);
                        contentET = view.findViewById(R.id.contentET);
                        titleLayout = view.findViewById(R.id.titleLayout);
                        contentLayout =view.findViewById(R.id.contentLayout);
                        titleET.setText(medicine.get_nameMedicine());
                        contentET.setText(medicine.get_priceMedicine());
                        ProgressDialog progressDialog= new ProgressDialog(BuyMedicine.this);
                        AlertDialog alertDialog = new AlertDialog.Builder(BuyMedicine.this)
                                .setTitle("Edit")
                                .setView(view)
                                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Objects.requireNonNull(titleET.getText().toString().isEmpty())) {
                                            titleLayout.setError("This field is required");
                                        }else if(Objects.requireNonNull(contentET.getText().toString().isEmpty())){
                                            contentLayout.setError("This field is required");
                                        } else{

                                            progressDialog.setMessage("Saving .......");
                                            progressDialog.show();
                                            Medicine medicine1 = new Medicine();
                                            medicine1.set_nameMedicine(titleET.getText().toString());
                                            medicine1.set_priceMedicine(contentET.getText().toString());
                                            database.getReference().child("medicine").child(medicine.getKey()).setValue(medicine1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    dialogInterface.dismiss();
                                                    Toast.makeText(BuyMedicine.this,"Saved Succes",Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(BuyMedicine.this,"There was an error",Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                        }
                                    }
                                })
                                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Deleting....");
                                        progressDialog.show();
                                        database.getReference().child("medicine").child(medicine.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(BuyMedicine.this, "Deleted Succsessfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateTotalHarga(int hargaObat,String _namaObat, String _hargaObat) {
        this._namaObat = _namaObat;
        this._hargaObat = _hargaObat;
        totalHarga += hargaObat;
        tvAmountMedicine.setText(String.valueOf(totalHarga));
    }
    public void setObat1(String obat1,String harga1) {
        this.obat1 = obat1;
        this.harga1 = harga1;
    }
    public void setObat2(String obat2, String harga2){
        this.obat2 = obat2;
        this.harga2 = harga2;
    }
    public void setObat3(String obat3, String harga3){
        this.obat3 = obat3;
        this.harga3 = harga3;
    }

}