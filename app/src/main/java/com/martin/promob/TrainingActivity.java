package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
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
        pong=findViewById(R.id.pongballbutton);
        memory=findViewById(R.id.memorybutton);
        escape=findViewById(R.id.escapebutton);
        justePrix=findViewById(R.id.justeprixbutton);
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






}


