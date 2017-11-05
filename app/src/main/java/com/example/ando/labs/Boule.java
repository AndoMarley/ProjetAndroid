package com.example.ando.labs;

/**
 * Created by Ando on 12/10/2017.
 */

import android.graphics.Color;
import android.graphics.RectF;

public class Boule {
    // Rayon de la boule
    public static final int RAYON = 25; // rayon boule
    public static final int Cote = 30; // Coté trou
    // Vitesse maximale autorisée pour la boule
    private static final float MAX_SPEED = 20.0f;
    // Permet à la boule d'accélérer moins vite
    private static final float COMPENSATEUR = 8.0f;
    // Utilisé pour compenser les rebonds
    private static final float REBOND = 1.3f; // reglage difficulté (valeur bas, vitesse élevé)
    public int xB;
    public int yB;
    // Couleur de la boule
    private int mCouleur = Color.rgb(243, 175, 22);
    // Le rectangle qui correspond à la position de départ de la boule
    private RectF mInitialRectangle = null;
    // Le rectangle de collision
    private RectF mRectangle = null;
    // Coordonnées en x
    private float mX = 1;
    // Coordonnées en y
    private float mY = 1;
    // Vitesse sur l'axe x
    private float mSpeedX = 0;
    // Vitesse sur l'axe y
    private float mSpeedY = 0;
    // Taille de l'écran en hauteur
    private int mHeight = 0;
    // Taille de l'écran en largeur
    private int mWidth = 0;
    private int life = 3;
    private int score = 0;



    public Boule() {
        mRectangle = new RectF();
    }

    public int getCouleur() {
        return mCouleur;
    }

    // A partir du rectangle initial on détermine la position de la boule
    public void setInitialRectangle(RectF pInitialRectangle) {
        this.mInitialRectangle = pInitialRectangle;
        // this.mX = pInitialRectangle.left + Cote;
        // this.mY = pInitialRectangle.top + Cote;
    }

    public float getX() {

        return mX;
    }

    public void setPosX(float pPosX) {
        mX = pPosX;

        // Si la boule sort du cadre, on rebondit
        if (mX < RAYON) {
            mX = RAYON;
            // Rebondir, c'est changer la direction de la balle
            mSpeedY = -mSpeedY / REBOND;//-
        } else if (mX > mWidth - RAYON) {
            mX = mWidth - RAYON;
            mSpeedY = -mSpeedY / REBOND;// reglage du rebond quand la boule touche extremite largeur
        }
    }

    public float getY() {
        return mY;
    }

    public void setPosY(float pPosY) {

        mY = pPosY;
        if (mY < RAYON) {
            mY = RAYON;
            mSpeedX = -mSpeedX / REBOND;//-
        } else if (mY > mHeight - RAYON) {
            mY = mHeight - RAYON;
            mSpeedX = -mSpeedX / REBOND;//-
        }
    }

    // Utilisé quand on rebondit sur les murs horizontaux
    public void changeXSpeed() {
        mSpeedX = -mSpeedX;
    }

    // Utilisé quand on rebondit sur les murs verticaux
    public void changeYSpeed() {
        mSpeedY = -mSpeedY;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setHeight(int pHeight) {
        this.mHeight = pHeight;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setWidth(int pWidth) {
        this.mWidth = pWidth;
    }

    // Mettre à jour les coordonnées de la boule
    public RectF putXAndY(float pX, float pY) {
        mSpeedX += pX / COMPENSATEUR;
        if (mSpeedX > MAX_SPEED)
            mSpeedX = MAX_SPEED;
        if (mSpeedX < -MAX_SPEED)
            mSpeedX = -MAX_SPEED;

        mSpeedY += pY / COMPENSATEUR;
        if (mSpeedY > MAX_SPEED)
            mSpeedY = MAX_SPEED;
        if (mSpeedY < -MAX_SPEED)
            mSpeedY = -MAX_SPEED;

        setPosX(mX + mSpeedY);
        setPosY(mY + mSpeedX);

        float xB = mX + RAYON;
        float yB = mY + RAYON;

        // Met à jour les coordonnées du rectangle de collision
        mRectangle.set(mX - RAYON, mY - RAYON, xB, yB);

        return mRectangle;
    }

    // Remet la boule à sa position de départ
    public void reposit() {
        mSpeedX = 0;
        mSpeedY = 0;

        int xnx = 20+(int) (Math.random() * 6);
        int yny = (int) (Math.random() * 6);
        this.mX = 20;//xnx;//mWidth / 2;//mInitialRectangle.left + RAYON;
        this.mY = 12;//7yny; //mHeight / 2;//mInitialRectangle.top + RAYON;
    }

    public void incrementScore() {
        score++;
    }

    public void decrementLife() {
        life--;
    }

    public int getLife() {
        return life;
    }

    public void resetLife() {
        life = 3;
    }
    public int getScore() {
        return score;
    }

    public void resetScore(){
        score = 0;
    }
}
