package com.peryite.familybudget.ui.models;

import android.content.SharedPreferences;

import com.peryite.familybudget.ui.contracts.LoginContract;

public class LoginModel implements LoginContract.Model {
    private SharedPreferences preferencesCredential;

    public LoginModel(SharedPreferences preferencesCredential){
        this.preferencesCredential = preferencesCredential;
    }
}
