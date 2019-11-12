package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userLogin = intent.getStringExtra(LoginActivity.EXTRA_USERLOGIN);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Hello "+ userLogin);

    }

    public void onePlayer(View view){
        Intent intent = new Intent(this, TypeActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        startActivity(intent);
    }



}
