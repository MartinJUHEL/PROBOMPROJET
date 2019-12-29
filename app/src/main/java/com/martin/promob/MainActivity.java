package com.martin.promob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.martin.promob.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private Button classementbutton;
    private ClassementSlider classementSlider;
    private RelativeLayout toHide;
    private ScrollView classementView;


    private static Map<String, User> mUser;
    private static ArrayList<Pair<Integer, User>> mScores;
    private static User currentUser;
    private static User currentUser2;
    private static boolean multi;

    private Button scoreSolo;
    private Button scoreMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new HashMap<>();
        mScores = new ArrayList<>();
        currentUser = null;
        currentUser2 = null;

        multi = false;

        scoreSolo = findViewById(R.id.soloclassement);
        scoreMulti = findViewById(R.id.multiclassement);

        classementView = findViewById(R.id.scroll_view_classement);

        // On récupère le bouton pour cacher/afficher le menu
        classementbutton = findViewById(R.id.buttonclassement);

        // On récupère le menu
        toHide = findViewById(R.id.toHide);
        toHide.setVisibility(View.GONE);

        // On récupère le layout principal
        classementSlider = findViewById(R.id.classementslider);

        // On donne le menu au layout principal
        classementSlider.setToHide(toHide);


    }

    public void onePlayer(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        Button oneplayer = (Button) findViewById(R.id.onePlayerButton);
        String type = oneplayer.getText().toString();
        multi = false;
        startActivity(intent);
    }

    public void multiPlayer(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        Button multiplayerButton = (Button) findViewById(R.id.multiplayerButton);
        String type = multiplayerButton.getText().toString();
        multi = true;
        startActivity(intent);
    }

    public void showClassement(View view) {
        classementSlider.toggle();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static User getCurrentUser2() {
        return currentUser2;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void setCurrentUser2(User user) {
        currentUser2 = user;
    }


    public static boolean isMulti() {
        return multi;
    }


    public static Map<String, User> getmUser() {
        return mUser;
    }

    public static ArrayList<Pair<Integer, User>> getmScores() {
        return mScores;
    }

    public static void addScore(int score, User user) {
        boolean insere = false;
        Pair p = new Pair(score, user);
        for (int i = 0; i < mScores.size(); i++) {
            if ((score >= mScores.get(i).first)&& !insere) {
                mScores.add(i, p);
                insere = true;
            }
        }
        if (!insere) {
            mScores.add(p);
        }
    }

    public String showScore(){
        String s="";
        for (Pair p:mScores) {
            s+= p.second +" : "+p.first+"\n";
        }
        return s;
    }

}
