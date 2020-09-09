package com.peryite.familybudget.ui.presenters;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.BudgetContract;
import com.peryite.familybudget.ui.listeners.BudgetListener;
import com.peryite.familybudget.ui.views.fragments.BaseFragment;
import com.peryite.familybudget.ui.views.fragments.BudgetCategoryFragment;
import com.peryite.familybudget.ui.views.fragments.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class BudgetPresenter implements BudgetContract.Presenter {
    private BudgetContract.View view;
    private BudgetContract.Model model;
    private final String TAG = this.getClass().getSimpleName();

    public BudgetPresenter(final BudgetContract.Model model){
        model.setListener(new BudgetListener() {
            @Override
            public void onResponse(int code) {
                view.showMessage("Response code: " + code);
            }

            @Override
            public void sendAlexaCode(String text) {
                view.showDialog("Alexa code", text);
            }

            @Override
            public void errorMessage(String message) {

            }

            @Override
            public void setUser(User user) {
                view.setUserInfo(user);
            }

            @Override
            public void setUsers(List<User> users) {

            }

            @Override
            public void onResponse() {

            }

            @Override
            public void onFailure() {
                view.showMessage("Fail");
            }
        });

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
        view.showProgress();

        FragmentManager.getInstance().setFragmentList(fillFragmentList());

        model.getUser();
        view.selectNavigation(R.id.nav_category);
        view.selectFragment(FragmentManager.FragmentSelect.BudgetCategory);

        view.hideProgress();
    }

    @Override
    public void onClickBudgetCategory() {
        view.selectFragment(FragmentManager.FragmentSelect.BudgetCategory);
    }

    @Override
    public void onClickRefresh() {
        //TODO: refresh fragment
        view.showProgress();
        model.getUser();
        view.refreshFragment(FragmentManager.FragmentSelect.BudgetCategory);
        view.showMessage("Refresh!");
        view.selectFragment(FragmentManager.FragmentSelect.BudgetCategory);

        view.hideProgress();
    }

    @Override
    public void onClickSetting() {
        view.showMessage("Setting");
    }

    @Override
    public void onClickAlexaCode() {
        model.getAlexaCode();
    }

    private List<BaseFragment> fillFragmentList(){
        List<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(new BudgetCategoryFragment());

        return baseFragments;
    }
}
