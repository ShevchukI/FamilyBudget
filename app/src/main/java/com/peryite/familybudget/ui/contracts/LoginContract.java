package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.models.Login;

public interface LoginContract {
    interface Model {

    }

    interface View extends BaseView {
        void doSignIn();

        void doSignIn(Login login);

        void doCreateAccount();

        boolean isFieldsValid();
    }

    interface Presenter extends BasePresenter {
        void onForgotPassword();

        void onSignIn(String email, String password);

        void onClickCreateNewAccount();

        void signInSuccessful();

        void signInFailure();
    }
}