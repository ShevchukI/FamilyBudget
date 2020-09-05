package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.entities.Login;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.entities.User;

import java.util.Map;

public interface RegistrationContract {

    interface Model extends BaseModel {
        void registerNewUser(Login login);

        void requestAlexaCode(Login login);
    }

    interface View extends BaseView {

        void showProgress();

        void hideProgress();

        boolean isFieldsValid();

        Map<String, Object> getFieldsValue();

        void showDialog(String title, String message);

    }

    interface Presenter extends BasePresenter {
        void onClickRegistration();

        void registrationSuccessful();

        void registrationFailure();
    }
}
