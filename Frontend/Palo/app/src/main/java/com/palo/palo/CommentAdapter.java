package com.palo.palo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.palo.palo.model.Comment;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Adapter for comment recyclerview.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Comment> comments;
    private OnCommentListener onCommentListener;

    public CommentAdapter(Context context, List<Comment> comments, OnCommentListener onCommentListener){
        this.inflater = LayoutInflater.from(context);
        this.comments = comments;
        this.onCommentListener = onCommentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.basic_comment_layout,parent,false);
        return new ViewHolder(view, onCommentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment c = comments.get(position);
        if(c.getAuthor().getUsername() == null) {
            holder.authorUserNameTV.setText("Placeholder");
        }
        else {
            holder.authorUserNameTV.setText(c.getAuthor().getUsername());
        }
        holder.postdateTV.setText(formatDate(c.getPostDate()));
        holder.captionTV.setText(c.getCaption());
        Picasso.get().load(c.getAuthor().getProfileImage()).into(holder.authorProfileImage);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void swapDataSet(List<Comment> newComments){
        this.comments = newComments;
        notifyDataSetChanged();
    }

    public void updateComment(int position, Comment comment){
        this.comments.set(position, comment);
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


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView authorUserNameTV, postdateTV, captionTV;
        ImageView authorProfileImage;
        OnCommentListener onCommentListener;

        public ViewHolder(@NonNull View itemView, OnCommentListener onCommentListener) {
            super(itemView);
            this.onCommentListener = onCommentListener;
            authorUserNameTV = itemView.findViewById(R.id.commentUsername);
            authorUserNameTV.setOnClickListener(v -> onCommentListener.onCommentUserClicked(getAdapterPosition()));
            postdateTV = itemView.findViewById(R.id.commentTimeStamp);
            captionTV = itemView.findViewById(R.id.commentCaption);
            authorProfileImage = itemView.findViewById(R.id.commentUserImage);
        }
    }

    public interface OnCommentListener {
        public void onCommentUserClicked(int position);
    }
}
