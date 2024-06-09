package com.example.sehati.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sehati.AppointmentDetail;
import com.example.sehati.R;
import com.example.sehati.model.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private Context context;
    private List<Doctor> doctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.doctorName.setText(doctor.getName());
        holder.doctorDetail.setText(doctor.getDetail());
        holder.doctorTime.setText(doctor.getTime());

        // Tambahkan click listener untuk button schedule
        holder.scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppointmentDetail.class);
                intent.putExtra("doctorName", doctor.getName());
                intent.putExtra("doctorDetail", doctor.getDetail());
                intent.putExtra("doctorTime", doctor.getTime());
                intent.putExtra("doctorPrice", doctor.getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, doctorDetail, doctorTime;
        Button scheduleBtn;
        ImageView doctorProfile, doctorBox, imageView8;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctorName);
            doctorDetail = itemView.findViewById(R.id.doctorDetail);
            doctorTime = itemView.findViewById(R.id.doctorTime);
            scheduleBtn = itemView.findViewById(R.id.scheduleBtn);
            doctorProfile = itemView.findViewById(R.id.doctorProfile);
            doctorBox = itemView.findViewById(R.id.doctorBox);
            imageView8 = itemView.findViewById(R.id.imageView8);
        }
    }
}