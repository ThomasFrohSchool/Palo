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

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.palo.palo.model.User;
import com.palo.palo.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.palo.palo.volley.ServerURLs.PICS;
import static com.palo.palo.volley.ServerURLs.USER_BY_ID;

/**
 * Adapter for user search recyclerview.
 */
public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<User> searchResults;
    int selectedIndex = -1;
    Context context;
    private onUserListener onUserListener;
    User user;
    List<Integer> userFollowing;

    public UserSearchAdapter(Context context, List<User> searchResults, onUserListener onUserListener) {
        user = SharedPrefManager.getInstance(context).getUser();
        this.inflater = LayoutInflater.from(context);
        this.searchResults = searchResults;
        this.context = context;
        this.onUserListener = onUserListener;
        getFollowing();
        //user.setUserFollowing(userFollowing);
        //System.out.println(user.getUserFollowing());
        //getFollowing();
        //user.setUserFollowing(userFollowing);
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
        //getFollowing();
        //user.setUserFollowing(getFollowing());
        holder.toggleFollow(searchResults.get(position).getIsFollower());
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
            //getFollowing();
            toggleFollow(searchResults.get(pos).getIsFollower());
            if(searchResults.get(pos).getIsFollower()) {
                onUserListener.onAddFollowClicked(pos);
                searchResults.get(pos).setIsFollower();
            }
            else {
                onUserListener.onRemoveFollowClicked(pos);
                searchResults.get(pos).setIsFollower();
            }
            //onUserListener.onAddFollowClicked(pos);
            notifyItemChanged(pos);
        }

        public void toggleFollow(boolean isFollowing) {
            if(isFollowing) {
                userFollowTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_close, 0, 0, 0);
                userFollowTV.setText("Unfollow");
            }
            else {
                userFollowTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_add, 0, 0, 0);
                userFollowTV.setText("Follow");
            }
        }
    }

    /*public boolean getIsFollowing(int id) {
        //int pos;
        for(int i = 0; i < userFollowing.size(); i++) {
            if(userFollowing.get(i) == id) {
                return true;
            }
        }
        return false;
    }*/

    /*public boolean toggleIsFollowing(int id) {
        //int pos;
        for(int i = 0; i < userFollowing.size(); i++) {
            if(userFollowing.get(i) == id) {
                userFollowing.remove(i);

                return false;
            }
        }
        userFollowing.add(id);
        return true;
    }*/

    public interface onUserListener {
        public String onAddFollowClicked(int position);
        public String onRemoveFollowClicked(int position);
    }

    private void getFollowing() {
        ArrayList<Integer> u = new ArrayList<>();

        JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, USER_BY_ID + user.getId(), null,
                response -> {
                    try {
                        for(int i = 0; i < response.getJSONArray("following").length(); i++) {
                            //Integer f = following.getInt(i);
                            u.add(response.getJSONArray("following").getInt(i));
                        }
                        user.setUserFollowing(u);
                        userFollowing = u;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        VolleySingleton.getInstance(context).addToRequestQueue(j);
    }
}
