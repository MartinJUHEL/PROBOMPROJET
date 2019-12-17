package com.martin.promob.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.martin.promob.R;

public class EscapeMissile extends View {

    private final Context mContext;
    private Bitmap img;
    private  Bitmap missileSrc;
    private int xPos, yPos; // coordonnées x,y de la balle en pixel
    private int missileW, missileH; // largeur et hauteur du missile en pixels
    private float xMax, yMax; // largeur et hauteur de l'écran en pixels
    private Rect rect;
    private static final int INCREMENT = 10;
    private int speedX = INCREMENT, speedY = INCREMENT;

    public EscapeMissile(Context context) {
        super(context);
        this.mContext = context;
        missileSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.missile);
        xPos = 200;
        yPos = 300;
        missileW=200;
        missileH=200;
        // on définit (au choixPos) la taille de la balle à 1/5ème de la largeur de l'écran
         img = Bitmap.createScaledBitmap(missileSrc,  missileW, missileH, true);

        rect=new Rect( xPos, yPos, missileW, missileH);



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
        xMax = wScreen-100;
        yMax = hScreen-100;

    }


    public Bitmap rotateBitmap(float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(missileSrc, 0, 0,(int)missileW, (int) missileH, matrix, true);
    }

    private void rotation(){

        if(speedX>0 && speedY>0){
            img=rotateBitmap(135);
        }
        if(speedX>0 && speedY<=0){
            img=rotateBitmap(225);
        }
        if(speedX<=0 && speedY>0){
            img=rotateBitmap(45);
        }
        if(speedX<=0 && speedY<=0){
            img=rotateBitmap(270);
        }


    }


    public Rect getRect() {
        return rect;
    }

    public void moveWithCollisionDetection()
    {

        // on incrémente les coordonnées X et Y
        xPos+=speedX;
        yPos+=speedY;

        rect.left=xPos;
        rect.top=yPos;
        rect.bottom=yPos+missileH;
        rect.right=xPos+missileW;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(xPos+missileW > xMax) {speedX=-INCREMENT;}

        // si yPos dépasse la hauteur l'écran, on inverse le déplacement
        if(yPos+missileH > yMax) {speedY=-INCREMENT;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(xPos<0) {speedX=INCREMENT;}

        // si y passe à dessus de l'écran, on inverse le déplacement
        if(yPos<0) {speedY=INCREMENT;}
    }

    // on dessine la balle, en x et y
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (img == null) {
            return;
        }
        canvas.drawBitmap(img, xPos, yPos, null);
        invalidate();
    }
}
