package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.views.BudgetActivity;
import com.peryite.familybudget.ui.views.LoginActivity;


public class InitialPresenter implements InitialContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    private InitialContract.View view;
    private InitialContract.Model model;

    public InitialPresenter(InitialContract.Model model) {
        model.setListener(new BaseAPIRequestListener() {
            @Override
            public void onResponse() {
                if (isVisited()) {
                    //TODO: created open BudgetActivity
                    view.openActivity(BudgetActivity.class);
                } else {
                    view.openActivity(LoginActivity.class);
                }
            }

            @Override
            public void onFailure() {
                view.hideProgress();
                view.showMessage("The server does not respond. Please try restart application");
            }
        });

        this.model = model;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (InitialContract.View) view;
    }

    public void detachView() {
        view = null;
    }

    @Override
    public void start() {
        view.showProgress();

        model.checkHealth();
    }

    public InitialContract.View getView() {
        return view;
    }

    public void setView(InitialContract.View view) {
        this.view = view;
    }

    private boolean isVisited() {
        return model.isVisitedUser();
    }
}
