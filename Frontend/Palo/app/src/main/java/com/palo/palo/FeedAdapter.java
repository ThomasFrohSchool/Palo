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

import java.util.List;

import com.palo.palo.model.Palo;
import com.squareup.picasso.Picasso;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Palo> palos;

    public FeedAdapter(Context context, List<Palo> palos){
        this.inflater = LayoutInflater.from(context);
        this.palos = palos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.basic_palo_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.authorUserNameTV.setText(palos.get(position).getAuthorUsername());
        holder.postdateTV.setText(palos.get(position).getPostDate());
        holder.captionTV.setText(palos.get(position).getCaption());
        Picasso.get().load(palos.get(position).getProfileImage()).into(holder.authorProfileImage);

        // Attached Song stuff...
        holder.songTitleTV.setText(palos.get(position).getAttachedSong().getTitle());
        holder.songArtistTV.setText(palos.get(position).getAttachedSong().getArtist());
        Picasso.get().load(palos.get(position).getAttachedSong().getAlbumCover()).into(holder.songCoverImage);
    }


    @Override
    public int getItemCount() {
        return palos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //views for palo
        TextView authorUserNameTV, postdateTV, captionTV;
        ImageView authorProfileImage;

        // views for interation with palo
        TextView commentTV, likeTV;

        // views for attached song
        TextView songTitleTV, songArtistTV;
        ImageView songCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorUserNameTV = itemView.findViewById(R.id.paloAuthorUserName);
            postdateTV = itemView.findViewById(R.id.paloDate);
            captionTV = itemView.findViewById(R.id.paloCaption);
            authorProfileImage = itemView.findViewById(R.id.paloAuthorProfileImage);
            commentTV = itemView.findViewById(R.id.paloComment);
            likeTV = itemView.findViewById(R.id.paloLike);

            songTitleTV = itemView.findViewById(R.id.songTitle);
            songArtistTV = itemView.findViewById(R.id.songArtist);
            songCoverImage = itemView.findViewById(R.id.coverImage);
            itemView.setOnClickListener(v -> Toast.makeText(v.getContext(), "POST was clicked!", Toast.LENGTH_SHORT).show());
//            commentTV.setOnClickListener(v -> Toast.makeText(v.getContext(), "Comment was clicked! this will eventually allow you to make a comment...", Toast.LENGTH_SHORT).show());
//            likeTV.setOnClickListener(v -> Toast.makeText(v.getContext(), "LIKE was clicked! this will eventually allow you to like this palo...", Toast.LENGTH_SHORT).show());
        }
    }
}
