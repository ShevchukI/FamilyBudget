package com.peryite.familybudget.ui.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.models.InitialModel;
import com.peryite.familybudget.ui.presenters.InitialPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitialActivity extends BaseActivity implements InitialContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private InitialContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Log.d(TAG, "onCreate:");

        init();
    }

    private void init(){
        unbinder = ButterKnife.bind(this);

        SharedPreferences preferencesVisited = getSharedPreferences(getResources().getString(R.string.visitedPreferences), MODE_PRIVATE);
        boolean hasVisited = preferencesVisited.getBoolean(getResources().getString(R.string.visitedPreferences), false);

        SharedPreferences preferencesCredential = getSharedPreferences("credential", MODE_PRIVATE);
        String credential = preferencesCredential.getString("credential", "empty");

        InitialModel initialModel = new InitialModel(hasVisited, credential);
        presenter = new InitialPresenter(initialModel);
        presenter.attachView(this);

        presenter.start();
    }

    @Override
    public void showProgress() {
        showProgress(progressBar);
    }

    @Override
    public void hideProgress() {
        hideProgress(progressBar);
    }


    @Override
    public void openActivity(Class<?> activityClass) {
        Log.d(TAG, String.format(getResources().getString(R.string.open_activity), activityClass.getSimpleName()));
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        presenter.detachView();
    }

}
