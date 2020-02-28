package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.models.User;

import java.util.List;
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
