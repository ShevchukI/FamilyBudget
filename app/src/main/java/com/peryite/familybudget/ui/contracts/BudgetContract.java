package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.listeners.BudgetFragmentListener;
import com.peryite.familybudget.ui.listeners.OnBudgetCategoryListener;
import com.peryite.familybudget.ui.views.fragments.FragmentManager;

public interface BudgetContract {
    interface Model extends BaseModel {
        void getUser();

        void setUser(User user);

        void getAlexaCode();

        void addBudget(Item budget);
    }

    interface View extends BaseView {
        void selectFragment(FragmentManager.FragmentSelect fragmentSelect);

        void selectNavigation(int id);

        void setBudget(double budget);

        void setUserInfo(User user);

        void refreshFragment(FragmentManager.FragmentSelect budgetCategory);

        void showDialog(String title, String text);

        void setListenerOnFragment(FragmentManager.FragmentSelect budgetCategory, BudgetFragmentListener budgetFragmentListener);
    }

    interface Presenter extends BasePresenter {
        void start();

        void onClickBudgetCategory();

        void onClickRefresh();

        void onClickSetting();

        void onClickAlexaCode();

        void onClickAddBudget(Item budget);
    }
}
