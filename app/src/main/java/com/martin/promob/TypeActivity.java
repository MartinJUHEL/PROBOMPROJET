package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class TypeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        TextView textJ1 = findViewById(R.id.textView2);
        textJ1.setText(MainActivity.getCurrentUser().getFirstname());

        if(MainActivity.isMulti()) {
            TextView textJ2 = findViewById(R.id.textView);
            textJ2.setText(MainActivity.getCurrentUser2().getFirstname());
        }
    }

     public void training(View view) {
     Intent intent = new Intent(this, TrainingActivity.class);
     startActivity(intent);
    }

    public void competition(View view) {
        ScoreActivity.initialise();
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);

    }


}
