package com.peryite.familybudget.ui.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.R;
import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.contracts.RegistrationContract;
import com.peryite.familybudget.ui.models.RegistrationModel;
import com.peryite.familybudget.ui.presenters.RegistrationPresenter;
import com.peryite.familybudget.utils.RegEx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity implements RegistrationContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.et_username)
    AppCompatEditText username;
    @BindView(R.id.tv_username_error)
    AppCompatTextView usernameError;

    @BindView(R.id.et_password)
    AppCompatEditText password;
    @BindView(R.id.tv_password_error)
    AppCompatTextView passwordError;

    @BindView(R.id.et_password_confirm)
    AppCompatEditText passwordConfirm;
    @BindView(R.id.tv_password_confirm_error)
    AppCompatTextView passwordConfirmError;

    @BindView(R.id.chb_enable_alexa)
    AppCompatCheckBox enableAlexa;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.registration_create)
    AppCompatButton registration;

    private RegistrationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }

    private void init(){
        unbinder = ButterKnife.bind(this);

        usernameError.setText(getString(R.string.format_empty_field, username.getHint().toString()));
        passwordError.setText(getString(R.string.format_empty_field, password.getHint().toString()));
        passwordConfirmError.setText(getString(R.string.format_incorrect_value, passwordConfirm.getHint().toString()));

        elements = fillElementList();

        RegistrationModel registrationModel = new RegistrationModel();
        presenter = new RegistrationPresenter(registrationModel);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        presenter.detachView();
    }

    @Override
    public void showProgress() {
        showProgress(progressBar);
    }

    @Override
    public void hideProgress() {
        hideProgress(progressBar);
    }

    @OnClick(R.id.registration_create)
    public void onClickRegistration() {
        presenter.onClickRegistration();
    }

    private List<View> fillElementList() {
        List<View> views = new ArrayList<>();
        views.add(username);
        views.add(password);
        views.add(enableAlexa);
        return views;
    }

    public boolean isFieldsValid() {
        boolean isValid = true;
        if (!isFieldNotEmpty(username, usernameError)) {
            isValid = false;
        }

        if (!isPasswordValid()) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public Map<String, Object> getFieldsValue() {
        Map<String, Object> values = new HashMap<>();
        values.put("username", username.getText().toString());
        values.put("password", passwordConfirm.getText().toString());
        values.put("enableAlexa", enableAlexa.isChecked());

        return values;
    }

    @Override
    public void showDialog(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeActivity();
            }
        });

        alertDialog.show();
    }

    @Override
    public void disableElements() {
        username.setEnabled(false);
        usernameError.setEnabled(false);
        password.setEnabled(false);
        passwordError.setEnabled(false);
        passwordConfirm.setEnabled(false);
        passwordConfirmError.setEnabled(false);
        enableAlexa.setEnabled(false);
        registration.setEnabled(false);
    }

    @Override
    public void enableElements() {
        username.setEnabled(true);
        usernameError.setEnabled(true);
        password.setEnabled(true);
        passwordError.setEnabled(true);
        passwordConfirm.setEnabled(true);
        passwordConfirmError.setEnabled(true);
        enableAlexa.setEnabled(true);
        registration.setEnabled(true);
    }

    @Override
    public void enableElements(boolean enabled) {
        username.setEnabled(enabled);
        usernameError.setEnabled(enabled);
        password.setEnabled(enabled);
        passwordError.setEnabled(enabled);
        passwordConfirm.setEnabled(enabled);
        passwordConfirmError.setEnabled(enabled);
        enableAlexa.setEnabled(enabled);
        registration.setEnabled(enabled);
    }

    private boolean isFieldNotEmpty(AppCompatEditText editText, AppCompatTextView errorTextView) {
        if (editText.getText().toString().equals("")) {
            errorTextView.setVisibility(View.VISIBLE);
            return false;
        } else {
            errorTextView.setVisibility(View.GONE);
            return true;
        }
    }


    private boolean isPasswordValid() {
        boolean isValid = true;
        String passwordText = password.getText().toString();
        String passwordConfirmText = passwordConfirm.getText().toString();
        if (passwordText.matches(RegEx.WITHOUT_SPACE.getFullName()) && !passwordText.equals("")) {
            passwordError.setVisibility(View.GONE);
        } else {
            passwordError.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (passwordConfirmText.equals(password.getText().toString())) {
            passwordConfirmError.setVisibility(View.GONE);
        } else {
            passwordConfirmError.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid;
    }

}
