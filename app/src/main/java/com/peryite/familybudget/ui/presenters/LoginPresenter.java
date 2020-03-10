package com.peryite.familybudget.ui.presenters;

import android.content.Context;
import android.util.Log;

import com.peryite.familybudget.ui.contracts.LoginContract;
import com.peryite.familybudget.ui.models.Login;
import com.peryite.familybudget.ui.views.BudgetActivity;
import com.peryite.familybudget.ui.views.CategoryItemActivity;

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = this.getClass().getSimpleName();

    private LoginContract.View view;
    private Context context;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onForgotPassword() {
        Log.d(TAG, "onForgotPassword: ");
        //TODO forgot password
        view.showMessage("onForgotPassword: placeholder");
    }

    @Override
    public void onSignIn(String username, String password) {
        Log.d(TAG, "onSignIn: ");

        Login login = new Login(username, password);
        view.doSignIn(login);
    }

    @Override
    public void onClickCreateNewAccount() {
        Log.d(TAG, "onClickCreateNewAccount: ");

        view.doCreateAccount();
    }

    @Override
    public void signInSuccessful() {
        view.startActivity(context, BudgetActivity.class);
    }

    @Override
    public void signInFailure() {

    }
}
