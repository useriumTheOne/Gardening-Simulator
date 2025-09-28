package com.example.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerSetupActivity extends AppCompatActivity {

    private EditText etPlayerName;
    private Button btnStartGame;
    private ScheduledExecutorService scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkExactAlarmPermission()) return;
        File file = new File(getFilesDir(), "player.dat");
        if (file.exists())
        {
            PlayerDataManager.loadPlayer(this, "Default");
            startFirebaseUpdater();
            openMainGame();
            finish();
            return;
        }

        setContentView(R.layout.activity_player_setup);

        etPlayerName = findViewById(R.id.etPlayerName);
        btnStartGame = findViewById(R.id.btnStartGame);

        btnStartGame.setOnClickListener(v -> {
            String name = etPlayerName.getText().toString().trim();
            if (name.isEmpty())
            {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            Player.getInstance(name);
            startFirebaseUpdater();
            openMainGame();
            finish();
        });
    }
    private boolean checkExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!getSystemService(android.app.AlarmManager.class).canScheduleExactAlarms()) {
                Toast.makeText(this, "You must allow exact alarms to use this app", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                finish();
                return false;
            }
        }
        return true;
    }
    private void startFirebaseUpdater() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Player player = Player.getInstance();
            User user = new User(player.getName(), player.getMoney());
            FirebaseManager.updateUser(user);
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void openMainGame() {
        Intent i = new Intent(this, GardenActivity.class); // your main game screen
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayerDataManager.savePlayer(this);
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
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
