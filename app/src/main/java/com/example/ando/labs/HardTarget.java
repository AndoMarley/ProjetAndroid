package com.example.ando.labs;

import android.graphics.RectF;

/**
 * Created by Ando on 03/11/2017.
 */

public class HardTarget {

    public RectF htrect = null;
    private float COTE = Boule.Cote * 2;

    public HardTarget(int sx, int sy) {

        this.htrect = new RectF(sx * COTE, sy * COTE, (sx + 1) * COTE, (sy + 1) * COTE);

    }

    public RectF getHtrect() {
        return htrect;
    }

    public void htReset() {

    }
}
