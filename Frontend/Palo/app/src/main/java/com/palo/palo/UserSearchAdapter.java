package com.palo.palo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.palo.palo.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for user search recyclerview.
 */
public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<User> searchResults;
    int selectedIndex = -1;
    Context context;

    public UserSearchAdapter(Context context, List<User> searchResults) {
        this.inflater = LayoutInflater.from(context);
        this.searchResults = searchResults;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_user_layout,parent,false);
        return new UserSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userNameTV.setText(searchResults.get(position).getUsername());
        Picasso.get().load(searchResults.get(position).getProfileImage()).into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTV;
        ImageView profilePic;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userName);
            profilePic = itemView.findViewById(R.id.profilePic);
            card = itemView.findViewById(R.id.card_search_user);
        }
    }
}
