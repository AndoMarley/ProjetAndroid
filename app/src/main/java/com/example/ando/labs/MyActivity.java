package com.example.ando.labs;

/**
 * Created by Ando Randriamaro and Brice Maroson on 12/10/2017.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

public class MyActivity extends Activity {
    // Identifiant de la boîte de dialogue de victoire
    public static final int VICTORY_DIALOG = 0;
    // Identifiant de la boîte de dialogue de défaite
    public static final int DEFEAT_DIALOG = 1;
    // Le moteur physique du jeu
    private MoteurDeJeu mEngine = null;
    // Le moteur graphique du jeu
    private PanelDeJeu mView = null;

    private boolean pause = false;

    private double latitude;
    private double longitude;

    private LocationManager mLocationManager;

    private MediaPlayer mPlayer = null;

    protected PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //initialisation
        mView = new PanelDeJeu(this);
        setContentView(mView);

        mEngine = new MoteurDeJeu(this);
//        Bloc but = new Bloc(Bloc.Type.TARGET,0,0);
        Boule b = new Boule();
        mView.setBoule(b);
        mEngine.setBoule(b);

        List<Bloc> mList = mEngine.getBoardElems();
        mView.setBlocks(mList);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        playSound(R.raw.kung);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, getPackageName());
        this.mWakeLock.acquire();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!pause)
            mEngine.resume();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onPause() {
        super.onPause();
        mEngine.stop();
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        //savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        //savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case VICTORY_DIALOG:
                builder.setCancelable(false);
                builder.setMessage("Victoire !");
                builder.setTitle(" Feliz !!");
                builder.setPositiveButton("Recommencer", new OkOnClickListener());
                builder.setNegativeButton("Quitter", new CancelOnClickListener());

                break;

            case DEFEAT_DIALOG:
                builder.setCancelable(false);
                builder.setMessage("Game over");
                builder.setTitle("La loose !");
                builder.setPositiveButton("Recommencer", new OkOnClickListener());
                builder.setNegativeButton("Quitter", new CancelOnClickListener());
                pause = true;
        }
        return builder.create();
    }

    @Override
    public void onPrepareDialog(int id, Dialog box) {
        // A chaque fois qu'une boîte de dialogue est lancée, on arrête le moteur physique
        mEngine.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveScore();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    public MoteurDeJeu getEngine() {
        return mEngine;
    }

    public void saveScore() {
        SharedPreferences settings = getSharedPreferences("SCORE", Context.MODE_PRIVATE);

        String score = settings.getString("maxScore", "0");

        if (Integer.parseInt(score) < mEngine.getBoule().getScore()) {

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("maxScore", "" + mEngine.getBoule().getScore());

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            editor.putString("latitude", "" + location.getLatitude());
            editor.putString("longitude", "" + location.getLongitude());

            editor.commit();
        }
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            mEngine.stop();
            finish();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            mEngine.stop();
            mEngine.resetTarget();
            pause = false;
            mEngine.resume();
        }
    }

    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }

    public PanelDeJeu getView() {
        return mView;
    }
}
