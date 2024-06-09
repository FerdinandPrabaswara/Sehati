package com.example.sehati.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import com.example.sehati.BuyMedicine;
import com.example.sehati.R;
import com.example.sehati.model.Medicine;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>{
    private ArrayList<Medicine> _MedicineList;
    private Context context;
    String obat1,obat2,obat3,harga1,harga2,harga3;
    OnItemClickListener onItemClickListener;

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
        if (position == 0) {
            obat1 = _MedicineList.get(position).get_nameMedicine();
            harga1 = _MedicineList.get(position).get_priceMedicine();
            ((BuyMedicine) context).setObat1(_MedicineList.get(position).get_nameMedicine(),
                    _MedicineList.get(position).get_priceMedicine());
        } else if (position ==1) {
            obat2 = _MedicineList.get(position).get_nameMedicine();
            harga2 = _MedicineList.get(position).get_priceMedicine();
            ((BuyMedicine) context).setObat2(_MedicineList.get(position).get_nameMedicine(),
                    _MedicineList.get(position).get_priceMedicine());
        } else if (position ==2) {
            obat3 = _MedicineList.get(position).get_nameMedicine();
            harga3 = _MedicineList.get(position).get_priceMedicine();
            ((BuyMedicine) context).setObat3(_MedicineList.get(position).get_nameMedicine(),
                    _MedicineList.get(position).get_priceMedicine());
        }
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(_MedicineList.get(position)));

    }

    @Override
    public int getItemCount() {
        return _MedicineList.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener  = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onClick(Medicine medicine);
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder{
        TextView _namaObat;
        TextView _hargaObat;
        TextView _JumlahObat;
        ImageView _addMedicine;
        Context _context;
        public  int currentAmount =0;
        private ArrayList<Medicine> medicineItemList = new ArrayList<>();

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            _context = itemView.getContext();
            _namaObat = itemView.findViewById(R.id.tv_nameMedicine);
            _hargaObat = itemView.findViewById(R.id.tv_priceMedicine);
            _JumlahObat = itemView.findViewById(R.id.tv_amount);
            _addMedicine = itemView.findViewById(R.id.bt_addMedicine);

            _addMedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hargaObat = Integer.parseInt(_hargaObat.getText().toString());
                    currentAmount++;
                    ((BuyMedicine) _context).updateTotalHarga(hargaObat ,_namaObat.getText().toString(),_hargaObat.getText().toString());
                    _JumlahObat.setText(String.valueOf(currentAmount));
                }
            });
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
}
