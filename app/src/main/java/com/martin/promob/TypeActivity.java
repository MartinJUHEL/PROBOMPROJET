package com.martin.promob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class TypeActivity extends AppCompatActivity {

    public static boolean compet;
    private Button trainingButton;
    private Button competButton;
    private TextView textJ1;
    private TextView textJ2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        trainingButton = findViewById(R.id.button_training);
        trainingButton.getBackground().setAlpha(MainActivity.getOpacity());

        competButton = findViewById(R.id.button_compet);
        competButton.getBackground().setAlpha(MainActivity.getOpacity());

        compet = false;

        View view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.drawable.background_type);

        textJ1 = findViewById(R.id.textView2);
        textJ1.setText(MainActivity.getCurrentUser().getFirstname());

        if (MainActivity.isMulti()) {
            textJ2 = findViewById(R.id.textView);
            textJ2.setText(MainActivity.getCurrentUser2().getFirstname());

            trainingButton.setActivated(false);
            trainingButton.setVisibility(View.INVISIBLE);

        }
    }

    public void training(View view) {
        compet = false;
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    public void competition(View view) {

        compet = true;
        ScoreActivity.initialise();
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);

    }


}
