package com.martin.promob;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.martin.promob.model.EscapeBall;
import com.martin.promob.model.EscapeMissile;

public class EscapeView extends SurfaceView implements SurfaceHolder.Callback {
    private EscapeMissile missile;
    private EscapeBall ball;
    Context mContext;
    private EscapeThread escapeThread;
    private int life;
    private long tpstouch;
    private long chrono;

    public EscapeView(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.mContext = context;
        escapeThread = new EscapeThread(this);
        missile = new EscapeMissile(this.getContext());
        ball = new EscapeBall((SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE), context);
        life = 3;
        tpstouch = System.currentTimeMillis();
        chrono = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (escapeThread.getState() == Thread.State.TERMINATED) {
            escapeThread = new EscapeThread(this);
        }
        escapeThread.setRunning(true);
        escapeThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        missile.resize(width, height); // on définit la taille de la balle selon la taille de l'écran
        ball.resize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        escapeThread.setRunning(false);
        while (retry) {
            try {
                escapeThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void update() {
        missile.moveWithCollisionDetection();
        ball.updateBall();
        chrono+=1;
        if (isCollisionDetected(ball.getBall(), (int) ball.getxPos(), (int) ball.getyPos(), missile.getImg(), missile.getxPos(), missile.getyPos())) {
            touch();
        }
    }


    public static boolean isCollisionDetected(Bitmap bitmap1, int x1, int y1,
                                              Bitmap bitmap2, int x2, int y2) {

        if (bitmap1 == null || bitmap2 == null) {
            throw new IllegalArgumentException("bitmaps cannot be null");
        }

        Rect bounds1 = new Rect(x1, y1, x1 + bitmap1.getWidth(), y1 + bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2 + bitmap2.getWidth(), y2 + bitmap2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i - x1, j - y1);
                    int bitmap2Pixel = bitmap2.getPixel(i - x2, j - y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    public boolean lose() {
        if (life == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void touch() {
        System.out.println("Touché");
        System.out.println("Tps :"+tpstouch);
        System.out.println("Actuel: "+System.currentTimeMillis());

        if (System.currentTimeMillis() - tpstouch >= 1500) {
            System.out.println("Enlever vie");

            life -= 1;
            tpstouch = System.currentTimeMillis();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        missile.draw(canvas);
        ball.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Vie : " + life, (float) 100, (float) 100, paint);
        canvas.drawText("Temps : " + chrono/30, (float) 500, (float) 100, paint);


    }
}

