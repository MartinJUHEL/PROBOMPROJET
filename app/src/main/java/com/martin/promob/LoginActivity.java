package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
    public static final String EXTRA_USER_LOGIN = "com.martin.EXTRA_USER_LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }



    public void sendMessage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String userLogin = editText.getText().toString();
        intent.putExtra(EXTRA_USER_LOGIN, userLogin);
        startActivity(intent);
    }




}
