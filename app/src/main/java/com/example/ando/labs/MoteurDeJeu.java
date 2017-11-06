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

    private Boule mBoule = null;
    private List<Bloc> mBlocks = null;
    private List<Bloc> mObstacles = null;
    private Bloc mTarget = null;
    private MyActivity mActivity = null;
    private SensorManager mManager = null;
    private Sensor mAccelerometre = null;
    private Sound soundPlayer = new Sound();

    SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            float x = pEvent.values[0];
            float y = pEvent.values[1];

            if (mBoule != null) {
                // On met à jour les coordonnées de la boule
                RectF hitBox = mBoule.putXAndY(x, y);

                // Pour tous les blocs, iteration de la collection
                for (Bloc block : mBlocks) {
                    // On crée un nouveau rectangle pour ne pas modifier celui du bloc
                    RectF inter = new RectF(block.getRectangle());
                    if (inter.intersect(hitBox)) {
                        // On agit différement en fonction du type de bloc
                        switch (block.getType()) {
                            case TROU:
                                mActivity.getView().startExplosion((int) mBoule.getX(), (int) mBoule.getY());
                                mBoule.reposit();
                                if (mBoule.getLife() > 0) {
                                    mBoule.decrementLife();
                                    soundPlayer.playFreq(330);
                                }
                                if (mBoule.getLife() == 0) {
                                    mActivity.showDialog(MyActivity.DEFEAT_DIALOG);
                                    stop();
                                }

                                break;

                            case TARGET:
                                mBoule.incrementScore();
                                repositTarget();
                                addNewObstacle();
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

    public MoteurDeJeu(MyActivity pView) {
        mActivity = pView;
        mManager = (SensorManager) mActivity.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initBoard();
    }

    public Boule getBoule() {
        return mBoule;
    }

    public void setBoule(Boule pBoule) {

        this.mBoule = pBoule;
    }

    // Remet à zéro l'emplacement de la boule
    public void reset() {
        mBoule.reposit();

    }

    public void resetTarget(){
        mBoule.reposit();
        reinitObstacles();
        mBoule.resetLife();
        mBoule.resetScore();
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
    public List<Bloc> getBoardElems() {
        return mBlocks;
    }

    public List<Bloc> initObstacles (int n) {
        mObstacles = new ArrayList<Bloc>();
        for (int i = 0; i < n; i++) {
            int x = 1 + (int) (Math.random() * Constants.BOARD_WIDTH);
            int y = 1 + (int) (Math.random() * Constants.BOARD_HEIGHT);
            mObstacles.add(new Bloc(Type.TROU, x, y));
        }
        return mObstacles;
    }

    public void reinitObstacles () {
        mBlocks.clear();
        mObstacles.clear();
        for (int i = 0; i < Constants.INITIAL_NUM_OBSTACLES; i++) {
            int x = 1 + (int) (Math.random() * Constants.BOARD_WIDTH);
            int y = 1 + (int) (Math.random() * Constants.BOARD_HEIGHT);
            mObstacles.add(new Bloc(Type.TROU, x, y));
        }
        mBlocks.add(mTarget);
        mBlocks.addAll(mObstacles);
    }

    public void repositObstacles() {
        for (Bloc obstacle : mObstacles) {
            int x = 1 + (int) (Math.random() * Constants.BOARD_WIDTH);
            int y = 1 + (int) (Math.random() * Constants.BOARD_HEIGHT);
            obstacle.reposit(x, y);
        }
    }

    private void repositTarget() {
        int x = 1 + (int) (Math.random() * Constants.BOARD_WIDTH);
        int y = 1 + (int) (Math.random() * Constants.BOARD_HEIGHT);
        mTarget.reposit(x, y);
    }

    private void addNewObstacle() {
        int x = 1 + (int) (Math.random() * Constants.BOARD_WIDTH);
        int y = 1 + (int) (Math.random() * Constants.BOARD_HEIGHT);
        Bloc obstacle = new Bloc(Type.TROU, x, y);
        mBlocks.add(obstacle);
        mObstacles.add(obstacle);
    }

    private void initBoard() {
        mBlocks = new ArrayList<Bloc>();
        mBlocks.addAll(initObstacles(Constants.INITIAL_NUM_OBSTACLES));

        int x = 1 + (int) (Math.random() * 20);
        int y = 1 + (int) (Math.random() * 15);
        mTarget = new Bloc(Type.TARGET, x, y);

        mBlocks.add(mTarget);
    }

}
