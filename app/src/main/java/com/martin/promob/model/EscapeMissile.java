package com.martin.promob.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

import com.martin.promob.R;

public class EscapeMissile {

    private final Context mContext;
    private Bitmap img;
    private Bitmap missileSrc;
    private int xPos, yPos; // coordonnées x,y de la balle en pixel
    private int missileW, missileH; // largeur et hauteur du missile en pixels
    private float xMax, yMax; // largeur et hauteur de l'écran en pixels
    private int speedX, speedY;
    private float angle;

    public EscapeMissile(Context context) {
        this.mContext = context;
        missileSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.asteroide);
        xPos = 500;
        yPos = -100;
        missileW = 300;
        missileH = 300;
        angle = 25+(float)Math.random()*125;
        speedX=25;
        speedY=25;
        // on définit (au choixPos) la taille de la balle à 1/5ème de la largeur de l'écran
        taille();

    }

    public Bitmap getImg() {
        return img;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        xMax = wScreen;
        yMax = hScreen - 100;

    }

    private void taille(){
        int taille=(int)(200+Math.random()*350);
        missileW=taille;
        missileH=taille;
        img = Bitmap.createScaledBitmap(missileSrc, missileW, missileH, true);
    }


    public void moveWithCollisionDetection() {

        //img= rotateBitmap(img);

        // on incrémente les coordonnées X et Y
        xPos = (int) (xPos + speedX * Math.cos(Math.toRadians(angle)));
        yPos = (int) (yPos + speedY * Math.sin(Math.toRadians(angle)));

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if (xPos+missileW>= xMax) {
            angle =180-angle;
        }
        if (yPos > yMax+50) {
            yPos= -100;
            xPos=(int)(Math.random()*xMax-250)+100;
            angle = 25+(float)Math.random()*125;
           taille();
        }
        if (xPos  < 0) {
            angle = 180-angle;
        }



    }



    // on dessine lle missile, en x et y
    public void draw(Canvas canvas) {

        if (img == null) {
            return;
        }

        canvas.drawBitmap(img, xPos, yPos, null);
    }
}
