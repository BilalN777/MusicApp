//package com.cs478.audioclient;
//
//import android.app.Activity;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.os.RemoteException;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//
//import com.cs478.clipserver.IAudioService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends Activity {
//    private IAudioService audioService;
//    private boolean isBound = false;
//    private ListView lvAudioClips;
//    private List<String> audioClips;
//    private final ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            audioService = IAudioService.Stub.asInterface(service);
//            Log.d("AudioClient", "Service connected");
//            isBound = true;
//            enableButtons();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            audioService = null;
//            Log.d("AudioClient", "Service Disconnected");
//            isBound = false;
//            enableButtons();
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        startServiceAndBind();
//        audioClips = new ArrayList<>();
//        audioClips.add("Clip 1");
//        audioClips.add("Clip 2");
//        audioClips.add("Clip 3");
//        audioClips.add("Clip 4");
//
//        lvAudioClips = findViewById(R.id.lvAudioClips);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, audioClips);
//        lvAudioClips.setAdapter(adapter);
//
//        Button btnStartService = findViewById(R.id.startServiceButton);
//        Button btnStopService = findViewById(R.id.stopServiceButton);
//        Button btnPlay = findViewById(R.id.btnPlay);
//        Button btnStop = findViewById(R.id.btnStop);
//        Button btnPause = findViewById(R.id.btnPause);
//
//        btnStartService.setOnClickListener(v -> startServiceAndBind());
//        btnStopService.setOnClickListener(v -> unbindAndStopService());
//
//        // Implement other buttons and their click listeners for play, pause, stopPlayback
//        btnPlay.setOnClickListener(v -> {
//            try {
//                audioService.play(1);
//            } catch (RemoteException e) {
//                Log.e("AudioClient", "Error playing audio", e);
//            }
//
//        });
//
//        btnPause.setOnClickListener(v -> {
//            try {
//                audioService.pause();
//            } catch (RemoteException e) {
//                Log.e("AudioClient", "Error pausing audio", e);
//            }
//        });
//
//        btnStop.setOnClickListener(v -> {
//            try {
//                audioService.stopPlayback();
//            } catch (RemoteException e) {
//                Log.e("AudioClient", "Error stopping audio", e);
//            }
//        });
//
//
//
//        lvAudioClips.setOnItemClickListener((parent, view, position, id) -> {
//            if (audioService != null && isBound) {
//                try {
//                    audioService.play(position);  // Assuming the position corresponds to the clip index
//                } catch (RemoteException e) {
//                    Log.e("AudioClient", "Error playing audio", e);
//                }
//            }
//        });
//    }
//
//    private void startServiceAndBind() {
//        Intent intent = new Intent("com.cs478.clipserver.ACTION_START_AUDIO_SERVICE");
//        intent.setPackage("com.cs478.clipserver");  // Ensure the intent is explicit
//
//        startService(intent);
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }
//
//
//    private void unbindAndStopService() {
//        if (isBound) {
//            unbindService(serviceConnection);
//            isBound = false;
//            Intent intent = new Intent();
//            intent.setComponent(new ComponentName("com.cs478.clipserver", "com.cs478.clipserver.AudioService"));
//            stopService(intent);
//        }
//    }
//
//    private void enableButtons() {
//        // Enable/disable buttons based on the service's bound state
//        Button btnPlay = findViewById(R.id.btnPlay);
//        Button btnPause = findViewById(R.id.btnPause);
//        Button btnStop = findViewById(R.id.btnStop);
//        Button btnUnbind = findViewById(R.id.stopServiceButton);
//
//        btnPlay.setEnabled(isBound);
//        btnPause.setEnabled(isBound);
//        btnStop.setEnabled(isBound);
//        btnUnbind.setEnabled(isBound);
//
//    }
//}
//

package com.cs478.audioclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs478.clipserver.IAudioService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private IAudioService audioService;
    private boolean isBound = false;
    private boolean isPlaying = false;
    private ListView lvAudioClips;
    private List<String> audioClips;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            audioService = IAudioService.Stub.asInterface(service);
            Log.d("AudioClient", "Service connected");
            isBound = true;
            updateButtons();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            audioService = null;
            Log.d("AudioClient", "Service Disconnected");
            isBound = false;
            updateButtons();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioClips = new ArrayList<>();
        audioClips.add("Eerie");
        audioClips.add("Cheery Violins");
        audioClips.add("Tavern");
        audioClips.add("Zen Garden");


        lvAudioClips = findViewById(R.id.lvAudioClips);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, audioClips);
        lvAudioClips.setAdapter(adapter);

        Button btnStartService = findViewById(R.id.startServiceButton);
        Button btnStopService = findViewById(R.id.stopServiceButton);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnResume = findViewById(R.id.btnResume);

        btnStartService.setOnClickListener(v -> startServiceAndBind());
        btnStopService.setOnClickListener(v -> unbindAndStopService());
        btnPlay.setOnClickListener(v -> playAudio());
        btnPause.setOnClickListener(v -> pauseAudio());
        btnResume.setOnClickListener(v -> resumeAudio());
        btnStop.setOnClickListener(v -> stopPlayback());

        lvAudioClips.setOnItemClickListener((parent, view, position, id) -> playAudioFromList(position));
        updateButtons();
    }

    private void startServiceAndBind() {
        Intent intent = new Intent("com.cs478.clipserver.ACTION_START_AUDIO_SERVICE");
        intent.setPackage("com.cs478.clipserver");
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindAndStopService() {
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.cs478.clipserver", "com.cs478.clipserver.AudioService"));
        stopService(intent);
        updateButtons();
    }

    private void playAudioFromList(int position) {
        if (audioService != null && isBound) {
            try {
                audioService.play(position);
                isPlaying = true;
                updateButtons();
            } catch (RemoteException e) {
                Log.e("AudioClient", "Error playing audio", e);
            }
        }
    }

    private void playAudio() {
        if (audioService != null && isBound) {
            try {
                audioService.play(1); // Play the first clip or manage clip index appropriately
                isPlaying = true;
                updateButtons();
            } catch (RemoteException e) {
                Log.e("AudioClient", "Error playing audio", e);
            }
        }
    }

    private void pauseAudio() {
        if (audioService != null && isBound && isPlaying) {
            try {
                audioService.pause();
                isPlaying = false;
                updateButtons();
            } catch (RemoteException e) {
                Log.e("AudioClient", "Error pausing audio", e);
            }
        }
    }

    private void resumeAudio() {
        if (audioService != null && isBound && !isPlaying) {
            try {
                audioService.resume();
                isPlaying = true;
                updateButtons();
            } catch (RemoteException e) {
                Log.e("AudioClient", "Error resuming audio", e);
            }
        }
    }

    private void stopPlayback() {
        if (audioService != null && isBound) {
            try {
                audioService.stopPlayback();
                isPlaying = false;
                updateButtons();
            } catch (RemoteException e) {
                Log.e("AudioClient", "Error stopping audio", e);
            }
        }
    }

    private void updateButtons() {
        // Logic to enable/disable buttons based on the state of isBound and isPlaying
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnResume = findViewById(R.id.btnResume);
        Button btnUnbind = findViewById(R.id.stopServiceButton);

        btnPlay.setEnabled(isBound && !isPlaying);
        btnPause.setEnabled(isBound && isPlaying);
        btnStop.setEnabled(isBound && isPlaying);
        btnResume.setEnabled(isBound && !isPlaying);
        btnUnbind.setEnabled(isBound);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(serviceConnection);
        }
    }
}
