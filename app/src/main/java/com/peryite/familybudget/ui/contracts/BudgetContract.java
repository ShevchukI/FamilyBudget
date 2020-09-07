package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.views.fragments.FragmentManager;

public interface BudgetContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {
        void selectFragment(FragmentManager.FragmentSelect fragmentSelect);

        void selectNavigation(int id);
    }

    interface Presenter extends BasePresenter {
        void start();

        void onClickBudgetCategory();
    }
}
