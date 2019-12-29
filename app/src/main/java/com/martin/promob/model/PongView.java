package com.martin.promob.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.martin.promob.R;

import java.util.Random;

public class PongView extends SurfaceView implements Runnable {


    // This is our thread
    Thread mGameThread = null;

    // We need a SurfaceHolder object
    // We will see it in action in the draw method soon.
    SurfaceHolder mOurHolder;

    // A boolean which we will set and unset
    // when the game is running- or not
    // It is volatile because it is accessed from inside and outside the thread
    volatile boolean mPlaying;

    // Game is mPaused at the start
    boolean mPaused = true;

    // A Canvas and a Paint object
    Canvas mCanvas;
    Paint mPaint;

    // This variable tracks the game frame rate
    long mFPS;

    // The size of the screen in pixels
    int mScreenX;
    int mScreenY;

    // The players mBat
    PongBat mBat;

    // A mBall
    PongBall mBall;

    // For sound FX
    SoundPool sp;
    int beep1ID = -1;
    int beep2ID = -1;
    int beep3ID = -1;
    int loseLifeID = -1;

    //creer un fond
    private Bitmap bgImg;


    // The mScore
    int mScore = 0;

    // Lives
    int mLives = 3;

    /*
    When the we call new() on pongView
    This custom constructor runs
*/

    public PongView(Context context, int x, int y) {

        super(context);

        // Set the screen width and height
        mScreenX = x;
        mScreenY = y;

        // Initialize mOurHolder and mPaint objects
        mOurHolder = getHolder();
        mPaint = new Paint();

        // A new mBat
        mBat = new PongBat(mScreenX, mScreenY);

        // Create a mBall
        mBall = new PongBall(mScreenX, mScreenY);

        bgImg = selectBckground(this.getContext(),bgImg);

        //creer des sons en fonction de la version d'android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            sp = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }



            // Load our fx in memory ready for use
            beep1ID = sp.load(this.getContext(), R.raw.when, 0);
            beep2ID = sp.load(this.getContext(), R.raw.beep2, 0);
            beep3ID = sp.load(this.getContext(), R.raw.looser, 0);





        setupAndRestart();
    }


    public void setupAndRestart() {

        // Put the mBall back to the start
        mBall.reset(mScreenX, mScreenY/2);

        mPaused = true;

        // if game over reset scores and mLives
        if (mLives == 0) {
            endgame();
        }

    }

    public Bitmap selectBckground(Context context,Bitmap bgImg){
        //nombre aleatoire de 0 a 3
        final int random = new Random().nextInt(4);
        Bitmap bgSrc;

        if(random==0){
            //prendre un fond dans la librairie :
            bgSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.feuille0);
        }
        else if(random==1){
            bgSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.feuille1);

        }
        else if(random==2) {
            bgSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.feuille2);

        }
        else{
            bgSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.feuille3);

        }

        return Bitmap.createScaledBitmap(bgSrc, mScreenX, mScreenY, true);
    }

    @Override
    public void run() {
        while (mPlaying) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            // Update the frame
            if (!mPaused) {
                update();
            }

            // Draw the frame
            draw();

        /*
            Calculate the FPS this frame
            We can then use the result to
            time animations in the update methods.
        */
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                mFPS = 1000 / timeThisFrame;
            }

        }

    }

    // Everything that needs to be updated goes in here
    // Movement, collision detection etc.
    public void update() {

        // Move the mBat if required
        mBat.update(mFPS);

        mBall.update(mFPS);

        // Check for mBall colliding with mBat
        if (RectF.intersects(mBat.getRect(), mBall.getRect())) {
            mBall.setRandomXVelocity();
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mBat.getRect().top - 2);

            mScore++;
            mBall.increaseVelocity();

            sp.play(beep2ID, 1, 1, 0, 0, 1);
        }

        // Bounce the mBall back when it hits the bottom of screen
        if (mBall.getRect().bottom > mScreenY) {
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mScreenY - 8);

            // Lose a life
            mLives--;
            mBall.reset(mScreenX, mScreenY/2);
            mPaused = true;

            sp.play(beep3ID, 1, 1, 0, 0, 1);

            if (mLives == 0) {
                mPaused = true;
                endgame();
            }
        }

        // Bounce the mBall back when it hits the top of screen
        if (mBall.getRect().top < 0) {
            mBall.reverseYVelocity();
            mBall.clearObstacleY(48);
            //48

            sp.play(beep1ID, 1, 1, 0, 0, 1);
        }

        // If the mBall hits left wall bounce
        if (mBall.getRect().left < 0) {
            mBall.reverseXVelocity();
            mBall.clearObstacleX(2);


            sp.play(beep1ID, 1, 1, 0, 0, 1);
        }

        // If the mBall hits right wall bounce
        if (mBall.getRect().right > mScreenX) {
            mBall.reverseXVelocity();
            mBall.clearObstacleX(mScreenX - 22);


            sp.play(beep1ID, 1, 1, 0, 0, 1);
        }

    }


    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (mOurHolder.getSurface().isValid()) {

            // Draw everything here

            // Lock the mCanvas ready to draw
            mCanvas = mOurHolder.lockCanvas();

            mPaint.setTypeface(Typeface.DEFAULT_BOLD);


            // Clear the screen with my favorite color
            mCanvas.drawBitmap(bgImg, 0, 0, null);

            // Choose the brush color for drawing
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            // Draw the mBat
            mCanvas.drawRect(mBat.getRect(), mPaint);


            mPaint.setColor(Color.argb(255,237, 0, 0));
            // Draw the mBall
            mCanvas.drawRect(mBall.getRect(), mPaint);


            // Mettre en blanc les vies et le score
            mPaint.setColor(Color.argb(255, 255, 0, 0));

            // Draw the mScore
            mPaint.setTextSize(40);
            mCanvas.drawText("Score: " + mScore + "   Vies: " + mLives, 10, 50, mPaint);

            // Draw everything to the screen
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    // If the Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If the Activity starts/restarts
    // start our thread.
    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    //prendre en compte le doigt du joueur
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_MOVE:

                mPaused = false;


                mBat.setMovementState((int) motionEvent.getX());

                break;

        }
        return true;
    }
    public void endgame(){
        final Activity act = (Activity) this.getContext();
        act.finish();

    }


}








