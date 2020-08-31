package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.entities.Login;

public interface LoginContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {
        void doSignIn();

        void doSignIn(Login login);

        void doCreateAccount();

        boolean isFieldsValid();
    }

    interface Presenter extends BasePresenter {
        void onClickForgotPassword();

        void onClickSignIn(String email, String password);

        void onClickCreateNewAccount();

        void signInSuccessful();

        void signInFailure();
    }
}