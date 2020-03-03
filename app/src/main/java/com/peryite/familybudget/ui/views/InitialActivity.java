package com.peryite.familybudget.ui.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.presenters.InitialPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InitialActivity extends BaseActivity implements InitialContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

//    private Unbinder unbinder;

    private InitialContract.Presenter presenter;
    private SharedPreferences preferencesVisited;
    private SharedPreferences preferencesCredential;
    private boolean hasVisited;
    private String credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Log.d(TAG, "onCreate:");

        unbinder = ButterKnife.bind(this);

        preferencesVisited = getSharedPreferences(getResources().getString(R.string.visitedPreferences), MODE_PRIVATE);
        hasVisited = preferencesVisited.getBoolean(getResources().getString(R.string.visitedPreferences), false);

        preferencesCredential = getSharedPreferences("credential", MODE_PRIVATE);
        credential = preferencesCredential.getString("credential", "empty");

//        Intent intent = new Intent(this, CategoryItemActivity.class);
//        startActivity(intent);

        presenter = new InitialPresenter.Builder().withView(this)
                .withVisited(hasVisited)
                .withCredential(credential)
                .build();

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy: ");
//        unbinder.unbind();
//    }

}
