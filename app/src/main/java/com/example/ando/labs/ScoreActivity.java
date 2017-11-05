package com.example.ando.labs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences settings = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
        String score = settings.getString("maxScore", "0");

        setContentView(new ScoreView(this, score));

        //TextView scoreText = (TextView) findViewById(R.id.score);
        //scoreText.setText(score);
    }
}
