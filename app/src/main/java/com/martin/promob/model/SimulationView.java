package com.martin.promob.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.martin.promob.R;

public class SimulationView extends View implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Display mDisplay;

    private float mSensorX;
    private float mSensorY;
    private float mSensorZ;
    private long mSensorTimeStamp;

    private Bitmap mBin;
    private Bitmap mPaper;
    private static final int BALL_SIZE = 32;
    private static final int HOLE_SIZE = 40;

    private float mXOrigin;
    private float mYOrigin;
    private float mHorizontalBound;
    private float mVerticalBound;

    private Particle mBallon = new Particle();


    public SimulationView(Context context) {
        super(context);

        //initialiser le capteur et l'affichage
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Bitmap paper = BitmapFactory.decodeResource(getResources(), R.drawable.papier);
        mPaper = Bitmap.createScaledBitmap(paper, BALL_SIZE, BALL_SIZE, true);

        Bitmap bin = BitmapFactory.decodeResource(getResources(), R.drawable.poubelle);
        mBin = Bitmap.createScaledBitmap(bin, HOLE_SIZE, HOLE_SIZE, true);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_0:
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                break;
            case Surface.ROTATION_90:
                mSensorX = -event.values[1];
                mSensorY = event.values[0];
                break;
            case Surface.ROTATION_180:
                mSensorX = -event.values[0];
                mSensorY = -event.values[1];
                break;
            case Surface.ROTATION_270:
                mSensorX = event.values[1];
                mSensorY = -event.values[0];
                break;
        }
        mSensorZ = event.values[2];
        mSensorTimeStamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mXOrigin = w * 0.5f;
        mYOrigin = h * 0.5f;

        mHorizontalBound = (w - BALL_SIZE) * 0.5f;
        mVerticalBound = (h - BALL_SIZE) * 0.5f;
    }

    //commencer à lire les modifications
    public void startSimulation() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    //arrete de lire les enregistrements
    public void stopSimulation() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBin, mXOrigin - HOLE_SIZE/2, mYOrigin - HOLE_SIZE/2, null);

        mBallon.updatePosition(mSensorX, mSensorY, mSensorZ, mSensorTimeStamp);
        mBallon.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);



        canvas.drawBitmap(mPaper,
                mXOrigin - BALL_SIZE/2 + mBallon.mPosX,
                mYOrigin - BALL_SIZE / 2 - mBallon.mPosY, null);

        invalidate();
    }

}
