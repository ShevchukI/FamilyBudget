package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.entities.User;

import java.util.Map;

public interface RegistrationContract {

    interface Model {

    }

    interface View extends BaseView {

        void showProgress();

        void hideProgress();

        boolean isFieldsValid();

        Map<String, Object> getFieldsValue();

        boolean registerNewUser(User user);

    }

    interface Presenter extends BasePresenter {
        void onClickRegistration();

        void registrationSuccessful();

        void registrationFailure();
    }
}
