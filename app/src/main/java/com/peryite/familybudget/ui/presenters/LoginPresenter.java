package com.peryite.familybudget.ui.presenters;

import android.util.Log;

import com.peryite.familybudget.ui.contracts.LoginContract;

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
        view.doSignIn();
        view.showMessage("onSignIn: placeholder");
    }

    @Override
    public void onClickCreateNewAccount() {
        Log.d(TAG, "onClickCreateNewAccount: ");
        //TODO create new account
        view.showMessage("onClickCreateNewAccount: placeholder");
        view.doCreateAccount();
    }
}
