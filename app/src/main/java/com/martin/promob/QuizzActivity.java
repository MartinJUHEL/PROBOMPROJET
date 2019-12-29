package com.martin.promob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;


import com.martin.promob.model.Question;
import com.martin.promob.model.QuestionBank;

import java.util.Arrays;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener {

    private int mScore;

    private TextView questionTextView ;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    //nombre de questions
    private int mNumberOfQuestions;

    private int mNumberOfQuestionsInitial;


    //public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        mNumberOfQuestionsInitial = 5;
        mNumberOfQuestions = mNumberOfQuestionsInitial;
        mScore = 0;

        questionTextView = (TextView) findViewById(R.id.activity_game_question1);
        answer1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        answer2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        answer3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        answer4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);

        answer1.setTag(0);
        answer2.setTag(1);
        answer3.setTag(2);
        answer4.setTag(3);


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien joue!")
                .setMessage("Ton score est de " + mScore + "/" + mNumberOfQuestionsInitial )
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

    private void displayQuestion(final Question question) {
        questionTextView.setText(question.getQuestion());
        answer1.setText(question.getChoiceList().get(0));
        answer2.setText(question.getChoiceList().get(1));
        answer3.setText(question.getChoiceList().get(2));
        answer4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Combien font 4x8?",
                Arrays.asList("20", "26", "32","44"),
                2);

        Question question2 = new Question("L'Europe est composée de combien de pays ?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Quand fut le premier Homme sur la Lune ?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question4 = new Question("Quelle est la capitale de la Roumanie ?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question5 = new Question("Que fête-t-on le premier mai ?",
                Arrays.asList("Le travail", "Le printemps", "Les mamans", "Le Beaujolais"),
                0);

        Question question6 = new Question("Qui est l'inséparable compagnon de Titi ?",
                Arrays.asList("Grosminet", "Toto", "Tac", "Tom"),
                0);

        Question question7 = new Question("En quelle année, la V République est-elle rentrée en vigueur ?",
                Arrays.asList("1878", "1905", "1958", "1962"),
                2);


        return new QuestionBank(Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7
        ));
    }


}

