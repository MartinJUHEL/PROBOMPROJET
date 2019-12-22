package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.martin.promob.model.PongView;

public class PongActivity extends AppCompatActivity {

    // pongView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    PongView pongView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        System.out.println("DIMENSIONS : "+size.x+ size.y);

        // Initialize pongView and set it as the view
        pongView = new PongView(this, size.x, size.y-500);
        setContentView(pongView);

//        onResume();

    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the pongView resume method to execute
        pongView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the pongView pause method to execute
        pongView.pause();
    }



}