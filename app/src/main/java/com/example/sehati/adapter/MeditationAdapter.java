package com.example.sehati.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.sehati.MeditationDetails;
import com.example.sehati.R;
import com.example.sehati.model.MeditationModel;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;

public class MeditationAdapter extends RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder> {

    private List<MeditationModel> meditationList;

    public MeditationAdapter(List<MeditationModel> meditationList) {
        this.meditationList = meditationList;
    }

    @NonNull
    @Override
    public MeditationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meditation, parent, false);
        return new MeditationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeditationViewHolder holder, int position) {
        MeditationModel meditationItem = meditationList.get(position);
        holder.titleTextView.setText(meditationItem.getTitle());
        holder.descriptionTextView.setText(meditationItem.getAuthor() + " • " + meditationItem.getDuration());
        Glide.with(holder.itemView.getContext()).load(meditationItem.getPhoto()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, MeditationDetails.class);
            intent.putExtra("NAME", meditationItem.getTitle());
            intent.putExtra("DESC", meditationItem.getAuthor() + " • " + meditationItem.getDuration());
            intent.putExtra("IMAGE_URL", meditationItem.getPhoto());  // Passing image URL
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return meditationList.size();
    }

    public static class MeditationViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public MeditationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.meditationIcon);
            titleTextView = itemView.findViewById(R.id.titleView);
            descriptionTextView = itemView.findViewById(R.id.author);
        }
    }
}
