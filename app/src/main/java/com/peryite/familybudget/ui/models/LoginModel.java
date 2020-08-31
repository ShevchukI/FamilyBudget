package com.peryite.familybudget.ui.models;

import android.content.SharedPreferences;

import com.peryite.familybudget.ui.contracts.LoginContract;

public class LoginModel implements LoginContract.Model {
    private SharedPreferences preferencesCredential;
    private SharedPreferences preferencesHasVisited;

    public LoginModel(SharedPreferences preferencesCredential, SharedPreferences preferencesHasVisited) {
        this.preferencesCredential = preferencesCredential;
        this.preferencesHasVisited = preferencesHasVisited;
    }


    @Override
    public boolean isSignInSuccessful(String username, String password) {
        return false;
    }
}
