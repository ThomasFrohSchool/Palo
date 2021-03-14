package com.palo.palo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.palo.palo.model.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AttachementSearchAdapter extends RecyclerView.Adapter<AttachementSearchAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Song> searchResults;
    int selectedIndex = -1;
    Context context;

    public AttachementSearchAdapter(Context context, List<Song> searchResults){
        this.inflater = LayoutInflater.from(context);
        this.searchResults = searchResults;
        this.context = context;
    }

    @NonNull
    @Override
    public AttachementSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_song_layout,parent,false);
        return new AttachementSearchAdapter.ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull AttachementSearchAdapter.ViewHolder holder, int position) {
        // Attached Song stuff...
        holder.songTitleTV.setText(searchResults.get(position).getTitle());
        holder.songArtistTV.setText(searchResults.get(position).getArtist());
        Picasso.get().load(searchResults.get(position).getAlbumCover()).into(holder.songCoverImage);
        if (position == selectedIndex)
            holder.card.setCardBackgroundColor(Color.parseColor("#ccf5ba"));
        else
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.design_default_color_background));
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Song was selected! " + position, Toast.LENGTH_SHORT).show();
            int previousItem = selectedIndex;
            selectedIndex = position;

            notifyItemChanged(previousItem);
            notifyItemChanged(position);
            holder.card.setCardBackgroundColor(Color.parseColor("#ccf5ba"));
        });
    }
    
    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public Song getSelectedSong(){
        if (selectedIndex == -1) return null;
        return searchResults.get(selectedIndex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView songTitleTV, songArtistTV;
        ImageView songCoverImage;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitleTV = itemView.findViewById(R.id.songTitle);
            songArtistTV = itemView.findViewById(R.id.songArtist);
            songCoverImage = itemView.findViewById(R.id.coverImage);
            card = itemView.findViewById(R.id.card_search_song);
        }
    }
}
