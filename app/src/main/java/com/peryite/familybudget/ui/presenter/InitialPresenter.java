package com.peryite.familybudget.ui.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.peryite.familybudget.ui.contract.InitialContract;
import com.peryite.familybudget.ui.view.BudgetActivity;
import com.peryite.familybudget.ui.view.LoginActivity;

public class InitialPresenter implements InitialContract.Presenter {
    private String TAG = this.getClass().getSimpleName();

    private InitialContract.View view;
    private boolean hasVisited;

    public InitialPresenter(InitialContract.View view, boolean hasVisited) {
        Log.d(TAG, "InitialPresenter: created with view = " + view + " and hasVisited = " + hasVisited);
        this.view = view;
        this.hasVisited = hasVisited;
    }


    @Override
    public void start() {
        Log.d(TAG, "start: hasVisited = " + hasVisited);
        view.showProgress();

        if (hasVisited) {
            view.openActivity(BudgetActivity.class);
        } else {
            view.openActivity(LoginActivity.class);
        }
    }
}
