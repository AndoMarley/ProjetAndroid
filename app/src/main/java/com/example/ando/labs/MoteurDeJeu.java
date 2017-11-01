package com.example.ando.labs;

/**
 * Created by Ando on 12/10/2017.
 */

import android.app.Service;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.ando.labs.Bloc.Type;

import java.util.ArrayList;
import java.util.List;

public class MoteurDeJeu {

    public double xpos;
    public double ypos;
    private double vie = 5;
    private Boule mBoule = null;
    // Le labyrinthe
    private List<Bloc> mBlocks = null;
    private MyActivity mActivity = null;
    SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            float x = pEvent.values[0];
            float y = pEvent.values[1];

            if (mBoule != null) {
                // On met à jour les coordonnées de la boule
                RectF hitBox = mBoule.putXAndY(x, y);

                // Pour tous les blocs du labyrinthe
                for (Bloc block : mBlocks) {
                    // On crée un nouveau rectangle pour ne pas modifier celui du bloc
                    RectF inter = new RectF(block.getRectangle());
                    if (inter.intersect(hitBox)) {
                        // On agit différement en fonction du type de bloc
                        switch (block.getType()) {
                            case TROU:
                                //vie--;
                                //if (vie==0){
                                mActivity.showDialog(MyActivity.DEFEAT_DIALOG);
                                // }
                                xpos = mBoule.getX();
                                ypos = mBoule.getY();

                                break;

                            case DEPART:
                                break;

                            case TARGET:

                                mActivity.showDialog(MyActivity.VICTORY_DIALOG);

                                break;
                        }
                        break;
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {

        }
    };
    private SensorManager mManager = null;
    private Sensor mAccelerometre = null;

    public MoteurDeJeu(MyActivity pView) {
        mActivity = pView;
        mManager = (SensorManager) mActivity.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public Boule getBoule() {

        return mBoule;
    }

    public void setBoule(Boule pBoule) {

        this.mBoule = pBoule;
    }

    // Remet à zéro l'emplacement de la boule
    public void reset() {
        mBoule.reset();
    }

    // Arrête le capteur
    public void stop() {
        mManager.unregisterListener(mSensorEventListener, mAccelerometre);
    }

    // Redémarre le capteur
    public void resume() {
        mManager.registerListener(mSensorEventListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
    }

    // Construit le labyrinthe
    public List<Bloc> pièges() {
        mBlocks = new ArrayList<Bloc>();

        // xmax== 15 ymax == 8

        mBlocks.add(new Bloc(Type.TROU, 3, 2));
        mBlocks.add(new Bloc(Type.TROU, 6, 10));
        mBlocks.add(new Bloc(Type.TROU, 7, 14));
        mBlocks.add(new Bloc(Type.TROU, 10, 0));
        mBlocks.add(new Bloc(Type.TROU, 13, 3));
        mBlocks.add(new Bloc(Type.TROU, 13, 16));
        mBlocks.add(new Bloc(Type.TROU, 19, 6));
        mBlocks.add(new Bloc(Type.TROU, 22, 4));
        mBlocks.add(new Bloc(Type.TROU, 30, 8));

        int x = 25 + (int) (Math.random() * (-1 - 75));
        int y = 100 + (int) (Math.random() * (-1 - 350));


        Bloc b = new Bloc(Type.DEPART, 23, 2);
        mBoule.setInitialRectangle(new RectF(b.getRectangle()));
        mBlocks.add(b);

        mBlocks.add(new Bloc(Type.TARGET, 8, 11));

        return mBlocks;
    }

}