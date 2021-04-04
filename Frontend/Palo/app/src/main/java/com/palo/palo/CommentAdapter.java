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

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Comment> comments;


    public CommentAdapter(Context context, List<Comment> comments){
        this.inflater = LayoutInflater.from(context);
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.basic_comment_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment c = comments.get(position);
        holder.authorUserNameTV.setText(c.getAuthor());
        holder.postdateTV.setText(c.getPostDate());
        holder.captionTV.setText(c.getCaption());
//        Picasso.get().load(c.getAuthor()).into(holder.authorProfileImage);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView authorUserNameTV, postdateTV, captionTV;
        ImageView authorProfileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorUserNameTV = itemView.findViewById(R.id.commentUsername);
            postdateTV = itemView.findViewById(R.id.commentTimeStamp);
            captionTV = itemView.findViewById(R.id.commentCaption);
            authorProfileImage = itemView.findViewById(R.id.commentUserImage);
        }
    }
}
