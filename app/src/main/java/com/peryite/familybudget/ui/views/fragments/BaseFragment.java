package com.peryite.familybudget.ui.views.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.ui.listeners.BudgetFragmentListener;

public abstract class BaseFragment extends Fragment {
    protected View view;

    protected Context context;

    protected Credential credential;

    protected BudgetFragmentListener listener;

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public void refresh(){

    }

    public BudgetFragmentListener getListener() {
        return listener;
    }

    public void setListener(BudgetFragmentListener listener) {
        this.listener = listener;
    }
}
