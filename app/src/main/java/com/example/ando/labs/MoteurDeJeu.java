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
    public Bloc t1;
    public Bloc tn;
    public int x;
    public int y;
    private double vie = 5;
    private Boule mBoule = null;
    // les blocks
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

                // Pour tous les blocs
                for (Bloc block : mBlocks) {
                    // On crée un nouveau rectangle pour ne pas modifier celui du bloc
                    RectF inter = new RectF(block.getRectangle());
                    if (inter.intersect(hitBox)) {
                        // On agit différement en fonction du type de bloc
                        switch (block.getType()) {
                            case TROU:

                                mActivity.showDialog(MyActivity.DEFEAT_DIALOG);

                                xpos = mBoule.getX();
                                ypos = mBoule.getY();

                                break;

                            case DEPART:
                                break;

                            case TARGET:

                                mActivity.showDialog(MyActivity.VICTORY_DIALOG);
//                                mBlocks.remove(t1);
//                                t1 = new Bloc(Type.TARGET,15,10);
//                                mBlocks.add(tn);


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

    public Bloc getBloc() {
        return (Bloc) mBlocks;
    }

    public void setBloc(Bloc pBloc) {
        this.mBlocks = (List<Bloc>) pBloc;
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
        mBlocks.removeAll(mBlocks);

    }

    // Arrête le capteur
    public void stop() {
        mManager.unregisterListener(mSensorEventListener, mAccelerometre);
    }

    // Redémarre le capteur
    public void resume() {
        mManager.registerListener(mSensorEventListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
    }

    // Construit les pieges
    public List<Bloc> pieges() {
        mBlocks = new ArrayList<Bloc>();

        // xmax== 15 ymax == 8
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);

        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);
        mBlocks.add(new Bloc(Type.TROU, x, y));
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);

        Bloc b = new Bloc(Type.DEPART, x, y);

        mBoule.setInitialRectangle(new RectF(b.getRectangle()));
        mBlocks.add(b);
        x = 1 + (int) (Math.random() * 29);
        y = 1 + (int) (Math.random() * 15);

        t1 = new Bloc(Type.TARGET, x, y);
        mBlocks.add(t1);



        return mBlocks;
    }

}