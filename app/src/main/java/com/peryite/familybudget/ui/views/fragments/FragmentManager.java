package com.peryite.familybudget.ui.views.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.ui.listeners.BudgetFragmentListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentManager {
    private static FragmentManager instance;

    private List<BaseFragment> fragmentList;

    public static FragmentManager getInstance() {
        if (instance == null) {
            instance = new FragmentManager();
        }
        return instance;
    }

    private FragmentManager() {
        //fragmentList = fillFragmentList();
    }

    private List<BaseFragment> fillFragmentList() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new BudgetCategoryFragment());
        return fragments;
    }

    public BaseFragment getFragment(FragmentSelect fragmentSelect) {
        BaseFragment fragment = null;
        switch (fragmentSelect) {
            case BudgetCategory:
                fragment = fragmentList.get(0);
                break;
            case BudgetItem:
                fragment = fragmentList.get(1);
                break;
            case InsertChart:
                fragment = fragmentList.get(2);
                break;
            default:
                break;
        }

        return fragment;
    }

    public void setContextOnFragment(FragmentSelect fragmentSelect, Context context) {
        switch (fragmentSelect) {
            case BudgetCategory:
                fragmentList.get(0).setContext(context);
                break;
            case BudgetItem:
                fragmentList.get(1).setContext(context);
                break;
            case InsertChart:
                fragmentList.get(2).setContext(context);
                break;
            default:
                break;
        }
    }

    public void setCredentialOnFragment(FragmentSelect fragmentSelect, Credential credential) {
        switch (fragmentSelect) {
            case BudgetCategory:
                fragmentList.get(0).setCredential(credential);
                break;
            case BudgetItem:
                fragmentList.get(1).setCredential(credential);
                break;
            case InsertChart:
                fragmentList.get(2).setCredential(credential);
                break;
            default:
                break;
        }
    }

    public void setListenerOnFragment(FragmentSelect fragmentSelect, BudgetFragmentListener listener){
        switch (fragmentSelect) {
            case BudgetCategory:
                fragmentList.get(0).setListener(listener);
                break;
            case BudgetItem:
                fragmentList.get(1).setListener(listener);
                break;
            case InsertChart:
                fragmentList.get(2).setListener(listener);
                break;
            default:
                break;
        }
    }

    public void setFragmentList(List<BaseFragment> baseFragments) {
        fragmentList = baseFragments;
    }


    public enum FragmentSelect {
        BudgetCategory,
        BudgetItem,
        InsertChart;

    }


}
