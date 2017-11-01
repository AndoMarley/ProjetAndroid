package com.example.ando.labs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button jouer;
    public Button score;
    public Button quit;
    private View.OnClickListener jouerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, MyActivity.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener scoreListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent statIntent = new Intent(MainActivity.this, ScoreActivity.class);
            startActivity(statIntent);

        }
    };
    private View.OnClickListener quitListener = new View.OnClickListener() {
        public void onClick(View v) {
            finish();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jouer = (Button) findViewById(R.id.play);
        score = (Button) findViewById(R.id.stat);
        quit = (Button) findViewById(R.id.quit);

        jouer.setOnClickListener(jouerListener);
        score.setOnClickListener(scoreListener);
        quit.setOnClickListener(quitListener);
    }

}
