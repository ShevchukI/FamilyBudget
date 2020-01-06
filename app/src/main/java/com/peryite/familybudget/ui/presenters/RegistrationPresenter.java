package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.ui.contracts.RegistrationContract;
import com.peryite.familybudget.ui.models.User;

import java.util.Map;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    private RegistrationContract.View view;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
    }

    @Override
    public void onClickRegistration() {
        if (view.isFieldsValid()) {
            User user = fillUserFromFields(view.getFieldsValue());

        }
    }

    private User fillUserFromFields(Map<String, Object> values) {
        User user = new User();
        user.setFirstName((String) values.get("firstName"));
        user.setLastName((String) values.get("lastName"));
        user.setUsername((String) values.get("username"));
        user.setEmail((String) values.get("email"));
        user.setPassword((String) values.get("password"));

        return user;
    }
}
