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
    private Map<String, ArrayList<Integer>> scores;

    public User(String userLogin){
        this.userLogin=userLogin;
        ArrayList<Integer> score=new ArrayList<>();
        this.scores=new HashMap<>();
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

    public void addScore(String jeu,Integer score){
        if(scores.containsKey(jeu)){
            scores.get(jeu).add(score);
        }else{
            ArrayList<Integer> l =new ArrayList<>();
            l.add(score);
            scores.put(jeu,l);
            Collections.sort(scores.get(jeu), Collections.reverseOrder());

        }
    }
}
