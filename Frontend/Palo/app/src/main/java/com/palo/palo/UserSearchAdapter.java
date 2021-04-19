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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for user search recyclerview.
 */
public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<User> searchResults;
    int selectedIndex = -1;
    Context context;
    private onUserListener onUserListener;
    private User user;
    ArrayList<Integer> userFollowing;

    public UserSearchAdapter(Context context, List<User> searchResults, onUserListener onUserListener) {
        user = SharedPrefManager.getInstance(context).getUser();
        System.out.println("Picture: " + user.getProfileImage());
        this.inflater = LayoutInflater.from(context);
        this.searchResults = searchResults;
        this.context = context;
        this.onUserListener = onUserListener;
        this.userFollowing = user.getUserFollowing();
        System.out.println(user.getUserFollowing());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_user_layout,parent,false);
        return new UserSearchAdapter.ViewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userNameTV.setText(searchResults.get(position).getUsername());
        Picasso.get().load(searchResults.get(position).getProfileImage()).into(holder.profilePic);
        /*for(int i = 0; i < searchResults.size(); i++) {
            for(int j = 0; j < userFollowing.size(); j++) {
                if(searchResults.get(i).getId() == userFollowing.get(j)) {
                    holder.toggleFollow(true);
                }
            }
        }*/
        //holder.toggleFollow(user.toggleIsFollowing(searchResults.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void swapDataSet(List<User> users){
        this.searchResults = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTV;
        TextView userFollowTV;
        ImageView profilePic;
        CardView card;
        UserSearchAdapter.onUserListener onUserListener;

        public ViewHolder(@NonNull View itemView, onUserListener onUserListener) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userName);
            userFollowTV = itemView.findViewById(R.id.userFollow);
            profilePic = itemView.findViewById(R.id.profilePic);
            card = itemView.findViewById(R.id.card_search_user);
            this.onUserListener = onUserListener;

            userFollowTV.setOnClickListener(v -> followClicked(getAdapterPosition()));
        }

        public void followClicked(int pos) {
            toggleFollow(searchResults.get(pos).toggleIsFollowing(searchResults.get(pos).getId()));
            onUserListener.onFollowClicked(pos);
            notifyItemChanged(pos);
        }

        public void toggleFollow(boolean isFollowing) {
            if(isFollowing)
                userFollowTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_close, 0, 0, 0);
            else
                userFollowTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_add, 0, 0, 0);
        }
    }

    public interface onUserListener {
        public void onFollowClicked(int position);
    }
}
