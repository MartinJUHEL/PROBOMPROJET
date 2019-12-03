package com.martin.promob.model;

import java.util.List;

public class Question {
    ///texte de la question
private String mQuestion ;

    //liste des reponses possibles
private List<String> mChoiceList;

    // index de la bonne reponse
private int mAnswerIndex;

        public Question(String question, List<String> choiceList, int answerIndex) {
            this.setQuestion(question);
            this.setChoiceList(choiceList);
            this.setAnswerIndex(answerIndex);
        }

        public String getQuestion() {
            return mQuestion;
        }

        public void setQuestion(String question) {
            mQuestion = question;
        }

        public List<String> getChoiceList() {
            return mChoiceList;
        }

        public void setChoiceList(List<String> choiceList) {
            if (choiceList == null) {
                throw new IllegalArgumentException("Erreur, il faut ajouter des questions");
            }
            mChoiceList = choiceList;
        }

        public int getAnswerIndex() {
            return mAnswerIndex;
        }

        public void setAnswerIndex(int answerIndex) {
            if (answerIndex < 0 || answerIndex >= mChoiceList.size()) {
                throw new IllegalArgumentException("Erreur dans retour de reponse");
            }

            mAnswerIndex = answerIndex;
        }

        @Override
        public String toString() {
            return "Question{" +
                    "mQuestion='" + mQuestion + '\'' +
                    ", mChoiceList=" + mChoiceList +
                    ", mAnswerIndex=" + mAnswerIndex +
                    '}';
        }
    }
