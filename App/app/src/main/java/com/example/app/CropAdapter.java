package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder>
{

    private final List<Crop> crops;

    public CropAdapter(List<Crop> crops) {
        this.crops = crops;
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crop, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        Crop crop = crops.get(position);
        holder.tvType.setText(crop.getType());
        holder.tvBuy.setText("Buy: " + crop.getBuyPrice());
        holder.tvSell.setText("Sell: " + crop.getSellPrice());
        holder.tvTime.setText("Growth: " + crop.getGrowthTime() + "s");

        holder.btnBuy.setOnClickListener(v -> {
            Player player = Player.getInstance();
            Seed seed = new Seed(crop);
            if (player.buyItem(seed))
            {
                PlayerInfoFragment.updateUI();
                PlayerDataManager.savePlayer(v.getContext());
                Toast.makeText(v.getContext(),
                        "Bought " + crop.getType(),
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(v.getContext(),
                        "Not enough money!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }

    static class CropViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvBuy, tvSell, tvTime;
        Button btnBuy;
        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvCropType);
            tvBuy = itemView.findViewById(R.id.tvBuyPrice);
            tvSell = itemView.findViewById(R.id.tvSellPrice);
            tvTime = itemView.findViewById(R.id.tvGrowthTime);
            btnBuy = itemView.findViewById(R.id.btnBuy);
        }
    }
}
