package com.martin.promob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.martin.promob.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.martin.promob.LoginActivity.EXTRA_USER_LOGIN;

public class MainActivity extends Activity {

    private Button classementbutton;
    private ClassementSlider classementSlider;
    private RelativeLayout toHide;

    public static final String EXTRA_TYPE = "com.martin.EXTRA_TYPE";
    public static String userLogin;
    public static Map<String, User> mUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère le bouton pour cacher/afficher le menu
        classementbutton = findViewById(R.id.buttonclassement);

        // On récupère le menu
        toHide = findViewById(R.id.toHide);
        toHide.setVisibility(View.GONE);

        // On récupère le layout principal
        classementSlider = findViewById(R.id.classementslider);

        // On donne le menu au layout principal
        classementSlider.setToHide(toHide);



        Intent intentLogin = getIntent();
        userLogin = intentLogin.getStringExtra(EXTRA_USER_LOGIN);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Hello " + userLogin);
         mUser= new HashMap<>();
        mUser.put(userLogin, new User(userLogin));

    }

    public void onePlayer(View view) {
        Intent intent = new Intent(this, TypeActivity.class);
        Button oneplayer = (Button) findViewById(R.id.onePlayerButton);
        String type = oneplayer.getText().toString();
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_USER_LOGIN, userLogin);
        startActivity(intent);
    }

    public void multiPlayer(View view) {
        Intent intent = new Intent(this, TypeActivity.class);
        Button multiplayerButton = (Button) findViewById(R.id.multiplayerButton);
        String type = multiplayerButton.getText().toString();
        intent.putExtra(EXTRA_TYPE, type);
        startActivity(intent);
    }

    public void showClassement(View view) {
        classementSlider.toggle();
    }


}
