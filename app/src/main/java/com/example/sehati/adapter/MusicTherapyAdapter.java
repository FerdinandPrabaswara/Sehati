package com.example.sehati.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.sehati.R;
import com.example.sehati.model.MusicTherapy;
import java.util.List;

public class MusicTherapyAdapter extends RecyclerView.Adapter<MusicTherapyAdapter.MusicTherapyViewHolder> {

    private Context context;
    private List<MusicTherapy> musicTherapyList;

    public MusicTherapyAdapter(Context context, List<MusicTherapy> musicTherapyList) {
        this.context = context;
        this.musicTherapyList = musicTherapyList;
    }

    @NonNull
    @Override
    public MusicTherapyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MusicTherapyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicTherapyViewHolder holder, int position) {
        MusicTherapy musicTherapy = musicTherapyList.get(position);
        holder.titleMusic.setText(musicTherapy.getTitle());
        holder.detailMusic.setText(musicTherapy.getDuration() + " • " + musicTherapy.getAuthor());

        // Load image using Glide
        Glide.with(context).load(musicTherapy.getPhoto()).into(holder.imgMusic);
    }

    @Override
    public int getItemCount() {
        return musicTherapyList.size();
    }

    public static class MusicTherapyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMusic;
        TextView titleMusic, detailMusic;

        public MusicTherapyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMusic = itemView.findViewById(R.id.imgMusic);
            titleMusic = itemView.findViewById(R.id.titleMusic);
            detailMusic = itemView.findViewById(R.id.detailMusic);
        }
    }
}
