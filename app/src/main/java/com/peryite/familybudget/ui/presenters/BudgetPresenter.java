package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.BudgetContract;
import com.peryite.familybudget.ui.views.fragments.FragmentManager;

public class BudgetPresenter implements BudgetContract.Presenter {
    private BudgetContract.View view;
    private BudgetContract.Model model;

    public BudgetPresenter(BudgetContract.Model model){
        this.model = model;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (BudgetContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void start() {
        view.selectNavigation(R.id.nav_category);
        view.selectFragment(FragmentManager.FragmentSelect.BudgetCategory);
    }

    @Override
    public void onClickBudgetCategory() {
        view.selectFragment(FragmentManager.FragmentSelect.BudgetCategory);
    }
}
