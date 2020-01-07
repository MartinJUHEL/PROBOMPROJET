package com.martin.promob.model;

import android.graphics.RectF;

import java.util.Random;

public class PongBall {

    private RectF mRect;

    private float mXVelocity;
    private float mYVelocity;

    //largeur de la balle
    private float mBallWidth;
    //hauteur de la balle
    private float mBallHeight;


    public PongBall(int screenX, int screenY){

        // creer une balle dont la taille est relative a la taille de l'ecran
        // la balle ici represente 1/100 de la taille de l'ecran
        mBallWidth = screenX / 40;
        mBallHeight = mBallWidth;

    //on initialise la vitesse de la balle en fonction de l'ecran
        // la balle parcourt 1/4 de l'ecran en une seconde
        mYVelocity = screenY / 4;
        mXVelocity = mYVelocity;

        // Initialize the Rect that represents the mBall
        mRect = new RectF();

    }

    public RectF getRect(){
        return mRect;
    }

    //mise a jour de la position de la balle
    public void update(long fps){
        mRect.left = mRect.left + (mXVelocity / fps);
        mRect.top = mRect.top + (mYVelocity / fps);
        mRect.right = mRect.left + mBallWidth;
        mRect.bottom = mRect.top - mBallHeight;
    }

    // Retourne la direction verticale
    public void reverseYVelocity(){
        mYVelocity = -mYVelocity;
    }

    // Retourne la direction horizontale
    public void reverseXVelocity(){
        mXVelocity = -mXVelocity;
    }

    public void setRandomXVelocity(){

        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    // Speed up by 10%
    // A score of over 20 is quite difficult
    // Reduce or increase 10 to make this easier or harder
    public void increaseVelocity(){
        mXVelocity = mXVelocity + mXVelocity / 4;
        mYVelocity = mYVelocity + mYVelocity /4;
    }



    public void clearObstacleY(float y){
        mRect.bottom = y;
        mRect.top = y - mBallHeight;
    }

    public void clearObstacleX(float x){
        mRect.left = x;
        mRect.right = x + mBallWidth;
    }

    public void reset(int x, int y){
        mRect.left = x / 2;
        mRect.top = y - 20;
        mRect.right = x / 2 + mBallWidth;
        mRect.bottom = y - 20 - mBallHeight;
    }



}
