package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private RecyclerView rvLeaderboard;
    private LeaderboardAdapter adapter;
    private List<User> leaderboardList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rvLeaderboard = findViewById(R.id.rvLeaderboard);
        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeaderboardAdapter(leaderboardList);
        rvLeaderboard.setAdapter(adapter);

        Button btnGarden = findViewById(R.id.btnGarden);
        Button btnShop = findViewById(R.id.btnShop);

        btnGarden.setOnClickListener(v -> {
            Intent i = new Intent(LeaderboardActivity.this, GardenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        btnShop.setOnClickListener(v -> {
            Intent i = new Intent(LeaderboardActivity.this, ShopActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        new Thread(() -> {
            List<User> users = FirebaseManager.getUsers();
            runOnUiThread(() -> {
                leaderboardList.clear();
                leaderboardList.addAll(users);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
}
