package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainingActivity extends AppCompatActivity {

    //Pour la police star wars
    Typeface fontbutton;

    Button quizz;
    Button pong;
    Button memory;
    Button escape;
    Button justePrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        fontbutton = Typeface.createFromAsset(getAssets(), "fonts/SfDistantGalaxy-0l3d.ttf");

        quizz=findViewById(R.id.quizzbutton);
        quizz.getBackground().setAlpha(MainActivity.getOpacity());

        pong=findViewById(R.id.pongballbutton);
        pong.getBackground().setAlpha(MainActivity.getOpacity());

        memory=findViewById(R.id.memorybutton);
        memory.getBackground().setAlpha(MainActivity.getOpacity());

        escape=findViewById(R.id.escapebutton);
        escape.getBackground().setAlpha(MainActivity.getOpacity());
        
        justePrix=findViewById(R.id.justeprixbutton);
        justePrix.getBackground().setAlpha(MainActivity.getOpacity());

        quizz.setTypeface(fontbutton);
        pong.setTypeface(fontbutton);
        memory.setTypeface(fontbutton);
        escape.setTypeface(fontbutton);
        justePrix.setTypeface(fontbutton);



    }

    public void OnclickQuizz(View view) {
        Intent startQuizz = new Intent(this, QuizzActivity.class);
        startActivity(startQuizz);

    }

    public void OnclickPong(View view) {
        Intent startPong = new Intent(this, PongActivity.class);
        startActivity(startPong);

    }

    public void OnclickMemory(View view) {
        Intent startMemory = new Intent(this, MemorySoloActivity.class );
        startActivity(startMemory);

    }

    public void OnclickEscape(View view) {
        Intent startEscape = new Intent(this, EscapeActivity.class );
        startActivity(startEscape);

    }

    public void OnclickJustePrix(View view){
        Intent startJustePrix = new Intent(this, JustePrixActivity.class );
        startActivity(startJustePrix);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoginActivity.appTheme = MediaPlayer.create(getApplicationContext(), R.raw.vadertheme);
        LoginActivity.appTheme.setLooping(true);
        LoginActivity.appTheme.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LoginActivity.appTheme.pause();
    }
}


