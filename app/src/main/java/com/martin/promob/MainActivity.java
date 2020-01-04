package com.martin.promob;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.martin.promob.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private Button classementbutton;
    private ClassementSlider classementSlider;
    private RelativeLayout toHide;
    private ScrollView classementView;
    private TextView classementTextView;
    public Button oneplayer;
    private ImageButton setting;

    private static Map<String, User> mUser;
    private static ArrayList<Pair<Integer, User>> soloScores;
    private static ArrayList<Pair<Integer, User>> multiScores;

    private static User currentUser;
    private static User currentUser2;
    private static boolean multi;

    private static int opacity;

    //Pour la police star wars
    Typeface fontbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new HashMap<>();
        multiScores = new ArrayList<>();
        soloScores = new ArrayList<>();
        currentUser = null;
        currentUser2 = null;

        opacity = 70;

        multi = false;

        fontbutton = Typeface.createFromAsset(getAssets(), "fonts/SfDistantGalaxy-0l3d.ttf");

        classementView = findViewById(R.id.scroll_view_classement);

        // On récupère le bouton pour cacher/afficher le menu
        classementbutton = findViewById(R.id.buttonclassement);
        classementbutton.getBackground().setAlpha(opacity);
        classementbutton.setTypeface(fontbutton);

        Button classementbutton2=findViewById(R.id.buttonclassement2);
        classementbutton2.getBackground().setAlpha(opacity);
        classementbutton2.setTypeface(fontbutton);


        // On récupère le layout principal
        classementSlider = findViewById(R.id.classementslider);

        // On récupère le menu
        toHide = findViewById(R.id.toHide);
        toHide.setVisibility(View.GONE);


        // On donne le menu au layout principal
        classementSlider.setToHide(toHide);

        classementTextView = findViewById(R.id.playerclassement_textview);

        Button oneplayer = (Button) findViewById(R.id.onePlayerButton);
        oneplayer.setTypeface(fontbutton);

        Button multiplayerButton = (Button) findViewById(R.id.multiplayerButton);
        multiplayerButton.setTypeface(fontbutton);

        Button scoreSolo = findViewById(R.id.soloclassement);
        scoreSolo.getBackground().setAlpha(opacity);
        scoreSolo.setTypeface(fontbutton);


        Button scoremulti = findViewById(R.id.multiclassement);
        scoremulti.getBackground().setAlpha(opacity);
        scoremulti.setTypeface(fontbutton);

    }

    public void onePlayer(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        multi = false;
        startActivity(intent);
    }

    public void multiPlayer(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        multi = true;
        startActivity(intent);
    }

    public void soloRanking(View view) {
        classementTextView.setText(showSoloScore());
    }

    public void multiRanking(View view) {
        classementTextView.setText(showMultiScore());
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

    public static ArrayList<Pair<Integer, User>> getSoloScores() {
        return soloScores;
    }

    public static ArrayList<Pair<Integer, User>> getMultiScores() {
        return multiScores;
    }


    public static void addSoloScore(int score, User user) {
        boolean insere = false;
        Pair p = new Pair(score, user.getFirstname());
        user.addScore(score);
        for (int i = 0; i < soloScores.size(); i++) {
            if ((score >= soloScores.get(i).first) && !insere) {
                soloScores.add(i, p);
                insere = true;
            }
        }
        if (!insere) {
            soloScores.add(p);
        }
    }

    public String showSoloScore() {
        String s = "";
        for (Pair p : soloScores) {
            s += p.second + " : " + p.first + "\n";
        }
        return s;
    }

    public static void addMultiScore(int score, User user) {
        boolean insere = false;
        Pair p = new Pair(score, user.getFirstname());
        user.addScore(score);
        for (int i = 0; i < multiScores.size(); i++) {
            if ((score >= multiScores.get(i).first) && !insere) {
                multiScores.add(i, p);
                insere = true;
            }
        }
        if (!insere) {
            multiScores.add(p);
        }
    }

    public String showMultiScore() {
        String s = "";
        for (Pair p : multiScores) {
            s += p.second + " : " + p.first + "\n";
        }
        return s;
    }

    public static int getOpacity() {
        return opacity;
    }

}
