package com.peryite.familybudget.ui.contract;

import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

public interface LoginContract {
    interface Model {

    }

    interface View extends BaseView {
        void doSignIn();

        void disableElements();

        void enableElements();

        void doCreateAccount();
    }

    interface Presenter extends BasePresenter {
        void onForgotPassword();

        void onSignIn(String email, String password);

        void onSignInWithGoogle(String email, String password);

        void onCreateNewAccount();
    }
}
