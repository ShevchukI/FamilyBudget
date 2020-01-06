package com.peryite.familybudget.ui.presenters;

import android.util.Log;

import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.views.BudgetActivity;
import com.peryite.familybudget.ui.views.LoginActivity;

public class InitialPresenter implements InitialContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

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

        if (!hasVisited) {
            view.openActivity(LoginActivity.class);
        } else {
            //TODO authorization

            view.openActivity(BudgetActivity.class);
        }
    }
}
