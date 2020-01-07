package com.martin.promob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class JustePrixMultiActivity extends AppCompatActivity {

    private EditText txtNumber;
    private ImageView imageAdec;
    private TextView resultat;
    private TextView history;
    private TextView history2;
    private Button compare;
    private ProgressBar pgbScore;
    private ArrayList<Pair<Integer,Integer>>bDimage;

    private  int justeprix;
    private int nbActivite;

    private int number1;
    private int number2;

    private int score1; //score sur 7 points relatifs a l'ecart de valeur entre le juste pric et celui propose
    private int score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juste_prix_multi);

        txtNumber = null;
        compare = findViewById(R.id.btnCompare);
        txtNumber = findViewById(R.id.txtNumber);
        resultat = findViewById(R.id.lblResult);
        history = findViewById(R.id.lblHistory);
        history2 = findViewById(R.id.lblHistory2);
        pgbScore = findViewById(R.id.pgbNumberAct);
        imageAdec = findViewById(R.id.imageMystere);

        init();

    }

    public void initPicture(){

        bDimage = new ArrayList<Pair<Integer,Integer>>();

        bDimage.add(new Pair(R.drawable.laser,30));
        bDimage.add(new Pair(R.drawable.mask_vador,100));
        bDimage.add(new Pair(R.drawable.robot1,45));

        int pic = new Random().nextInt(bDimage.size());

        justeprix = bDimage.get(pic).second;
        imageAdec.setImageResource(bDimage.get(pic).first);

    }

    public void init(){

        nbActivite = 3; //nombre de parties

        txtNumber.setText("");
        resultat.setText(""); //pas de resultat au debut
        history.setText(MainActivity.getCurrentUser().getFirstname()+"\n"); //pas d'historique de recherche au debut
        history2.setText(MainActivity.getCurrentUser2().getFirstname()+ "\n"); //pas d'historique de recherche au debut
        pgbScore.setProgress(nbActivite);
        pgbScore.setMax(nbActivite);

        initPicture();

        number1 = -1;
        number2 =-1;

        resultat.setText(MainActivity.getCurrentUser().getFirstname()+" à toi de commencer");


    }

    public void compare(View view){

        String txtRecup = txtNumber.getText().toString(); //l'utilisateur peut saisir du vide donc considerer comme une string
        if(txtRecup.equals("")){ return;} //si la chaine recup est vide on fait rien
        int nombreRecup = Integer.parseInt(txtRecup);


        if(number1==-1){
            number1=nombreRecup;
            history.append(nombreRecup + "\r\n");
            resultat.setText(MainActivity.getCurrentUser2().getFirstname()+" à toi de jouer");
            txtNumber.setText("");


        }else{
            number2 = nombreRecup;
            history.append(nombreRecup + "\r\n");
            txtNumber.setText("");
        }

        if(number2!=-1 && number1!=-1){
            comparerPrix();
        }
    }

    public void comparerPrix(){

        int vainq;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        int diff1 = Math.abs(justeprix-number1);
        int diff2 = Math.abs(justeprix-number2);

        if(diff1<diff2){
            builder.setTitle("Félicitations "+ MainActivity.getCurrentUser().getFirstname()+" !");
            score1 = number1*7/justeprix;
            score2 = 0;
        }
        else{
            if(diff1>diff2){
                builder.setTitle("Félicitations "+ MainActivity.getCurrentUser2().getFirstname()+" !");
                score2 = number2*7/justeprix;
                score1 = 0;

            } else{
                builder.setTitle("Les deux joueurs ont gagné");
                score1 = number1*7/justeprix;
                score2 = score1;
            }
        }


        if (TypeActivity.compet) {
            ScoreActivity.setmScoreJ1(score1);
            ScoreActivity.addmScoreTotJ1();
            ScoreActivity.setmScoreJ2(score2);
            ScoreActivity.addmScoreTotJ2();
        }


        builder.setMessage("Le prix exacte était de "+ justeprix);
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
