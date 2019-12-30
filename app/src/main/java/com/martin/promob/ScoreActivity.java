package com.martin.promob;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private static List<Integer> list;
    private Button next;

    private TextView scorej1View;
    private TextView scorej1TotView;
    private TextView scorej2View;
    private TextView scorej2TotView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scorej1View = findViewById(R.id.textView_scoretemp);
        scorej1TotView = findViewById(R.id.textView_scoretotal);
        scorej2View = findViewById(R.id.textView_scoretemp2);
        scorej2TotView = findViewById(R.id.textView_scoretotal2);

        scorej1View.setText("Score : " + mScoreJ1);
        scorej1TotView.setText("Score total : " + mScoreTotJ1);
        scorej2View.setText("Score : " + mScoreJ1);
        scorej2TotView.setText("Score total : " + mScoreTotJ1);


        if (!MainActivity.isMulti()) {
            scorej2View.setActivated(false);
            scorej2View.setVisibility(View.INVISIBLE);
            scorej2TotView.setActivated(false);
            scorej2TotView.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scorej1View.setText("Score : " + mScoreJ1);
        scorej1TotView.setText("Score total : " + mScoreTotJ1);
    }

    public static void initialise() {
        mScoreJ1 = 0;
        mScoreJ2 = 0;
        mScoreTotJ1 = 0;
        mScoreTotJ2 = 0;
        numberActivity = 3;
        list = new ArrayList<Integer>();
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(3);

    }

    public void next(View view) {
        next = findViewById(R.id.button_next);

        mScoreJ1 = 0;
        mScoreJ2 = 0;

        //Si c'est la fin de la compétition
        if (numberActivity == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Fin de la partie!");
            if(MainActivity.isMulti()){
                builder.setMessage("Le score du joueur 1 est de " + mScoreTotJ1);
                builder.setMessage("Le score du joueur 2 est de "+ mScoreTotJ2);

                //si c'est en mode multijoueur il faut afficher les deux scores
            }
            else{

                builder.setMessage("Ton score est de " + mScoreTotJ1);

            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // End the activity
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            })
                    .setCancelable(false)
                    .create()
                    .show();

            endgame();
            // creer fonction endgame

        } else {
            numberActivity--;
            final int game = new Random().nextInt(list.size());

            Intent intent;


            switch (game) {
                case 0:
                    intent = new Intent(this, QuizzActivity.class);
                    break;
                case 1:
                    intent = new Intent(this, EscapeActivity.class);
                    break;
                case 2:
                    intent = new Intent(this, PongActivity.class);
                    break;
                case 3:
                    intent = new Intent(this, MemorySoloActivity.class);
                    break;
                case 4:
                    intent = new Intent(this, LoginActivity.class);
                    break;
                case 5:
                    intent = new Intent(this, LoginActivity.class);
                    break;


                default:
                    intent = new Intent(this, LoginActivity.class);
                    break;
            }
            list.remove(game);
            startActivity(intent);
        }
    }

    public static void setmScoreJ1(int mScoreJ1) {
        ScoreActivity.mScoreJ1 = mScoreJ1;
    }

    public static void setmScoreJ2(int mScoreJ2) {
        ScoreActivity.mScoreJ2 = mScoreJ2;
    }

    public static void addmScoreTotJ1() {
        ScoreActivity.mScoreTotJ1 += mScoreJ1;
    }

    public static void addmScoreTotJ2() {
        ScoreActivity.mScoreTotJ2 += mScoreJ2;
    }

    public void endgame() {
        if (MainActivity.isMulti()) {
            MainActivity.addMultiScore(mScoreTotJ1,MainActivity.getCurrentUser());
        }
        else{
            MainActivity.addSoloScore(mScoreTotJ1,MainActivity.getCurrentUser());
        }
        this.finish();
    }
}
