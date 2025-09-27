package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayerInfoFragment extends Fragment {

    private TextView tvPlayerName, tvPlayerMoney;
    private static PlayerInfoFragment instance;

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
        instance = this;
        tvPlayerName = view.findViewById(R.id.tvPlayerName);
        tvPlayerMoney = view.findViewById(R.id.tvPlayerMoney);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPlayerInfo();
    }

    public void refreshPlayerInfo() {
        Player player = Player.getInstance();
        tvPlayerName.setText("Name: " + player.getName());
        tvPlayerMoney.setText("Money: $" + player.getMoney());
    }
    public static void updateUI()
    {
        if (instance != null) {
            instance.refreshPlayerInfo();
        }
    }
}
