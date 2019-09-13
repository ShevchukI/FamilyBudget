package com.peryite.familybudget.ui.presenter;

import android.util.Log;

import com.peryite.familybudget.ui.contract.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = this.getClass().getSimpleName();

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void onForgotPassword() {
        Log.d(TAG, "onForgotPassword: ");
        //TODO forgot password
        view.showMessage("onForgotPassword: placeholder");
    }

    @Override
    public void onSignIn(String email, String password) {
        Log.d(TAG, "onSignIn: ");
        //TODO sign in
        view.showMessage("onSignIn: placeholder");
    }

    @Override
    public void onSignInWithGoogle(String email, String password) {
        Log.d(TAG, "onSignInWithGoogle: ");
        //TODO sign in with Google
        view.showMessage("onSignInWithGoogle: placeholder");
    }

    @Override
    public void onCreateNewAccount() {
        Log.d(TAG, "onCreateNewAccount: ");
        //TODO create new account
        view.showMessage("onCreateNewAccount: placeholder");
    }
}
