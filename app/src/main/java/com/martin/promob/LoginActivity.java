package com.martin.promob;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.martin.promob.model.User;

import java.io.IOException;

public class LoginActivity extends Activity {


    EditText editTextj1;
    EditText editTextj2;
    private Button send;

    //Pour la police star wars
    Typeface fontbutton;

    public static FrameLayout layout;
    public static MediaPlayer appTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fontbutton = Typeface.createFromAsset(getAssets(), "fonts/SfDistantGalaxy-0l3d.ttf");



        editTextj1 = findViewById(R.id.edit_user1);
        editTextj2 = findViewById(R.id.edit_user2);
        editTextj1.setTypeface(fontbutton);
        editTextj2.setTypeface(fontbutton);


        send = findViewById(R.id.sendButton);
        send.getBackground().setAlpha(MainActivity.getOpacity());
        send.setTypeface(fontbutton);


        View view = this.getWindow().getDecorView();

        if (!MainActivity.isMulti()) {
            editTextj2.setActivated(false);
            editTextj2.setVisibility(View.INVISIBLE);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        appTheme = MediaPlayer.create(getApplicationContext(), R.raw.vadertheme);
        appTheme.setLooping(true);
        appTheme.start();
    }



    @Override
    protected void onPause() {
        super.onPause();
        LoginActivity.appTheme.pause();
    }



    public void sendMessage(View view) {
        Intent intent = new Intent(this, TypeActivity.class);

        String user1 = editTextj1.getText().toString();

        if (MainActivity.getmUser().containsKey(user1)) {
            MainActivity.setCurrentUser(MainActivity.getmUser().get(user1));
        } else {
            MainActivity.getmUser().put(user1, new User(user1));
            MainActivity.setCurrentUser(MainActivity.getmUser().get(user1));
        }

        if (MainActivity.isMulti()) {
            String user2 = editTextj2.getText().toString();

            if (MainActivity.getmUser().containsKey(user2)) {
                MainActivity.setCurrentUser2(MainActivity.getmUser().get(user2));
            } else {
                MainActivity.getmUser().put(user2, new User(user2));
                MainActivity.setCurrentUser2(MainActivity.getmUser().get(user2));
            }

        }
        startActivity(intent);
    }

}
