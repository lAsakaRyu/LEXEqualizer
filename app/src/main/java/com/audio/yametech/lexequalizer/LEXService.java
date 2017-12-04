package com.audio.yametech.lexequalizer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.audiofx.AudioEffect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LEXService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    public LEXService() {
    }
    private final BroadcastReceiver mAudioSessionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int sessionId = intent.getIntExtra(AudioEffect.EXTRA_AUDIO_SESSION, 0);
            if (action.equals(AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION)) {
                Log.i("LEXEqualizer", String.format("Audio session open: %d", sessionId));
            }

            if (action.equals(AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION)) {
                Log.i("LEXEqualizer", String.format("Audio session closed: %d", sessionId));

            }
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return  mBinder;
    }
    public class LocalBinder extends Binder {

        public LEXService getService() {
            return LEXService.this;
        }
    }
    public void onCreate(){
        super.onCreate();
        IntentFilter audioSessionFilter = new IntentFilter();
        audioSessionFilter.addAction(AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION);
        audioSessionFilter.addAction(AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION);
        registerReceiver(mAudioSessionReceiver, audioSessionFilter);
    }
}
