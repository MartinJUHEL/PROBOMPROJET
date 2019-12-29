package com.martin.promob.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class User {
    private String userLogin;


    //Map de jeu,score dans chaque jeu
    private ArrayList<Integer> scores;

    public User(String userLogin){
        this.userLogin=userLogin;
        ArrayList<Integer> score=new ArrayList<>();
        this.scores=new ArrayList<>();
    }

    public String getFirstname() {
        return userLogin;
    }

    public void setFirstname(String firstname) {
        userLogin = firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUser='" + userLogin + '\'' +
                '}';
    }

    public void addScore(Integer score){
            scores.add(score);
            Collections.sort(scores, Collections.reverseOrder());


    }
}
