package com.example.sehati;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MeditationRecyclerViewAdapter extends RecyclerView.Adapter<MeditationRecyclerViewAdapter.MyViewHolder> {

    private final MeditationRecyclerViewInterface meditationRecyclerViewInterface;

    Context context;
    ArrayList<MeditationModel> meditationModelArrayList;
    public MeditationRecyclerViewAdapter(Context context, ArrayList<MeditationModel> meditationModelArrayList,
                                            MeditationRecyclerViewInterface meditationRecyclerViewInterface) {
        this.context = context;
        this.meditationModelArrayList = meditationModelArrayList;
        this.meditationRecyclerViewInterface = meditationRecyclerViewInterface;
    }

    @NonNull
    @Override
    public MeditationRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meditation_recycler_view_row, parent, false);

        return new MeditationRecyclerViewAdapter.MyViewHolder(view, meditationRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MeditationRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(meditationModelArrayList.get(position).getMeditationName());
        holder.tvDesc.setText(meditationModelArrayList.get(position).getMeditationDescription());
        holder.imageView.setImageResource(meditationModelArrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return meditationModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvDesc;
        public MyViewHolder(@NonNull View itemView,
                            MeditationRecyclerViewInterface meditationRecyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView19);
            tvName = itemView.findViewById(R.id.textView22);
            tvDesc = itemView.findViewById(R.id.textView20);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (meditationRecyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            meditationRecyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
