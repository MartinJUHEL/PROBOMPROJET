package com.martin.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

import static com.martin.promob.LoginActivity.EXTRA_USER_LOGIN;

public class MainActivity extends Activity {

    public static final String EXTRA_TYPE = "com.martin.EXTRA_TYPE";
    public static String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentLogin = getIntent();
        userLogin = intentLogin.getStringExtra(EXTRA_USER_LOGIN);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Hello "+ userLogin);

    }

    public void onePlayer(View view){
        Intent intent = new Intent(this, TypeActivity.class);
        Button oneplayer = (Button) findViewById(R.id.onePlayerButton);
        String type = oneplayer.getText().toString();
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_USER_LOGIN,userLogin);
        startActivity(intent);
    }

    public void multiPlayer(View view){
        Intent intent = new Intent(this, TypeActivity.class);
        Button multiplayerButton = (Button) findViewById(R.id.multiplayerButton);
        String type = multiplayerButton.getText().toString();
        intent.putExtra(EXTRA_TYPE, type);
        startActivity(intent);
    }


}
