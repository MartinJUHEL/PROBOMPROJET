package com.martin.promob.model;

import android.graphics.RectF;


    public class PongBat {


        // RectF is an object that holds four coordinates - just what we need
        private RectF mRect;

        // How long and high our mBat will be
        private float mLength;
        private float mHeight;

        // X is the far left of the rectangle which forms our mBat
        private float mXCoord;

        // Y is the top coordinate
        private float mYCoord;

        // This will hold the pixels per second speed that
        // the mBat will move
        private float mBatSpeed;

        // Which ways can the mBat move
        public final int STOPPED = 0;
        public final int LEFT = 1;
        public final int RIGHT = 2;

        // Is the mBat moving and in which direction
        private int mBatMoving = STOPPED;

        // The screen length and width in pixels
        private int mScreenX;
        private int mScreenY;

        public PongBat(int x, int y){

            mScreenX = x;
            mScreenY = y;

            // 1/8 screen width wide
            mLength = mScreenX / 8;

            // 1/25 screen mHeight high
            mHeight = mScreenY / 25;

            // Start mBat in roughly the screen centre
            mXCoord = mScreenX / 2;
            mYCoord = mScreenY - 40;

            mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength, mYCoord + mHeight);

            // How fast is the mBat in pixels per second
            mBatSpeed = mScreenX;
            // Cover entire screen in 1 second
        }

        public RectF getRect() {
            return mRect;
        }


        public void setMovementState(int state){
            mBatMoving = state;
        }

        // modifier la position de la BAt
        public void update(long fps){

            mXCoord = mBatMoving;

            float largeur = (mRect.right - mRect.left);

            // Make sure it's not leaving screen
            if(mRect.left < 0){ mXCoord = 0; }
            if((mBatMoving+largeur)> mScreenX){
                mXCoord = mScreenX -
                        // The width of the Bat
                        (mRect.right - mRect.left);
            }

            // Update the Bat graphics
            mRect.left = mXCoord;
            mRect.right = mXCoord + mLength;
        }




    }
