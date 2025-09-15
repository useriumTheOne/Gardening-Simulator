package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Button btnGarden = findViewById(R.id.btnGarden);
        Button btnLeaderboard = findViewById(R.id.btnLeaderboard);

        btnGarden.setOnClickListener(v -> {
            Intent i = new Intent(ShopActivity.this, GardenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });


        btnLeaderboard.setOnClickListener(v -> {
            Intent i = new Intent(ShopActivity.this, LeaderboardActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
    }
}
