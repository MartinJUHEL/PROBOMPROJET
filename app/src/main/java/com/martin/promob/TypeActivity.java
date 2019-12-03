package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.martin.promob.LoginActivity.EXTRA_USER_LOGIN;

public class TypeActivity extends AppCompatActivity {

    String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        Intent intent = getIntent();
        String type = intent.getStringExtra(MainActivity.EXTRA_TYPE);

        Intent intentLogin = getIntent();
        userLogin = intentLogin.getStringExtra(EXTRA_USER_LOGIN);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Mode "+ type);
    }

     public void training(View view) {
     Intent intent = new Intent(this, TrainingActivity.class);
     startActivity(intent);
 }



}
