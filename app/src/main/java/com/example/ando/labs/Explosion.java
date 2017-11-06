package com.example.ando.labs;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Ando on 06/11/2017.
 */

public class Explosion {
    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private Sound soundPlayer = new Sound();

    public Explosion(Bitmap res, int _x, int _y, int w, int h, int numFrames) {
        x = _x;
        y = _y;
        width = w;
        height = h;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for (int i = 0; i < image.length; i ++) {
            if (i % 4 == 0 && i > 4)
                row++;
            image[i] = Bitmap.createBitmap(spritesheet, (i - (4 * row)) * width,
                                            row * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        soundPlayer.playFreq(262);
    }

    public void draw(Canvas canvas) {
        if (!animation.isPlayedOnce()) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }
    }

    public void update() {
        if (!animation.isPlayedOnce()) {
            animation.update();
        }
    }
}
