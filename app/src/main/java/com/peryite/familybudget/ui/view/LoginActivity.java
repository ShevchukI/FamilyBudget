package com.peryite.familybudget.ui.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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
import com.peryite.familybudget.ui.contract.LoginContract;
import com.peryite.familybudget.ui.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.et_email)
    AppCompatEditText email;
    @BindView(R.id.et_password)
    AppCompatEditText password;
    @BindView(R.id.login_remember_me)
    AppCompatCheckBox rememberMe;
    @BindView(R.id.login_forgot_password)
    AppCompatTextView forgotPassword;
    @BindView(R.id.login_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.login_sign_in)
    AppCompatButton signIn;
    @BindView(R.id.login_create_new_account)
    AppCompatButton createNewAccount;
    @BindView(R.id.login_sign_in_with_google)
    AppCompatButton signInWithGoogle;


    private Unbinder unbinder;

    private LoginContract.Presenter presenter;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity", "onCreate: ");

        unbinder = ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
        progressBar.setVisibility(View.VISIBLE);
        disableElements();
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Log.d(TAG, "showMessage: ");
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unbinder.unbind();
    }

    @Override
    public void doSignIn() {
        Log.d(TAG, "doSignIn: ");
        preferences = getSharedPreferences(getResources().getString(R.string.visitedPreferences), MODE_PRIVATE);
        preferences.edit()
                .putBoolean(getResources().getString(R.string.visitedPreferences), rememberMe.isChecked())
                .apply();
        //TODO sign in
    }

    @Override
    public void disableElements() {
        email.setEnabled(false);
        password.setEnabled(false);
        rememberMe.setEnabled(false);
        forgotPassword.setEnabled(false);
        signIn.setEnabled(false);
        createNewAccount.setEnabled(false);
        signInWithGoogle.setEnabled(false);
    }

    @Override
    public void enableElements() {
        email.setEnabled(true);
        password.setEnabled(true);
        rememberMe.setEnabled(true);
        forgotPassword.setEnabled(true);
        signIn.setEnabled(true);
        createNewAccount.setEnabled(true);
        signInWithGoogle.setEnabled(true);
    }


}
