package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.UserViewHolder> {

    private final List<User> users;

    public LeaderboardAdapter(List<User> users)
    {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position)
    {
        User user = users.get(position);
        holder.tvName.setText(user.getName());
        holder.tvMoney.setText("$" + user.getMoney());
    }

    @Override
    public int getItemCount()
    {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvMoney;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvUserName);
            tvMoney = itemView.findViewById(R.id.tvUserMoney);
        }
    }
}
