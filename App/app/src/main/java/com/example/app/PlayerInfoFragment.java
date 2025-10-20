package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoFragment extends Fragment {

    private TextView tvPlayerName, tvPlayerMoney;
    private Button btn;
    private static List<PlayerInfoFragment> instances = new ArrayList<PlayerInfoFragment>();;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_info, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instances.add(this);
        tvPlayerName = view.findViewById(R.id.tvPlayerName);
        tvPlayerMoney = view.findViewById(R.id.tvPlayerMoney);
        btn = view.findViewById(R.id.btnLogoff);
        btn.setOnClickListener(View -> {
            PlayerDataManager.deletePlayer(view.getContext());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPlayerInfo();
    }

    public void refreshPlayerInfo() {
        if (tvPlayerName == null || tvPlayerMoney == null) return;
        Player player = Player.getInstance();
        tvPlayerName.setText("Name: " + player.getName());
        tvPlayerMoney.setText("Money: $" + player.getMoney());
    }
    public static void updateUI()
    {
        for (PlayerInfoFragment  instance : instances)
        {
            instance.refreshPlayerInfo();
        }

    }
}
