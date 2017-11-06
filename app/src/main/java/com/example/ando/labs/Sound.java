package com.example.ando.labs;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.JetPlayer;
import android.os.Handler;

/**
 * Created by Ando on 06/11/2017.
 */

public class Sound {
    private final int duration = 1; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final byte generatedSnd[] = new byte[2 * numSamples];
    Handler handler = new Handler();
    private double freqOfTone;
    private Context context;

    public void playFreq(int freq) {
        freqOfTone = freq;
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                genTone();
                handler.post(new Runnable() {

                    public void run() {
                        playSound();
                    }
                });
            }
        });
        thread.start();
    }

    public void playJet(String jet) {
        JetPlayer jetPlayer = JetPlayer.getJetPlayer();
        jetPlayer.loadJetFile(context.getResources().openRawResourceFd(R.raw.gover));
        byte segmentId = 0;

        // queue segment 5, repeat once, use General MIDI, transpose by -1 octave
        jetPlayer.queueJetSegment(5, -1, 1, -1, 0, segmentId++);
        jetPlayer.play();
    }

    private void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    private void playSound() {
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

    public void setContext(Context c) {
        context = c;
    }
}
