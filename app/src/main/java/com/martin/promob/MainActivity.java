package com.martin.promob;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.martin.promob.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

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

    MediaPlayer mediaPlayer;


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

        Button classementbutton2 = findViewById(R.id.buttonclassement2);
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

        try {
            loadScores();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveScores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginActivity.appTheme != null) {
            LoginActivity.appTheme.stop();
            LoginActivity.appTheme.release();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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
        classementTextView.setTypeface(fontbutton);
        classementTextView.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void multiRanking(View view) {
        classementTextView.setText(showMultiScore());
        classementTextView.setTypeface(fontbutton);
        classementTextView.setGravity(Gravity.CENTER_VERTICAL);

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


    public static void addSoloScore(int score, String user) {
        boolean insere = false;
        Pair p = new Pair(score, user);
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

    public static void addMultiScore(int score, String user) {
        boolean insere = false;
        Pair p = new Pair(score, user);
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

    public void loadScores() throws IOException, JSONException {
        String scoresJson = null;

        FileInputStream file = openFileInput("score.json");
        StringBuilder stringb = new StringBuilder();
        int content;
        while ((content = file.read()) != -1) {
            scoresJson = String.valueOf(stringb.append((char) content));
        }
        JSONObject scores = new JSONObject(scoresJson);
        JSONArray scoresMultiArray = scores.getJSONArray("score_multi");
        JSONArray scoresSoloArray = scores.getJSONArray("score_solo");

        for (int i = 0; i < scoresMultiArray.length(); i++) {
           JSONObject current=scoresMultiArray.getJSONObject(i);
            addMultiScore(current.getInt("score"),current.getString("user"));
        }
        for (int i = 0; i < scoresSoloArray.length(); i++) {
            JSONObject current=scoresSoloArray.getJSONObject(i);
            addSoloScore(current.getInt("score"),current.getString("user"));
        }


    }


    private void saveScores() {
        try {
            FileOutputStream file = openFileOutput("score.json", MODE_PRIVATE);
            JSONArray allScoreMulti = new JSONArray();
            JSONArray allScoreSolo = new JSONArray();


            for (Pair p : multiScores) {
                JSONObject score = new JSONObject();
                score.put("score", p.first);
                score.put("user", p.second);
                allScoreMulti.put(score);

            }
            for (Pair p : soloScores) {
                JSONObject score = new JSONObject();
                score.put("score", p.first);
                score.put("user", p.second);
                allScoreSolo.put(score);
            }
            JSONObject scores = new JSONObject();
            scores.put("score_multi", allScoreMulti);
            scores.put("score_solo", allScoreSolo);
            String save = scores.toString();
            file.write(save.getBytes());
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
