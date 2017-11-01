package com.example.ando.labs;

/**
 * Created by Ando on 12/10/2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

public class MyActivity extends Activity {
    // Identifiant de la boîte de dialogue de victoire
    public static final int VICTORY_DIALOG = 0;
    // Identifiant de la boîte de dialogue de défaite
    public static final int DEFEAT_DIALOG = 1;

    // Le moteur graphique du jeu
    private PanelDeJeu mView = null;
    // Le moteur physique du jeu
    private MoteurDeJeu mEngine = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialisation
        mView = new PanelDeJeu(this);
        setContentView(mView);

        mEngine = new MoteurDeJeu(this);
        Boule b = new Boule();
        mView.setBoule(b);
        mEngine.setBoule(b);

        List<Bloc> mList = mEngine.pieges();
        mView.setBlocks(mList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEngine.resume();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onPause() {
        super.onStop();
        mEngine.stop();
    }

    @Override
    public Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case VICTORY_DIALOG:
                builder.setCancelable(false);
                builder.setMessage("Victoire !");
                builder.setTitle(" Feliz !!");
                builder.setPositiveButton("Recommencer", new OkOnClickListener());
                builder.setNegativeButton("Quitter", new CancelOnClickListener());

                break;

            case DEFEAT_DIALOG:
                builder.setCancelable(false);
                builder.setMessage("Game over");
                builder.setTitle("La loose !");
                builder.setPositiveButton("Recommencer", new OkOnClickListener());
                builder.setNegativeButton("Quitter", new CancelOnClickListener());
        }
        return builder.create();
    }

    @Override
    public void onPrepareDialog(int id, Dialog box) {
        // A chaque fois qu'une boîte de dialogue est lancée, on arrête le moteur physique
        mEngine.stop();
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            mEngine.stop();
            finish();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            mEngine.stop();
            mEngine.reset();
            mEngine.resume();
        }
    }
}
