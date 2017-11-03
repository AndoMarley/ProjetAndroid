package com.example.ando.labs;

/**
 * Created by Ando on 12/10/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class PanelDeJeu extends SurfaceView implements SurfaceHolder.Callback {

    // Variables

    Boule mBoule;
    SurfaceHolder mSurfaceHolder;
    DrawingThread mThread;
    Paint mPaint;
    Bitmap bcg;
    Bitmap trx;
    private List<Bloc> mBlocks = null;

    public PanelDeJeu(Context pContext) {
        super(pContext);
        bcg = BitmapFactory.decodeResource(getResources(), R.drawable.ga);
        trx = BitmapFactory.decodeResource(getResources(), R.drawable.trou);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mBoule = new Boule();
    }

    public Boule getBoule() {

        return mBoule;
    }

    public void setBoule(Boule pBoule) {
        this.mBoule = pBoule;
    }

    public List<Bloc> getBlocks() {
        return mBlocks;
    }

    public void setBlocks(List<Bloc> pBlocks) {
        this.mBlocks = pBlocks;
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        // Dessiner le fond de l'écran en premier
        //pCanvas.drawColor(Color.WHITE);
        pCanvas.drawBitmap(bcg, 0, 0, null);


        if (mBlocks != null) {
            // Dessiner tous les blocs du labyrinthe
            for (Bloc b : mBlocks) {
                switch (b.getType()) {
                    case DEPART:
                        mPaint.setColor(Color.RED);
                        break;
                    case TARGET:
                        mPaint.setColor(Color.argb(100, 161, 228, 221));
//                        Canvas c = getHolder().lockCanvas();
//                        c.drawBitmap(trx,0,0,null);
//                        getHolder().unlockCanvasAndPost(c);
                        break;
                    case TROU:
                        mPaint.setColor(Color.RED);
                        break;
                }
                pCanvas.drawRect(b.getRectangle(), mPaint);
            }
        }

        // Dessiner la boule
        if (mBoule != null) {
            mPaint.setColor(mBoule.getCouleur());
            pCanvas.drawCircle(mBoule.getX(), mBoule.getY(), Boule.RAYON, mPaint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight) {
        //
    }

    @Override
    public void surfaceCreated(SurfaceHolder pHolder) {
        mThread.keepDrawing = true;
        mThread.start();
        // Quand on crée la boule, on lui indique les coordonnées de l'écran
        if (mBoule != null) {
            this.mBoule.setHeight(getHeight());
            this.mBoule.setWidth(getWidth());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder pHolder) {
        mThread.keepDrawing = false;
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

    }

    private class DrawingThread extends Thread {
        boolean keepDrawing = true;

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            Canvas canvas;
            while (keepDrawing) {
                canvas = null;

                try {
                    canvas = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        onDraw(canvas);
                    }
                } finally {
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
