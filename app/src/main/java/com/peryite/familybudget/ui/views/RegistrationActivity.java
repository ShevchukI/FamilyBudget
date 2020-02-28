package com.peryite.familybudget.ui.views;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.R;
import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.ui.contracts.RegistrationContract;
import com.peryite.familybudget.ui.models.User;
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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity implements RegistrationContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.et_first_name)
    AppCompatEditText firstName;
    @BindView(R.id.tv_first_name_error)
    AppCompatTextView firstNameError;

    @BindView(R.id.et_last_name)
    AppCompatEditText lastName;
    @BindView(R.id.tv_last_name_error)
    AppCompatTextView lastNameError;

    @BindView(R.id.et_username)
    AppCompatEditText username;
    @BindView(R.id.tv_username_error)
    AppCompatTextView usernameError;

    @BindView(R.id.et_email)
    AppCompatEditText email;
    @BindView(R.id.tv_email_error)
    AppCompatTextView emailError;

    @BindView(R.id.et_password)
    AppCompatEditText password;
    @BindView(R.id.tv_password_error)
    AppCompatTextView passwordError;

    @BindView(R.id.et_password_confirm)
    AppCompatEditText passwordConfirm;
    @BindView(R.id.tv_password_confirm_error)
    AppCompatTextView passwordConfirmError;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.registration_create)
    AppCompatButton registration;

    private Unbinder unbinder;

    private RegistrationContract.Presenter presenter;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        unbinder = ButterKnife.bind(this);

        firstNameError.setText(getString(R.string.format_empty_field, firstName.getHint().toString()));
        lastNameError.setText(getString(R.string.format_empty_field, lastName.getHint().toString()));
        usernameError.setText(getString(R.string.format_empty_field, username.getHint().toString()));
        emailError.setText(getString(R.string.format_incorrect_value, email.getHint().toString()));
        passwordError.setText(getString(R.string.format_empty_field, password.getHint().toString()));
        passwordConfirmError.setText(getString(R.string.format_incorrect_value, passwordConfirm.getHint().toString()));

        presenter = new RegistrationPresenter(this);

        elements = fillElementList();

        userRepository = RestClient.getClient().create(UserRepository.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unbinder.unbind();
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

    public User fillUserFromFields() {
        User user = new User();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setUsername(username.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        return user;
    }

    private List<View> fillElementList() {
        List<View> views = new ArrayList<>();
        views.add(firstName);
        views.add(lastName);
        views.add(username);
        views.add(email);
        views.add(password);

        return views;
    }

    public boolean isFieldsValid() {
        boolean isValid = true;
//        if (!isFieldNotEmpty(firstName, firstNameError)) {
//            isValid = false;
//        }
//        if (!isFieldNotEmpty(lastName, lastNameError)) {
//            isValid = false;
//        }
        if (!isFieldNotEmpty(username, usernameError)) {
            isValid = false;
        }
        if (!isEmailValid()) {
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
        values.put("firstName", firstName.getText().toString());
        values.put("lastName", lastName.getText().toString());
        values.put("username", username.getText().toString());
        values.put("email", email.getText().toString());
        values.put("password", passwordConfirm.getText().toString());

        return values;
    }

    @Override
    public boolean registerNewUser(User user) {
        Call<User> userCall = userRepository.create(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();

                    Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();

                    presenter.registrationSuccessful();
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();

                        presenter.registrationFailure();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.registrationFailure();

                call.cancel();
            }
        });

        return true;
    }

    @Override
    public void disableElements() {
        firstName.setEnabled(false);
        firstNameError.setEnabled(false);
        lastName.setEnabled(false);
        lastNameError.setEnabled(false);
        username.setEnabled(false);
        usernameError.setEnabled(false);
        email.setEnabled(false);
        emailError.setEnabled(false);
        password.setEnabled(false);
        passwordError.setEnabled(false);
        passwordConfirm.setEnabled(false);
        passwordConfirmError.setEnabled(false);
        registration.setEnabled(false);
    }

    @Override
    public void enableElements() {
        firstName.setEnabled(true);
        firstNameError.setEnabled(true);
        lastName.setEnabled(true);
        lastNameError.setEnabled(true);
        username.setEnabled(true);
        usernameError.setEnabled(true);
        email.setEnabled(true);
        emailError.setEnabled(true);
        password.setEnabled(true);
        passwordError.setEnabled(true);
        passwordConfirm.setEnabled(true);
        passwordConfirmError.setEnabled(true);
        registration.setEnabled(true);
    }

    @Override
    public void enableElements(boolean enabled) {
        firstName.setEnabled(enabled);
        firstNameError.setEnabled(enabled);
        lastName.setEnabled(enabled);
        lastNameError.setEnabled(enabled);
        username.setEnabled(enabled);
        usernameError.setEnabled(enabled);
        email.setEnabled(enabled);
        emailError.setEnabled(enabled);
        password.setEnabled(enabled);
        passwordError.setEnabled(enabled);
        passwordConfirm.setEnabled(enabled);
        passwordConfirmError.setEnabled(enabled);
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


    private boolean isEmailValid() {
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setVisibility(View.GONE);
            return true;
        } else {
            emailError.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isEmailValid(String email, AppCompatTextView errorTextView) {
        if (!isEmailValid(email)) {
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

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.matches(RegEx.WITHOUT_SPACE.getFullName()) && password.equals(confirmPassword);
    }


}
