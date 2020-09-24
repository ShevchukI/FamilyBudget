package com.peryite.familybudget.ui.views.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.listeners.BudgetFragmentListener;

import java.util.List;

public abstract class BaseFragment extends Fragment implements BaseView {
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

    public void refresh() {

    }

    public BudgetFragmentListener getListener() {
        return listener;
    }

    public void setListener(BudgetFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void showProgress(ProgressBar progressBar) {

    }

    @Override
    public void hideProgress(ProgressBar progressBar) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void disableElements(List<View> elements) {

    }

    @Override
    public void disableElements() {

    }

    @Override
    public void enableElements(List<View> elements) {

    }

    @Override
    public void enableElements() {

    }

    @Override
    public void enableElements(boolean enabled) {

    }

    @Override
    public void startActivity(Context packageContext, Class cls) {

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
