package com.martin.promob.model;


import java.util.Collections;
import java.util.List;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    //fonction pour avoir les questions
    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;

        //lance aleatoire les questions
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex = 0;
    }


    public Question getQuestion() {
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }

        // Please note the post-incrementation
        return mQuestionList.get(mNextQuestionIndex++);
    }
}

