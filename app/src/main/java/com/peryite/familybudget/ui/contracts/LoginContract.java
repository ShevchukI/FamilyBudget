package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

public interface LoginContract {
    interface Model {

    }

    interface View extends BaseView {
        void doSignIn();

        void doCreateAccount();
    }

    interface Presenter extends BasePresenter {
        void onForgotPassword();

        void onSignIn(String email, String password);

        void onClickCreateNewAccount();
    }
}