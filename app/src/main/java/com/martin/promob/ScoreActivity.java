package com.martin.promob;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoreActivity extends AppCompatActivity {

    public static int mScoreJ1;
    public static int mScoreJ2;
    public static int mScoreTotJ1;
    public static int mScoreTotJ2;
    public static int numberActivity;


    private static List<Class> list;
    public static boolean joueur1end; //joueur 1 a fini de jouer
    private static boolean debut; //Savoir si on est au debut de la compet

    private Button next;

    private TextView scorej1View;
    private TextView scorej1TotView;
    private TextView scorej2View;
    private TextView scorej2TotView;
    private TextView showPlayer;

    private Object currentGame;

    ImageView republic;
    ImageView empire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scorej1View = findViewById(R.id.textView_scoretemp);
        scorej1TotView = findViewById(R.id.textView_scoretotal);

        scorej2View = findViewById(R.id.textView_scoretemp2);
        scorej2TotView = findViewById(R.id.textView_scoretotal2);
        showPlayer = findViewById(R.id.textview_showplayer);

        scorej1View.setText("Score " + MainActivity.getCurrentUser().getFirstname() + " : " + mScoreJ1);
        scorej1TotView.setText("Total " + MainActivity.getCurrentUser().getFirstname() + " : " + mScoreTotJ1);

        republic= findViewById(R.id.imageview_republic);
        empire =findViewById(R.id.imageview_empire);

        republic.setImageResource(R.drawable.republic);
        republic.setAdjustViewBounds(true);
        republic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        empire.setImageResource(R.drawable.empire);
        empire.setAdjustViewBounds(true);
        empire.setScaleType(ImageView.ScaleType.CENTER_INSIDE);




        //mode solo on cache les scores du joueur 2
        if (!MainActivity.isMulti()) {
            scorej2View.setActivated(false);
            scorej2View.setVisibility(View.INVISIBLE);
            scorej2TotView.setActivated(false);
            scorej2TotView.setVisibility(View.INVISIBLE);
            empire.setVisibility(View.INVISIBLE);
            empire.setActivated(false);

            //mode multi
        }else {
            scorej2View.setText("Score " + MainActivity.getCurrentUser2().getFirstname() + " : " + mScoreJ2);
            scorej2TotView.setText("Total " + MainActivity.getCurrentUser2().getFirstname() + " : " + mScoreTotJ2);
        }

        showPlayer.setTextColor(Color.BLACK);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginActivity.appTheme = MediaPlayer.create(getApplicationContext(), R.raw.vadertheme);
        LoginActivity.appTheme.setLooping(true);
        LoginActivity.appTheme.start();


        scorej1View.setText("Score " + MainActivity.getCurrentUser().getFirstname() + " : " + mScoreJ1);
        scorej1TotView.setText("Total " + MainActivity.getCurrentUser().getFirstname() + " : " + mScoreTotJ1);

        if(MainActivity.isMulti()){
            scorej2View.setText("Score " + MainActivity.getCurrentUser2().getFirstname() + " : " + mScoreJ2);
            scorej2TotView.setText("Total " + MainActivity.getCurrentUser2().getFirstname() + " : " + mScoreTotJ2);
        }


        if(!joueur1end){
                showPlayer.setText(MainActivity.getCurrentUser2().getFirstname()+" c'est à toi de jouer");
        }else{
                showPlayer.setText(MainActivity.getCurrentUser().getFirstname()+" c'est à toi de jouer");

            }
        }

    @Override
    protected void onPause() {
        super.onPause();
        LoginActivity.appTheme.pause();
    }

    public static void initialise() {
        mScoreJ1 = 0;
        mScoreJ2 = 0;
        mScoreTotJ1 = 0;
        mScoreTotJ2 = 0;

        numberActivity = 3;
        list = new ArrayList<Class>();
        list.add(QuizzActivity.class);
        list.add(EscapeActivity.class);
        list.add(PongActivity.class);
        if (MainActivity.isMulti()) {
            list.add(MemoriesMultiActivity.class);
            list.add(JustePrixMultiActivity.class);

        } else {
            list.add(MemorySoloActivity.class);
            list.add(JustePrixActivity.class);

        }
        joueur1end = true; //signifie que le joueur 1 joue

    }

    //Si le joueur joue en solo
    public void playSolo() {
        if (numberActivity == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Fin de la partie !");
            builder.setMessage("Le score de " + MainActivity.getCurrentUser().getFirstname() + " est de " + mScoreTotJ1);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // End the activity
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }).setCancelable(false)
                    .create()
                    .show();
            endgame();

        } else {
            numberActivity--;
            playj1();
        }
    }


    //Si le joueur joue en multi
    public void playMulti() {
        if (currentGame == MemoriesMultiActivity.class || currentGame ==JustePrixMultiActivity.class) {
            numberActivity--;
            setJoueur1end(true);
            currentGame=null;
        }

        if (numberActivity == 0) {
            //Affichage du vainqueur
            String vainq;
            if (mScoreTotJ2 > mScoreTotJ1) {
                vainq = MainActivity.getCurrentUser2().getFirstname();
            } else {
                if (mScoreTotJ2 < mScoreTotJ1) {
                    vainq = MainActivity.getCurrentUser().getFirstname();
                } else {
                    vainq = MainActivity.getCurrentUser().getFirstname() + " et " + MainActivity.getCurrentUser2().getFirstname();
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Fin de la partie !");
            builder.setMessage("Le score de " + MainActivity.getCurrentUser().getFirstname() + " est de " + mScoreTotJ1 + "\n"
                    + "Le score de " + MainActivity.getCurrentUser2().getFirstname() + " est de " + mScoreTotJ2
                    + "\n" + "Le vainqueur : " + vainq);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // End the activity
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
            )
                    .setCancelable(false)
                    .create()
                    .show();

            endgame();

        } else {
                //On fait jouer le bon joueur
                if (joueur1end) {
                    playj1();
                    setJoueur1end(false);
                } else {
                    playj2();
                    setJoueur1end(true);
                }
        }
    }


    public void playj1() {

        final int game = new Random().nextInt(list.size());
        Intent intent;
        intent = new Intent(this, list.get(game));

        currentGame = list.get(game);
        list.remove(game);
        startActivity(intent);
    }

    public void playj2() {
        numberActivity--;
        Intent launchGame;
        launchGame = new Intent(this, (Class<?>) currentGame);
        startActivity(launchGame);
    }

    public void next(View view) {
        next = findViewById(R.id.button_next);
        mScoreJ1 = 0;
        mScoreJ2 = 0;
        if (MainActivity.isMulti()) {
            playMulti();
        } else {
            playSolo();
        }
    }


    public static void setJoueur1end(boolean joueur1end) {
        ScoreActivity.joueur1end = joueur1end;
    }

    public static boolean isJoueur1end() {
        return joueur1end;
    }


    public static void setmScoreJ1(int mScoreJ1) {
        ScoreActivity.mScoreJ1 = mScoreJ1;
    }

    public static void setmScoreJ2(int mScoreJ2) {
        ScoreActivity.mScoreJ2 = mScoreJ2;
    }

    public static void addmScoreTotJ1() {
        mScoreTotJ1 += mScoreJ1;
    }

    public static void addmScoreTotJ2() {
        ScoreActivity.mScoreTotJ2 += mScoreJ2;
    }

    public void endgame() {
        if (MainActivity.isMulti()) {
            MainActivity.addMultiScore(mScoreTotJ1, MainActivity.getCurrentUser());
            MainActivity.addMultiScore(mScoreTotJ2, MainActivity.getCurrentUser2());
        } else {
            MainActivity.addSoloScore(mScoreTotJ1, MainActivity.getCurrentUser());
        }
    }
}
