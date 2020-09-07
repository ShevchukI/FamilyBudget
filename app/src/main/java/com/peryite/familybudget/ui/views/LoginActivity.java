package com.peryite.familybudget.ui.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.peryite.familybudget.api.repository.AuthorizationRepository;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.contracts.LoginContract;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.Login;
import com.peryite.familybudget.ui.models.LoginModel;
import com.peryite.familybudget.ui.presenters.LoginPresenter;
import com.peryite.familybudget.utils.GsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.et_username)
    AppCompatEditText username;
    @BindView(R.id.et_password)
    AppCompatEditText password;

    @BindView(R.id.login_remember_me)
    AppCompatCheckBox rememberMe;
    @BindView(R.id.login_forgot_password)
    AppCompatTextView forgotPassword;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.login_sign_in)
    AppCompatButton signIn;
    @BindView(R.id.login_create_new_account)
    AppCompatButton createNewAccount;
    @BindView(R.id.login_sign_in_with_google)
    AppCompatButton signInWithGoogle;

    private SharedPreferences preferencesCredential;
    private SharedPreferences preferencesHasVisited;

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity", "onCreate: ");

        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        elements = fillElementList();

        preferencesCredential = getSharedPreferences(getResources().getString(R.string.credentialPreferences), MODE_PRIVATE);

        preferencesHasVisited = getSharedPreferences(getResources().getString(R.string.visitedPreferences), MODE_PRIVATE);
        preferencesHasVisited.edit()
                .putBoolean(getResources().getString(R.string.visitedPreferences), false)
                .apply();

        LoginModel loginModel = new LoginModel();

        presenter = new LoginPresenter(loginModel);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        presenter.detachView();
    }

    @Override
    public void doSignIn() {
        Intent intent = new Intent(this, BudgetActivity.class);
        if(rememberMe.isChecked()){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }

    @Override
    public void doSignIn(Login login) {
//        Log.d(TAG, "doSignIn: ");
//        Call<Credential> credentialCall = authorizationRepository.login(login);
//        credentialCall.enqueue(new Callback<Credential>() {
//            @Override
//            public void onResponse(Call<Credential> call, Response<Credential> response) {
//                if (response.isSuccessful()) {
//                    Credential credential = response.body();
//                    if (credential != null) {
//
//                        String json = GsonUtil.toJson(credential);
//                        preferencesCredential.edit().putString(getResources().getString(R.string.credentialPreferences), json)
//                                .apply();
//
//                        preferencesHasVisited.edit()
//                                .putBoolean(getResources().getString(R.string.visitedPreferences), rememberMe.isChecked())
//                                .apply();
//
//                        presenter.signInSuccessful();
//                    }
//                } else {
//                    if (response.code() == 403) {
//                        Toast.makeText(getApplicationContext(), "Username or password incorrect!", Toast.LENGTH_LONG).show();
//                        presenter.signInFailure();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Credential> call, Throwable t) {
//                presenter.signInFailure();
//                call.cancel();
//            }
//        });

    }


    @Override
    public void doCreateAccount() {
        Log.d(TAG, "doCreateAccount: ");
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean isFieldsValid() {
        return false;
    }

    @Override
    public void saveCredential() {
        String json = GsonUtil.toJson(new Credential(username.getText().toString(), password.getText().toString()));

        preferencesCredential.edit().putString(getResources().getString(R.string.credentialPreferences), json)
                .apply();

        preferencesHasVisited.edit()
                .putBoolean(getResources().getString(R.string.visitedPreferences), rememberMe.isChecked())
                .apply();
    }


    @OnClick(R.id.login_sign_in)
    public void clickOnSignIn() {
        presenter.onClickSignIn(username.getText().toString(), password.getText().toString(), rememberMe.isChecked());
    }

    @OnClick(R.id.login_create_new_account)
    public void clickOnCreateNewAccount() {
        presenter.onClickCreateNewAccount();
    }

    private List<View> fillElementList() {
        List<View> views = new ArrayList<>();
        views.add(username);
        views.add(password);
        views.add(rememberMe);
        views.add(signIn);
        views.add(createNewAccount);

        return views;
    }
}
