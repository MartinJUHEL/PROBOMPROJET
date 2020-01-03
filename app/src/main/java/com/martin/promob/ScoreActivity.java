package com.martin.promob;

import android.content.Context;
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
    public static int numberActivity2;


    private static List<Class> list;
    private static List<Class> list2;
    public static boolean joueur1end ; //joueur 1 a fini de jouer

    private Button next;
    private Button buttonJoueur2;

    private static View viewI;

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

        scorej2View.setText("Score : " + mScoreJ2);
        scorej2TotView.setText("Score total : " + mScoreTotJ2);

        buttonJoueur2 = findViewById(R.id.button_joueur2);
        buttonJoueur2.setActivated(false);
        buttonJoueur2.setVisibility(View.INVISIBLE);


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

        scorej2View.setText("Score : " + mScoreJ2);
        scorej2TotView.setText("Score total : " + mScoreTotJ2);
    }

    public static void initialise() {
        mScoreJ1 = 0;
        mScoreJ2 = 0;
        mScoreTotJ1 = 0;
        mScoreTotJ2 = 0;

        numberActivity = 3;
        numberActivity2 = 3;
        list = new ArrayList<Class>();
        list.add(QuizzActivity.class);
        list.add(EscapeActivity.class);
        list.add(PongActivity.class);
        list.add(MemorySoloActivity.class);

        list2 = new ArrayList<Class>();

        joueur1end = false;

    }

    public void next(View view) {
        next = findViewById(R.id.button_next);

        setView(view);
        mScoreJ1 = 0;
        mScoreJ2=0;
        if (numberActivity == 0) {

            if(MainActivity.isMulti()){
                setJoueur1end(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Joueur 2 a ton tour !");
                builder.setMessage("Le score de "+ MainActivity.getCurrentUser().getFirstname() +" est de " + mScoreTotJ1);
                builder.setPositiveButton("Pret", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // End the activity
                                runjoueur2(viewI);

                            }
                        }
                )
                        .setCancelable(false)
                        .create()
                        .show();

            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Fin de la partie !");
                builder.setMessage("Le score de "+MainActivity.getCurrentUser().getFirstname()+" est de " + mScoreTotJ1);
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
            }

            endgame();
        }
        else {
            numberActivity--;
            final int game = new Random().nextInt(list.size());

            Intent intent;

            intent = new Intent(this, list.get(game));

            list2.add(list.get(game));
            list.remove(game);
            startActivity(intent);
        }




    }



    public void runjoueur2(View view) {

        buttonJoueur2.setActivated(true);
        buttonJoueur2.setVisibility(View.VISIBLE);
        next.setActivated(false);
        next.setVisibility(View.INVISIBLE);

        mScoreJ2=0;

        if (numberActivity2 == 0) {
            String vainq ;
            if(mScoreTotJ2>mScoreTotJ1){
                vainq = MainActivity.getCurrentUser2().getFirstname();
            }
            else{
                if (mScoreTotJ2<mScoreTotJ1){
                    vainq = MainActivity.getCurrentUser().getFirstname();
                }
                else{
                    vainq = MainActivity.getCurrentUser().getFirstname() +" et "+MainActivity.getCurrentUser2().getFirstname();
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Fin de la partie !");
            builder.setMessage("Le score de "+MainActivity.getCurrentUser().getFirstname()+" est de " + mScoreTotJ1+ "\n"
                    +"Le score de "+ MainActivity.getCurrentUser2().getFirstname() +" est de " + mScoreTotJ2
                    +"\n"+ "Le(s) vainqueur(s) : "+ vainq );
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
        }
        else {
            numberActivity2--;

            Intent intent;

            intent = new Intent(this, list2.get(0));

            list2.remove(0);
            startActivity(intent);
        }




    }



    public static void setJoueur1end(boolean joueur1end) {
        ScoreActivity.joueur1end = joueur1end;
    }

    public static boolean isJoueur1end() {
        return joueur1end;
    }

    public void setView(View view){
        viewI=view;
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
            MainActivity.addMultiScore(mScoreTotJ1,MainActivity.getCurrentUser());
        }
        else {
            MainActivity.addSoloScore(mScoreTotJ1, MainActivity.getCurrentUser());
        }
    }
}
