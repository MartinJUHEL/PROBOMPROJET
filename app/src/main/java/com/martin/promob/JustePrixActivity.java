package com.martin.promob;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class JustePrixActivity extends AppCompatActivity {

    private EditText txtNumber;
    private TextView resultat;
    private TextView history;
    private Button compare;
    private ProgressBar pgbScore;

    private int justeprix;
    private int mScore;

    private MediaPlayer music;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juste_prix);

        LoginActivity.appTheme.stop();
        LoginActivity.appTheme.release();

        music= MediaPlayer.create(getApplicationContext(), R.raw.cantina);
        music.setLooping(true);
        music.start();

        txtNumber = null;
        compare = findViewById(R.id.btnCompare);
        txtNumber = findViewById(R.id.txtNumber);
        resultat = findViewById(R.id.lblResult);
        history = findViewById(R.id.lblHistory);
        pgbScore = findViewById(R.id.pgbNumberAct);


        init();


    }
    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
        music.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }

    public void init() {

        justeprix = new Random().nextInt(100) + 1; //valeur que l'on doit trouver
        mScore = 7; //score du joueur

        txtNumber.setText("");
        resultat.setText(""); //pas de resultat au debut
        history.setText(""); //pas d'historique de recherche au debut
        pgbScore.setProgress(mScore);
        pgbScore.setMax(7);

        txtNumber.requestFocus(); // ouvre directement le clavier pour saisir la valeur

    }

    public void compare(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String txtRecup = txtNumber.getText().toString(); //l'utilisateur peut saisir du vide donc considerer comme une string

        if (txtRecup.equals("")) {
            return;
        } //si la chaine recup est vide on fait rien

        int nombreRecup = Integer.parseInt(txtRecup);


        if (mScore > 0) {
            if (nombreRecup == justeprix) {
                if (TypeActivity.compet) {
                    ScoreActivity.setmScoreJ1(mScore);
                    ScoreActivity.addmScoreTotJ1();
                    this.finish();
                } else {
                    builder.setTitle("Félicitations !");
                    builder.setMessage("Le score de " + MainActivity.getCurrentUser().getFirstname() + " est de " + mScore);
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
            } else {
                history.append(nombreRecup + "\r\n");
                pgbScore.incrementProgressBy(-1);
                mScore--;

                if (nombreRecup > justeprix) {
                    resultat.setText(R.string.ResaisirJustePrixPlusPetit);

                } else {
                    resultat.setText(R.string.ResaisirJustePrixPlusGrand);

                }
                txtNumber.setText("");


            }
        } else if (mScore == 0) {

            if (TypeActivity.compet) {
                this.finish();
            } else {

                builder.setTitle("Perdu !");
                builder.setMessage("Le score de " + MainActivity.getCurrentUser().getFirstname() + " est de " + mScore + "\n" + "La valeur était de " + justeprix);
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
        }


    }


}
