package com.martin.promob.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.martin.promob.R;
import com.martin.promob.ScoreActivity;
import com.martin.promob.TypeActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EscapeView extends SurfaceView implements SurfaceHolder.Callback {
    private EscapeMissile missile;
    private EscapeBall ball;
    Context mContext;
    private EscapeThread escapeThread;
    private int life;
    private long tpstouch;
    private long chrono;
    private int mScreenX, mScreenY;

    private Bitmap bgImg;

    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;
    private static final int MAX_STREAMS = 100;


    private final List<Explosion> explosionList = new ArrayList<Explosion>();


    public EscapeView(Context context, int x, int y) {
        super(context);
        getHolder().addCallback(this);
        this.mContext = context;
        escapeThread = new EscapeThread(this);

        life = 3;
        tpstouch = System.currentTimeMillis();
        chrono = 0;
        mScreenX = x;
        mScreenY = y;

        Bitmap bgSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.starbackground);
        bgImg = Bitmap.createScaledBitmap(bgSrc, mScreenX, mScreenY, true);


        missile = new EscapeMissile(this.getContext());
        ball = new EscapeBall((SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE), context);


        this.initSoundPool();

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


    public void update() {
        missile.moveWithCollisionDetection();
        ball.updateBall();
        chrono += 1;
        if (isCollisionDetected(ball.getBall(), (int) ball.getxPos(), (int) ball.getyPos(), missile.getImg(), missile.getxPos(), missile.getyPos())) {
            touch();
            for (Explosion explosion : this.explosionList) {
                explosion.update();
            }

            Iterator<Explosion> iterator = this.explosionList.iterator();
            while (iterator.hasNext()) {
                Explosion explosion = iterator.next();

                if (explosion.isFinish()) {
                    // If explosion finish, Remove the current element from the iterator & list.
                    iterator.remove();
                    continue;
                }
            }
        }
        lose();
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

    public void lose() {
        System.out.println("Valeur de life "+ life);

        if (life == 0) {
            if (TypeActivity.compet) {
                int score = (int) ((chrono / 30) / 10);
                System.out.println("score du jeu escape " + score);
                ScoreActivity.setmScoreJ1(score);
                ScoreActivity.addmScoreTotJ1();
            }
            final Activity act = (Activity) this.getContext();

            act.finish();
        }
        else{

        }
    }

    public void touch() {
        if (System.currentTimeMillis() - tpstouch >= 1500) {
            life -= 1;

            Bitmap boomSrc = BitmapFactory.decodeResource(this.getResources(), R.drawable.explosion);
            Explosion explosion = new Explosion(this, boomSrc, (int) ball.getxPos(), (int) ball.getyPos());
            this.explosionList.add(explosion);

            tpstouch = System.currentTimeMillis();
        }

    }

    private void initSoundPool() {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;

                // Playing background sound.
                playSoundBackground();
            }
        });

        // Load the sound background.mp3 into SoundPool
        // this.soundIdBackground= this.soundPool.load(this.getContext(), R.raw.background,1);

        // Load the sound explosion.wav into SoundPool
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion, 1);


    }

    public void playSoundExplosion() {
        if (this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground() {
        if (this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            // Play sound background.mp3
            int streamId = this.soundPool.play(this.soundIdBackground, leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        canvas.drawBitmap(bgImg, 0, 0, null);
        missile.draw(canvas);
        ball.draw(canvas);
        for (Explosion explosion : this.explosionList) {
            explosion.draw(canvas);
        }
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("Vie : " + life, (float) 100, (float) 100, paint);
        canvas.drawText("Temps : " + chrono / 30, (float) 500, (float) 100, paint);


    }
}

