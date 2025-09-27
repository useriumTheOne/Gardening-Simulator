package com.example.app;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GardenAdapter extends RecyclerView.Adapter<GardenAdapter.PlotViewHolder> {

    private final Garden garden;
    private final Player player;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public GardenAdapter(Garden garden, Player player) {
        this.garden = garden;
        this.player = player;

        // Start live update
        handler.post(updateRunnable);
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            notifyDataSetChanged();
            handler.postDelayed(this, 1000);
        }
    };

    @NonNull
    @Override
    public PlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plot, parent, false);
        return new PlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlotViewHolder holder, int position) {
        int row = position / 5;
        int col = position % 5;

        PlantedCrop pc = garden.getPlantedCrop(row, col);

        if (pc == null) {
            holder.tvPlot.setText("");
            holder.progressGrowth.setVisibility(View.GONE);
        } else {
            double totalTime = pc.getCrop().getGrowthTime();
            int elapsed = (int) pc.getElapsedSeconds();
            double remaining = Math.max(totalTime - elapsed, 0);

            holder.tvPlot.setText(pc.getCrop().getType() + "\n" + remaining + "s");

            int progress = (int) ((elapsed * 100L) / totalTime);
            progress = Math.min(progress, 100);
            holder.progressGrowth.setProgress(progress);
            holder.progressGrowth.setVisibility(progress < 100 ? View.VISIBLE : View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (pc == null) {
                showSeedSelection(holder.itemView.getContext(), row, col);
            } else if (pc.isGrown()) {
                // Harvest
                if (player.harvestCrop(row, col)) {
                    notifyItemChanged(position);
                    PlayerInfoFragment.updateUI();
                    Toast.makeText(holder.itemView.getContext(),
                            "Harvested " + pc.getCrop().getType(),
                            Toast.LENGTH_SHORT).show();
                    PlayerDataManager.savePlayer(v.getContext());
                }
            } else {
                Toast.makeText(holder.itemView.getContext(),
                        "Crop is still growing!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSeedSelection(Context context, int row, int col) {
        List<Item> seeds = new ArrayList<>();
        for (Item item : player.getInventory().getItems())
        {
            if (item instanceof Seed) seeds.add(item);
        }

        if (seeds.isEmpty()) {
            Toast.makeText(context, "No seeds in inventory", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] seedNames = seeds.stream()
                .map(seed -> seed.getName() + " x" + player.getInventory().getQuantity(seed))
                .toArray(String[]::new);

        new AlertDialog.Builder(context)
                .setTitle("Select Seed")
                .setItems(seedNames, (dialog, which) -> {
                    Seed selectedSeed = (Seed) seeds.get(which);
                    if (player.plantSeed(row, col, selectedSeed)) {
                        notifyItemChanged(row * 5 + col);
                        PlayerInfoFragment.updateUI();
                        Toast.makeText(context,
                                "Planted " + selectedSeed.getCrop().getType(),
                                Toast.LENGTH_SHORT).show();
                        PlayerDataManager.savePlayer(context);
                    } else {
                        Toast.makeText(context,
                                "Cannot plant seed", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return 25; // 5x5 grid
    }

    static class PlotViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlot;
        ProgressBar progressGrowth;

        public PlotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlot = itemView.findViewById(R.id.tvPlot);
            progressGrowth = itemView.findViewById(R.id.progressGrowth);
        }
    }
}
