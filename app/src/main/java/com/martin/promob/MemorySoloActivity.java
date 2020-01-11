package com.martin.promob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.martin.promob.model.MemoryAdapter;
import com.martin.promob.model.MemoryCard;

import java.util.ArrayList;
import java.util.Collections;

import static com.martin.promob.QuizzActivity.BUNDLE_STATE_SCORE;

public class MemorySoloActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<MemoryCard> lCard = new ArrayList<>();
    int nbCardDiscover;
    int nbPair;
    String jBleu;
    int score;
    TextView scoreView;
    boolean debut;

    private MediaPlayer music;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_memory);

        LoginActivity.appTheme.stop();
        LoginActivity.appTheme.release();

        music = MediaPlayer.create(getApplicationContext(), R.raw.jeditheme);
        music.setLooping(true);
        music.start();

        memoryBank();
        nbCardDiscover = 0;
        nbPair = lCard.size() / 2;

        score = 0;
        scoreView = findViewById(R.id.scorej1);

        //Recupérer les joueurs
        jBleu = "jbleu";


        scoreView.setText("Score: 0");

        TextView titre = findViewById(R.id.memory_text_view);

        GridView gridView = (GridView) findViewById(R.id.gridview);

        final MemoryAdapter memoryadapter = new MemoryAdapter(this, lCard);

        gridView.setAdapter(memoryadapter);

        final Handler handler = new Handler();

        debut = true;


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                MemoryCard card = lCard.get(position);

                if (debut) {

                    for (MemoryCard c : lCard) {
                        c.setDiscover(true);
                        memoryadapter.notifyDataSetChanged();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (MemoryCard c : lCard) {
                                c.setDiscover(false);
                            }
                            memoryadapter.notifyDataSetChanged();
                            debut = false;
                        }
                    }, 2000);


                } else {

                    if (card.isFind() == false) {

                        if (nbCardDiscover == 1) {
                            card.setDiscover(true);
                            memoryadapter.notifyDataSetChanged();
                            //delay
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
                            }, 500);
                        }
                        if (nbCardDiscover == 0) {
                            card.setDiscover(true);
                            memoryadapter.notifyDataSetChanged();
                            nbCardDiscover++;
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
        music.release();
    }


    @Override
    public void onClick(View v) {

    }

    public void memoryBank() {
        //lCard.add(new MemoryCard("Robot6", R.drawable.robot6));
        //lCard.add(new MemoryCard("Robot6", R.drawable.robot6));
        //lCard.add(new MemoryCard("Robot5", R.drawable.robot5));
        //lCard.add(new MemoryCard("Robot5", R.drawable.robot5));
        //lCard.add(new MemoryCard("Robot4", R.drawable.robot4));
        //lCard.add(new MemoryCard("Robot4", R.drawable.robot4));
        lCard.add(new MemoryCard("Robot3", R.drawable.robot3));
        lCard.add(new MemoryCard("Robot3", R.drawable.robot3));
        lCard.add(new MemoryCard("Robot2", R.drawable.robot2));
        lCard.add(new MemoryCard("Robot2", R.drawable.robot2));
        lCard.add(new MemoryCard("Robot1", R.drawable.robot1));
        lCard.add(new MemoryCard("Robot1", R.drawable.robot1));
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
            score++;
            scoreView.setText("Score: " + score);

            if (score == nbPair) {
                endgame();
            }

        } else {
            endgame();
        }
    }


    public void endgame() {


        if (TypeActivity.compet) {
                ScoreActivity.setmScoreJ1(score);
                ScoreActivity.addmScoreTotJ1();
            this.finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Finish")
                    .setMessage("Score =" + score)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // End the activity
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_STATE_SCORE, score);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }
    }
}
