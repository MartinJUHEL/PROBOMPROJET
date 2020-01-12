package com.martin.promob;

import android.app.Activity;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;

import com.martin.promob.model.EscapeView;

public class EscapeActivity extends Activity {
    private EscapeView escapeView;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        LoginActivity.appTheme.stop();
        LoginActivity.appTheme.release();
        music = MediaPlayer.create(getApplicationContext(), R.raw.faucon);
        music.setLooping(true);
        music.start();

        // On cré un objet "GameView" qui est le code principal du jeu
        escapeView = new EscapeView(this, size.x, size.y);

        // et on l'affiche.
        setContentView(escapeView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
        music.release();
    }
}
