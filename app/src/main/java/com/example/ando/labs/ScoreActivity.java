package com.example.ando.labs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences settings = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
        String score = settings.getString("maxScore", "0");

        RelativeLayout scoreLayout = new RelativeLayout(this);

        ScoreView scoreView = new ScoreView(this, score);
        scoreView.setId(ViewIdGenerator.generateViewId());

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250);
        relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        ImageButton mapButton = new ImageButton(this);
        mapButton.setImageResource(R.drawable.button_location);
        mapButton.setBackgroundColor(Color.TRANSPARENT);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        mapButton.setLayoutParams(relativeParams);

        scoreLayout.addView(scoreView);
        scoreLayout.addView(mapButton, relativeParams);

        setContentView(scoreLayout);
    }
}
