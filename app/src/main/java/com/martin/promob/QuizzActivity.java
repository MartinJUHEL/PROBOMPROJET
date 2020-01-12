package com.martin.promob;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.martin.promob.model.Question;
import com.martin.promob.model.QuestionBank;

import java.util.Arrays;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener {

    private int mScore;

    private TextView questionTextView;
    private Button answer1;
    private Button answer2;
    private Button answer3;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    //nombre de questions
    private int mNumberOfQuestions;

    private int mNumberOfQuestionsInitial;


    //public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";

    private boolean mEnableTouchEvents;

    private MediaPlayer music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        LoginActivity.appTheme.stop();
        LoginActivity.appTheme.release();

        music = MediaPlayer.create(getApplicationContext(), R.raw.jeditheme);
        music.setLooping(true);
        music.start();

        mNumberOfQuestionsInitial = 5;
        mNumberOfQuestions = mNumberOfQuestionsInitial;
        mScore = 0;

        questionTextView = (TextView) findViewById(R.id.activity_game_question1);
        answer1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        answer2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        answer3 = (Button) findViewById(R.id.activity_game_answer3_btn);


        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);

        answer1.setTag(0);
        answer2.setTag(1);
        answer3.setTag(2);


        mQuestionBank = this.generateQuestions();


        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);


  /*      if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 2;
        }

   */

        mEnableTouchEvents = true;


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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            // Good answer
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            // Wrong answer
            Toast.makeText(this, "Mauvaise reponse!", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {

        if (TypeActivity.compet) {
            if (!MainActivity.isMulti()) {
                ScoreActivity.setmScoreJ1(mScore);
                ScoreActivity.addmScoreTotJ1();
            } else {
                if (ScoreActivity.isJoueur1end()) {
                    ScoreActivity.setmScoreJ2(mScore);
                    ScoreActivity.addmScoreTotJ2();
                } else {
                    ScoreActivity.setmScoreJ1(mScore);
                    ScoreActivity.addmScoreTotJ1();
                }
            }
            this.finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Bien joue!")
                    .setMessage("Ton score est de " + mScore + "/" + mNumberOfQuestionsInitial)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // End the activity
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_STATE_SCORE, mScore);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }
    }

    private void displayQuestion(final Question question) {
        questionTextView.setText(question.getQuestion());
        answer1.setText(question.getChoiceList().get(0));
        answer2.setText(question.getChoiceList().get(1));
        answer3.setText(question.getChoiceList().get(2));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("POURQUOI GEORGE LUCAS A T-IL COMMENCÉ PAR LES ÉPISODES IV, V ET VI ?",
                Arrays.asList("Parce qu'il ne pensait pas réaliser toute la trilogie", "Parce qu'il la pensait plus commerciale", "Parce que c'est la seule qu'il a été autorisé à faire"),
                1);

        Question question2 = new Question("COMMENT SE NOMME LE PÈRE DE BOBA FETT ?",
                Arrays.asList("Jango Fett", "Melo Fett", "Ango Fett"),
                0);

        Question question3 = new Question("A QUI A ÉTÉ CONFIÉ LEIA, FILLE DE ANAKIN SKYWALKER ET PADME AMIDALA ?",
                Arrays.asList("Au Sénateur Organa et son épouse", "A la famille Solo", "A Ower Lars, fils de Shmi Skywalker"),
                0);

        Question question4 = new Question("COMMENT ANAKIN EST LIBÉRÉ DE SON STATUT D'ESCLAVE ?",
                Arrays.asList("En étant acheté par Obi-Wan", "En gagnant la course de Boonta", "En se mariant avec Padme"),
                1);

        Question question5 = new Question("SUR QUELLE PLANÈTE A GRANDI LUKE SKYWALKER ?",
                Arrays.asList("Naboo", "Conruscant", "Tatooine"),
                2);

        Question question6 = new Question("QUEL EST LE NOM SITH DE PALPATINE ?",
                Arrays.asList("Dark Sidious", "Dark Maul", "Dark Vador"),
                0);

        Question question7 = new Question("DE QUELLE ESPÈCE FAIT PARTIE JAR JAR BINKS ?",
                Arrays.asList("Eworks", "Gungan", "Wookie"),
                1);

        Question question8 = new Question("DANS QUEL ÉPISODE PADME ET ANAKIN SE MARIENT-ILS EN SECRET ?",
                Arrays.asList("La menace fantôme", "L'attaque des Clones", "La revanche des Sith"),
                1);

        Question question9 = new Question("OÙ A LIEU LE COMBAT ENTRE YODA ET PALPATINE DANS L'ÉPISODE III ??",
                Arrays.asList("Au Senat", "A l'academie Jedi", "Dans le bureau de Palpatine"),
                0);

        Question question10 = new Question("POURQUOI LES EWOKS AIDENT-ILS FINALEMENT LES MEMBRES DE L'ALLIANCE REBELLE ?",
                Arrays.asList("Parce qu'ils pensent que C3PO est un dieu", "Pour protéger leur territoire", "Parce qu'ils y sont obligés par Luke"),
                0);


        return new QuestionBank(Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10
        ));
    }


}

