package com.example.ando.labs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * Created by Ando on 05/11/2017.
 */

public class ScoreView extends View {

    String score;
    private Bitmap digits;

    public ScoreView(Context context, String _score) {
        super(context);
        digits = BitmapFactory.decodeResource(getResources(), R.drawable.digits);
        score = _score;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Bitmap imgDigit[] = {
                Bitmap.createBitmap(digits, 0, 0, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, digits.getWidth() / 4, 0, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, digits.getWidth() / 2, 0, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, 3 * digits.getWidth() / 4, 0, digits.getWidth() / 4, digits.getHeight() / 4),

                Bitmap.createBitmap(digits, 0, digits.getHeight() / 4, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, digits.getWidth() / 4, digits.getHeight() / 4, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, digits.getWidth() / 2, digits.getHeight() / 4, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, 3 * digits.getWidth() / 4, digits.getHeight() / 4, digits.getWidth() / 4, digits.getHeight() / 4),

                Bitmap.createBitmap(digits, 0, digits.getHeight() / 2, digits.getWidth() / 4, digits.getHeight() / 4),
                Bitmap.createBitmap(digits, digits.getWidth() / 4, digits.getHeight() / 2, digits.getWidth() / 4, digits.getHeight() / 4),
        };

        int leftPadding = 10;
        int topPadding = 10;

        int tab[] = new int[4];

        int j = 3;
        for (int i = score.length() - 1; i >= 0; i--) {
            tab[j] = score.charAt(i) - '0';
            j--;
        }

        while (j > 0) {
            tab[j] = 0;
            j--;
        }

        canvas.drawBitmap(imgDigit[tab[0]], leftPadding, topPadding, null);
        canvas.drawBitmap(imgDigit[tab[1]], leftPadding + digits.getWidth() / 4, topPadding, null);
        canvas.drawBitmap(imgDigit[tab[2]], leftPadding + digits.getWidth() / 2, topPadding, null);
        canvas.drawBitmap(imgDigit[tab[3]], leftPadding + 3 * digits.getWidth() / 4, topPadding, null);

        //topPadding = digits.getHeight() / 4;

        //canvas.drawBitmap(imgDigit[5], leftPadding, topPadding, null);
        //canvas.drawBitmap(imgDigit[6], leftPadding + digits.getWidth() / 4, topPadding, null);
        //canvas.drawBitmap(imgDigit[7], leftPadding + digits.getWidth() / 2, topPadding, null);
        //canvas.drawBitmap(imgDigit[8], leftPadding + 3 * digits.getWidth() / 4, topPadding, null);
    }
}
