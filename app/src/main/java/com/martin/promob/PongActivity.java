package com.martin.promob;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import com.martin.promob.model.PongView;

public class PongActivity extends AppCompatActivity {

    // pongView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    PongView pongView;

    private MediaPlayer music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginActivity.appTheme.stop();
        LoginActivity.appTheme.release();

        music = MediaPlayer.create(getApplicationContext(), R.raw.siththeme);
        music.setLooping(true);
        music.setVolume(80,80);
        music.start();

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        // Initialize pongView and set it as the view
        pongView = new PongView(this, size.x, size.y-100);
        setContentView(pongView);

//        onResume();

    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();
        music.start();
        // Tell the pongView resume method to execute
        pongView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
        music.release();
        // Tell the pongView pause method to execute
        pongView.pause();
    }


}