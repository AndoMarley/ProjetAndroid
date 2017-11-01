package com.example.ando.labs;

/**
 * Created by Ando on 12/10/2017.
 */

import android.graphics.RectF;

public class Bloc {
    private float SIZE = Boule.RAYON * 2;
    private float COTE = Boule.Cote * 2;
    private Type typeBloc = null;
    private RectF mRectangle = null;

    public Bloc(Type pType, int pX, int pY) {
        this.typeBloc = pType;
        this.mRectangle = new RectF(pX * COTE, pY * COTE, (pX + 1) * COTE, (pY + 1) * COTE);
    }

    public Type getType() {
        return typeBloc;
    }

    public RectF getRectangle() {

        return mRectangle;
    }

    enum Type {TROU, DEPART, TARGET}
}

