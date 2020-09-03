package com.peryite.familybudget.ui.presenters;

import android.content.Context;
import android.util.Log;

import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.LoginContract;
import com.peryite.familybudget.entities.Login;
import com.peryite.familybudget.ui.listeners.OnAPIUserRequestListener;
import com.peryite.familybudget.ui.views.BudgetActivity;

import java.util.List;

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = this.getClass().getSimpleName();

    private LoginContract.View view;
    private LoginContract.Model model;
    private Context context;

    public LoginPresenter(LoginContract.Model model) {
        model.setListener(new OnAPIUserRequestListener() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure() {
                view.showMessage("Fail!");
            }

            @Override
            public void setUser(User user) {
                if (user != null) {
                    //TODO: add saving hasVisited state and do login
                    view.showMessage(user.toString());
                } else {
                    view.showMessage("User not found!");
                }
            }

            @Override
            public void setUsers(List<User> users) {

            }
        });

        this.model = model;
    }

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onClickForgotPassword() {
        Log.d(TAG, "onForgotPassword: ");
        //TODO forgot password
        view.showMessage("onForgotPassword: placeholder");
    }

    @Override
    public void onClickSignIn(String login, String password, boolean rememberMe) {
        Log.d(TAG, "onSignIn: ");

        model.getUser(login, password);
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

    @Override
    public void userCheckResult(User user) {
        if (user != null) {
            view.showMessage(user.toString());
        } else {
            view.showMessage("User is Null. Something wrong!");
        }
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (LoginContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
