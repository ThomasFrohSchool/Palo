package com.palo.palo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for user search recyclerview.
 */
public class UserSearchDMAdapter extends RecyclerView.Adapter<UserSearchDMAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<String> searchResults;
    int selectedIndex = -1;
    Context context;
    private onUserDMListener onUserListener;

    public UserSearchDMAdapter(Context context, List<String> searchResults, onUserDMListener onUserListener) {
        this.inflater = LayoutInflater.from(context);
        this.searchResults = searchResults;
        this.context = context;
        this.onUserListener = onUserListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_user_dm_layout,parent,false);
        return new UserSearchDMAdapter.ViewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userNameTV.setText(searchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void swapDataSet(List<String> users){
        this.searchResults = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTV;
        CardView card;
        UserSearchDMAdapter.onUserDMListener onUserListener;

        public ViewHolder(@NonNull View itemView, onUserDMListener onUserListener) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.dmSearchUserName);
            card = itemView.findViewById(R.id.card_search_user_dm);
            this.onUserListener = onUserListener;
            card.setOnClickListener(v -> onUserClicked(getAdapterPosition()));
        }

        public void onUserClicked(int pos) {
            onUserListener.onUserClicked(pos);
        }
    }

    public interface onUserDMListener {
        public void onUserClicked(int position);
    }
}
