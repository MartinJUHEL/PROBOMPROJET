package com.martin.promob.model;

import android.content.Intent;

import com.martin.promob.LoginActivity;

public class User {
    private String mUser;

    public String getFirstname() {
        return mUser;
    }

    public void setFirstname(String firstname) {
        mUser = firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUser='" + mUser + '\'' +
                '}';
    }
}
