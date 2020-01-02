package com.martin.promob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.martin.promob.model.MemoryAdapter;
import com.martin.promob.model.MemoryCard;

import java.util.ArrayList;
import java.util.Collections;

import static com.martin.promob.QuizzActivity.BUNDLE_STATE_SCORE;


public class MemoriesMultiActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<MemoryCard> lCard = new ArrayList<>();
    int nbCardDiscover;
    int nbPair;
    String jRouge;
    String jBleu;
    int scoreRouge;
    int scoreBleu;
    String gagnant;
    String joueurCourant;
    ImageView imJRouge;
    ImageView imJBleu;
    TextView scoreJRouge;
    TextView scoreJBleu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_memory);

        memoryBank();
        nbCardDiscover = 0;
        nbPair = 3;

        scoreBleu = 0;
        scoreRouge = 0;

        scoreJRouge = findViewById(R.id.scorej2);
        scoreJBleu = findViewById(R.id.scorej1);

        imJRouge = findViewById(R.id.imagerouge);
        imJBleu = findViewById(R.id.imagebleu);

        //Recupérer les joueurs
        jBleu = MainActivity.getCurrentUser().getFirstname();
        jRouge = MainActivity.getCurrentUser2().getFirstname();

        imJBleu.setImageResource(R.drawable.noir);
        imJRouge.setImageResource(R.drawable.rouge);

        scoreJBleu.setText("Score: 0");
        scoreJRouge.setText("Score: 0");


        joueurCourant = jRouge;


        TextView titre = findViewById(R.id.memory_text_view);

        GridView gridView = (GridView) findViewById(R.id.gridview);

        final MemoryAdapter memoryadapter = new MemoryAdapter(this, lCard);

        gridView.setAdapter(memoryadapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                MemoryCard card = lCard.get(position);


                if (card.isFind() == false) {

                    if (nbCardDiscover == 1) {
                        card.setDiscover(true);
                        memoryadapter.notifyDataSetChanged();
                        //delay
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                trouver();
                                for (MemoryCard c : lCard) {
                                    if (c.isFind() == false) {
                                        c.setDiscover(false);
                                    }
                                }
                                memoryadapter.notifyDataSetChanged();
                                nbCardDiscover = 0;
                            }
                        }, 1000);
                    }
                    if (nbCardDiscover == 0) {
                        card.setDiscover(true);
                        memoryadapter.notifyDataSetChanged();
                        nbCardDiscover++;
                    }
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

    }

    public void memoryBank() {
        lCard.add(new MemoryCard("Banane", R.drawable.banane));
        lCard.add(new MemoryCard("Banane", R.drawable.banane));
        lCard.add(new MemoryCard("Pomme", R.drawable.pomme));
        lCard.add(new MemoryCard("Pomme", R.drawable.pomme));
        lCard.add(new MemoryCard("Poire", R.drawable.poire));
        lCard.add(new MemoryCard("Poire", R.drawable.poire));
        Collections.shuffle(lCard);
    }

    public void trouver() {
        ArrayList<MemoryCard> pair = new ArrayList<MemoryCard>();

        for (MemoryCard c : lCard) {
            if (c.isFind() == false) {
                if (c.isDiscover()) {
                    pair.add(c);
                }
            }
        }
        if (pair.get(0).getName().equals(pair.get(1).getName())) {
            pair.get(0).setFind(true);
            pair.get(1).setFind(true);

            if (joueurCourant == jRouge) {
                scoreRouge++;
                scoreJRouge.setText("Score: " + scoreRouge);

            }
            if (joueurCourant == jBleu) {
                scoreBleu++;
                scoreJBleu.setText("Score: " + scoreBleu);

            }
            if (scoreBleu + scoreRouge == nbPair) {
                System.out.println("Gagner");
                if (scoreRouge <= scoreBleu) {
                    gagnant = jBleu;
                } else {
                    gagnant = jRouge;
                }
                endgame();
            }
        } else {
            changementJoueur();
        }
    }


    public void changementJoueur() {
        if (joueurCourant == jRouge) {
            System.out.println("Rouge");
            joueurCourant = jBleu;
            imJBleu.setImageResource(R.drawable.bleu);
            imJRouge.setImageResource(R.drawable.noir);

        } else {
            System.out.println("Bleu");
            joueurCourant = jRouge;
            imJBleu.setImageResource(R.drawable.noir);
            imJRouge.setImageResource(R.drawable.rouge);
        }
    }

    public void endgame() {

        if (TypeActivity.compet) {
            ScoreActivity.setmScoreJ1(scoreBleu);
            ScoreActivity.addmScoreTotJ1();
            ScoreActivity.setmScoreJ2(scoreRouge);
            ScoreActivity.addmScoreTotJ2();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done!")
                    .setMessage("The winners is " + gagnant + "\n" + jRouge + " : " + scoreRouge + "\n" + jBleu + " : " + scoreBleu)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // End the activity
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_STATE_SCORE, scoreBleu);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }
        this.finish();
    }

}
