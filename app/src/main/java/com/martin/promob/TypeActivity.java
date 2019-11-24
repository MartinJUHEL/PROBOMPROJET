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

        Intent intent = getIntent();
        String type = intent.getStringExtra(MainActivity.EXTRA_TYPE);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Mode "+ type);
    }

     public void training(View view) {
     Intent intent = new Intent(this, TrainingActivity.class);
     startActivity(intent);
 }



}
