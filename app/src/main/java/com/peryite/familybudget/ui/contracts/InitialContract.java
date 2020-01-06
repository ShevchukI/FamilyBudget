package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

public interface InitialContract {
    interface Model {

    }

    interface View extends BaseView {
        void openActivity(Class<?> activityClass);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends BasePresenter {
        void start();
    }
}
