package com.palo.palo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.palo.palo.model.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AttachementSearchAdapter extends RecyclerView.Adapter<AttachementSearchAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Song> searchResults;
    public AttachementSearchAdapter(Context context, List<Song> searchResults){
        this.inflater = LayoutInflater.from(context);
        this.searchResults = searchResults;
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
    }
    
    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // views for attached song
        TextView songTitleTV, songArtistTV;
        ImageView songCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitleTV = itemView.findViewById(R.id.songTitle);
            songArtistTV = itemView.findViewById(R.id.songArtist);
            songCoverImage = itemView.findViewById(R.id.coverImage);
            itemView.setOnClickListener(v -> Toast.makeText(v.getContext(), "Song was selected!", Toast.LENGTH_SHORT).show());

        }
    }
}
