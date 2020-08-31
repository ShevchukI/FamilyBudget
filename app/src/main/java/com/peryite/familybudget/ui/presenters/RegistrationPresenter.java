package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.RegistrationContract;
import com.peryite.familybudget.entities.User;

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

            view.enableElements(false);
            view.showProgress();

            view.registerNewUser(user);
        }
    }

    @Override
    public void registrationSuccessful() {
        view.hideProgress();
        view.enableElements(true);

        view.showMessage("Successful");

        view.closeActivity();
    }

    @Override
    public void registrationFailure() {
        view.hideProgress();
        view.enableElements(true);

        view.showMessage("Failure");
    }

    private User fillUserFromFields(Map<String, Object> values) {
        User user = new User();
      //  user.setFirstName((String) values.get("firstName"));
      //  user.setLastName((String) values.get("lastName"));
        user.setUsername((String) values.get("username"));
     //   user.setEmail((String) values.get("email"));
//        user.setPassword((String) values.get("password"));

        return user;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (RegistrationContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
