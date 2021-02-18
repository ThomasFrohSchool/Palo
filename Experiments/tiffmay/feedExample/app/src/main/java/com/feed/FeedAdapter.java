package com.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feed.models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Song> songs;

    public FeedAdapter(Context context, List<Song> songs){
        this.inflater = LayoutInflater.from(context);
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songTitleTV.setText(songs.get(position).getTitle());
        holder.songArtistTV.setText(songs.get(position).getArtist());
        Picasso.get().load(songs.get(position).getAlbumCover()).into(holder.songCoverImage);
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView songTitleTV, songArtistTV;
        ImageView songCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songTitleTV = itemView.findViewById(R.id.songTitle);
            songArtistTV = itemView.findViewById(R.id.songArtist);
            songCoverImage = itemView.findViewById(R.id.coverImage);

            itemView.setOnClickListener(v -> Toast.makeText(v.getContext(), "Song was clicked! :)", Toast.LENGTH_SHORT).show());
        }
    }
}
