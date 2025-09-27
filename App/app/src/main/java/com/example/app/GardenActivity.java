package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GardenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);
        Button btnShop = findViewById(R.id.btnShop);
        Button btnLeaderboard = findViewById(R.id.btnLeaderboard);

        btnShop.setOnClickListener(v -> {
            Intent i = new Intent(GardenActivity.this, ShopActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        btnLeaderboard.setOnClickListener(v -> {
            Intent i = new Intent(GardenActivity.this, LeaderboardActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        RecyclerView rvGarden = findViewById(R.id.rvGarden);
        rvGarden.setLayoutManager(new GridLayoutManager(this, 5));
        rvGarden.setAdapter(new GardenAdapter(Player.getInstance().getGarden(), Player.getInstance()));
    }
}
