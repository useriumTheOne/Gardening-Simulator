package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private CropAdapter adapter;
    private List<Crop> cropList = new ArrayList<>();
    private RecyclerView rvCrops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        rvCrops = findViewById(R.id.rvCrops);
        rvCrops.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CropAdapter(cropList);
        rvCrops.setAdapter(adapter);

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
        new Thread(() -> {
            List<Crop> crops = FirebaseManager.getCrops();
            runOnUiThread(() -> {
                cropList.clear();
                cropList.addAll(crops);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }
}
