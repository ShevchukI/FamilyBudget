package com.peryite.familybudget.ui.presenters;

import android.util.Log;

import com.peryite.familybudget.entities.Login;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.RegistrationContract;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.OnAPIRegistrationRequestListener;

import java.util.Map;

public class RegistrationPresenter implements RegistrationContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    private RegistrationContract.View view;
    private RegistrationContract.Model model;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
    }

    public RegistrationPresenter(RegistrationContract.Model model) {
        model.setListener(new OnAPIRegistrationRequestListener() {
            @Override
            public void errorMessage(String text) {
                view.hideProgress();
                view.enableElements(true);

                view.showMessage(text);
            }

            @Override
            public void onResponse() {
                registrationSuccessful();
            }

            @Override
            public void onFailure() {
                registrationFailure();
            }
        });

        this.model = model;
    }

    @Override
    public void onClickRegistration() {
        if (view.isFieldsValid()) {
            Login login = fillUserFromFields(view.getFieldsValue());

            view.enableElements(false);
            view.showProgress();

            model.registerNewUser(login);
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

    private Login fillUserFromFields(Map<String, Object> values) {
        Login login = new Login();
        //  user.setFirstName((String) values.get("firstName"));
        //  user.setLastName((String) values.get("lastName"));
        login.setUsername((String) values.get("username"));
        //   user.setEmail((String) values.get("email"));
        login.setPassword((String) values.get("password"));

        return login;
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
