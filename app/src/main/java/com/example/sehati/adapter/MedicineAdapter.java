package com.example.sehati.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sehati.R;
import com.example.sehati.ViewHolder.MedicineViewHolder;
import com.example.sehati.model.Medicine;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder>{
    private ArrayList<Medicine> _MedicineList;
    private Context context;

    public MedicineAdapter(ArrayList<Medicine> _MedicineList, Context context) {
        this._MedicineList = _MedicineList;
        this.context = context;

    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item,parent,false);
        MedicineViewHolder viewHolder = new MedicineViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.get_namaObat().setText(_MedicineList.get(position).get_nameMedicine());
        holder.get_hargaObat().setText(_MedicineList.get(position).get_priceMedicine());
        holder.get_JumlahObat().setText("0");

    }

    @Override
    public int getItemCount() {
        return _MedicineList.size();
    }
}
