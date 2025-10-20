package com.example.app;

import android.app.Activity;
import android.content.Context;
import java.io.*;

public class PlayerDataManager {

    private static final String FILE_NAME = "player.dat";

    public static void savePlayer(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(Player.getInstance());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deletePlayer(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        if (context instanceof Activity) {
            ((Activity) context).finishAffinity();
        }
        System.exit(0);
    }


    public static void loadPlayer(Context context, String defaultName) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            Player.getInstance(defaultName);
            return;
        }

        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Player loaded = (Player) ois.readObject();
            Player.setInstanceFromLoad(loaded);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Player.getInstance(defaultName);
        }
    }
}
