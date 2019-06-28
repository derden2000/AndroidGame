package ru.geekbrains.android;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AudioService extends Service {
    MediaPlayer player;

    public AudioService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        player.start();
    }

    @Override
    public void onDestroy() {
        player.stop();
    }
}
