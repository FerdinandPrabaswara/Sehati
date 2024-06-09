package com.example.sehati.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sehati.BuyMedicine;
import com.example.sehati.R;


public class MedicineViewHolder extends RecyclerView.ViewHolder {
    TextView _namaObat;
    TextView _hargaObat;
    TextView _JumlahObat;
    ImageView _addMedicine;
    Context _context;
    public int totalHarga = 0;
    public int totalHargaItem = 0;
    public  int currentAmount =0;

    public MedicineViewHolder(@NonNull View itemView) {
        super(itemView);
        _context = itemView.getContext();
        _namaObat = itemView.findViewById(R.id.tv_nameMedicine);
        _hargaObat = itemView.findViewById(R.id.tv_priceMedicine);
        _JumlahObat = itemView.findViewById(R.id.tv_amount);
        _addMedicine = itemView.findViewById(R.id.bt_addMedicine);

//        _addMedicine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int hargaObat = Integer.parseInt(_hargaObat.getText().toString());
//                currentAmount++;
//                totalHarga = (currentAmount * hargaObat)-totalHargaItem;
//                ((BuyMedicine) _context).setTotalHarga(totalHarga);
//                _JumlahObat.setText(String.valueOf(currentAmount));
//
//            }
//        });
    }



    public TextView get_JumlahObat() {
        return _JumlahObat;
    }

    public TextView get_namaObat() {
        return _namaObat;
    }

    public TextView get_hargaObat() {
        return _hargaObat;
    }

    public Context get_context() {
        return _context;
    }
}
