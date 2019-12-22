package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.martin.promob.model.PongView;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
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
        Intent startMemory = new Intent(this, MemoriesActivity.class );
        startActivity(startMemory);

    }

    public void OnclickEscape(View view) {
        Intent startEscape = new Intent(this, EscapeActivity.class );
        startActivity(startEscape);

    }




}


