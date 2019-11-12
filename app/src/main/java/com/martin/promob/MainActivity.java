package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userLogin = intent.getStringExtra(LoginActivity.EXTRA_USERLOGIN);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Hello "+ userLogin);
    }



}