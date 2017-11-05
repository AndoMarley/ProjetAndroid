package com.example.ando.labs;

/**
 * Created by Ando on 12/10/2017.
 */

import android.graphics.RectF;

public class Bloc {
    public Type typeBloc = null;
    public RectF mRectangle = null;
    public int ipX;
    public int ipY;
    private float COTE = Boule.Cote * 2;
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

    enum Type {TROU, TARGET}

    public void reposit(int x, int y) {
        ipX = x;
        ipY = y;
        this.mRectangle = new RectF(x * COTE, y * COTE, (x + 1) * COTE, (y + 1) * COTE);
    }
}
