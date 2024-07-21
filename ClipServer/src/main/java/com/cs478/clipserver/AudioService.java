package com.cs478.clipserver;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AudioService extends Service {

    // MediaPlayer object to play audio clips
    private MediaPlayer mediaPlayer = new MediaPlayer();

    // Channel ID is a unique string for each notification channel
    private final String CHANNEL_ID = "AudioServiceChannel";
    // Notification ID is a unique int for each notification
    private final int NOTIFICATION_ID = 1;

    // Called when the service is created. Creates a notification channel and starts the service in the foreground.
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, getNotification());
    }

    // Creates a notification channel for the service
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Audio Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    // Returns a notification for the service
    private Notification getNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Audio Service")
                .setContentText("Ready to play audio")
                .setSmallIcon(R.drawable.ic_notification)
                .build();
    }

    // Called when the service is bound. Returns an IBinder object that allows clients to interact with the service.
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("AudioService", "onBind");
        return new IAudioService.Stub() {
            @Override
            public void play(int clipIndex) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                try {
                    // Play the audio clip with the given index
                    clipIndex++;
                    mediaPlayer.reset();
                    AssetFileDescriptor afd = getAssets().openFd("clip" + clipIndex + ".mp3");
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    Log.e("AudioService", "Error playing audio", e);
                }
            }


            @Override
            public void pause() {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }

            @Override
            public void stopPlayback() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer(); // Reinitialize the MediaPlayer if needed again.
                }
            }


            @Override
            public void stopService() {
                stopSelf();
            }

            @Override
            public void resume() {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        };
    }
    
    /**
        * Called when the service is being destroyed. Releases the media player resources if it is not null.
        */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
