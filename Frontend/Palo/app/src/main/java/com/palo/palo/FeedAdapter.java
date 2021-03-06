package com.palo.palo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.palo.palo.model.Palo;
import com.squareup.picasso.Picasso;

/**
 * Adapter for feed fragement's recyclerview.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Palo> palos;
    private OnFeedListener onFeedListener;

    /**
     * Constructor for FeedApapter. Sets list of post or "palos".
     * @param palos
     */
    public FeedAdapter(Context context, List<Palo> palos, OnFeedListener onFeedListener) {
        this.inflater = LayoutInflater.from(context);
        this.palos = palos;
        this.onFeedListener = onFeedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.basic_palo_layout, parent, false);
        return new ViewHolder(view, onFeedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.authorUserNameTV.setText(palos.get(position).getAuthorUsername());
        holder.postdateTV.setText(formatDate(palos.get(position).getPostDate()));
        holder.captionTV.setText(palos.get(position).getCaption());
        Picasso.get().load(palos.get(position).getProfileImage()).into(holder.authorProfileImage);

        // Attached Song stuff...
        holder.songTitleTV.setText(palos.get(position).getAttachment().getTitle());
        holder.songArtistTV.setText(palos.get(position).getAttachment().getArtist());
        Picasso.get().load(palos.get(position).getAttachment().getAlbumCover()).into(holder.songCoverImage);
        holder.likeCountTV.setText("("+palos.get(position).getLikeCount()+")");
        holder.toggleLikeHeart(palos.get(position).getIsLiked());
    }

    @Override
    public int getItemCount() {
        return palos.size();
    }
    
    public void swapDataSet(List<Palo> newPalos){
        this.palos = newPalos;
        notifyDataSetChanged();
    }

    public void updatePalo(int position, Palo palo){
        this.palos.set(position, palo);
        notifyItemChanged(position);
    }

    public String formatDate(String dateStr){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = formatter.parse(dateStr);
            Date now = new Date();
            long showTime = TimeUnit.MILLISECONDS.toDays(now.getTime() - date.getTime());
            if (showTime >0) return showTime + " days ago";
            showTime = TimeUnit.MILLISECONDS.toHours(now.getTime() - date.getTime());
            if (showTime > 0) return showTime + " hours ago";
            showTime = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - date.getTime()) ;
            if (showTime > 0) return showTime + " minutes ago";
            return "a few seconds ago";
        } catch (ParseException e) {
            return dateStr;
        }
    }

    /**
     * ViewHolder for FeedAdapter. Set posts info for each item in Feed Adapter.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //views for palo
        TextView authorUserNameTV, postdateTV, captionTV;
        ImageView authorProfileImage;

        // views for interation with palo
        TextView commentTV, likeTV, likeCountTV;

        // views for attached song
        TextView songTitleTV, songArtistTV;
        ImageView songCoverImage;

        OnFeedListener onFeedListener;

        public ViewHolder(@NonNull View itemView, OnFeedListener onFeedListener) {
            super(itemView);
            authorUserNameTV = itemView.findViewById(R.id.paloAuthorUserName);
            authorUserNameTV.setOnClickListener(v -> onFeedListener.onUserNameClicked(getAdapterPosition()));
            postdateTV = itemView.findViewById(R.id.paloDate);
            captionTV = itemView.findViewById(R.id.paloCaption);
            authorProfileImage = itemView.findViewById(R.id.paloAuthorProfileImage);
            commentTV = itemView.findViewById(R.id.paloComment);
            likeTV = itemView.findViewById(R.id.paloLike);
            likeCountTV = itemView.findViewById(R.id.paloLikeCount);
            songTitleTV = itemView.findViewById(R.id.songTitle);
            songArtistTV = itemView.findViewById(R.id.songArtist);
            songCoverImage = itemView.findViewById(R.id.coverImage);
            this.onFeedListener = onFeedListener;
            itemView.setOnClickListener(v -> onFeedListener.onPaloClick(getAdapterPosition()));
            likeTV.setOnClickListener(v -> likeClicked(getAdapterPosition()));
        }

        public void likeClicked(int pos){
//            toggleLikeHeart(palos.get(pos).toggleIsLiked());
            onFeedListener.onLikeClicked(pos);
//            notifyItemChanged(pos);
        }

        public void toggleLikeHeart(boolean isLiked){
            if(isLiked) likeTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_heart_full,0,0,0);
            else likeTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_heart_empty,0,0,0);
        }
    }

    public interface OnFeedListener {
        public void onPaloClick(int position);
        public void onLikeClicked(int position);
        public void onUserNameClicked(int position);
    }
}
