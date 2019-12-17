package com.martin.promob.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;

import com.martin.promob.R;

public class EscapeBall extends Activity implements SensorEventListener2 {

    //coordonné max de la balle
    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float xMax, yMax;
    private int ballW,ballH;
    private Bitmap ball;
    private Bitmap boomImg;
    private long boom;

///////////////////Remplacer 100 par la taille !!

    SensorManager sensorManager;


    public EscapeBall(SensorManager sensorManager, Context context) {
        Bitmap ballSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.faucon);
        Bitmap boomSrc=BitmapFactory.decodeResource(context.getResources(),R.mipmap.boom);
        ballW = 200;
        ballH = 200;
        xPos=500;
        yPos=1000;

        ball = Bitmap.createScaledBitmap(ballSrc, ballW, ballH, true);
        boomImg = Bitmap.createScaledBitmap(boomSrc, 200, 200, true);
        this.sensorManager = sensorManager;
        boom=0;

    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        xAccel = sensorEvent.values[0];
        yAccel = -sensorEvent.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Bitmap getBall() {
        return ball;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void updateBall() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        float frameTime = 0.666f;
        xVel += (xAccel * frameTime);
        yVel += (yAccel * frameTime);




        float xS = (xVel / 2) * frameTime;
        float yS = (yVel / 2) * frameTime;

        xPos -= xS;
        yPos -= yS;


        if (xPos >= xMax) {
            xPos =xMax;
        } else if (xPos <= 0) {
            xPos = 0;
        }

        if (yPos >= yMax) {
            yPos = yMax;
        } else if (yPos <= 0) {
            yPos = 0;
        }

    }

    public void boom(){
        System.out.println("Boom");
    boom=System.currentTimeMillis();
    }



    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        xMax = wScreen - 100;
        yMax = hScreen - 100;

    }


    public void draw(Canvas canvas) {

        canvas.drawBitmap(ball, xPos, yPos, null);
        if(System.currentTimeMillis()-boom<=1500){
            canvas.drawBitmap(boomImg, xPos - 5, yPos - 5, null);
        }

    }
}
