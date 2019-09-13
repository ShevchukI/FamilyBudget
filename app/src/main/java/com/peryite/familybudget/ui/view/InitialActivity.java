package com.peryite.familybudget.ui.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.contract.InitialContract;
import com.peryite.familybudget.ui.presenter.InitialPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InitialActivity extends AppCompatActivity implements InitialContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.progress_bar_initial)
    ProgressBar progressBar;

    private Unbinder unbinder;

    private InitialContract.Presenter presenter;
    private SharedPreferences preferences;
    private boolean hasVisited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Log.d(TAG, "onCreate:");

        unbinder = ButterKnife.bind(this);

        preferences = getSharedPreferences(getResources().getString(R.string.visitedPreferences), MODE_PRIVATE);
        hasVisited = preferences.getBoolean(getResources().getString(R.string.visitedPreferences), false);

        presenter = new InitialPresenter(this, hasVisited);
        presenter.start();
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Log.d(TAG, "showMessage: ");
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
        unbinder.unbind();
    }
}
