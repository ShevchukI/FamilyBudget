package com.peryite.familybudget.ui;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public interface BaseView {

    void showProgress(ProgressBar progressBar);

    void hideProgress(ProgressBar progressBar);

    void showMessage(String message);

    void disableElements(List<View> elements);

    void disableElements();

    void enableElements(List<View> elements);

    void enableElements();

    void enableElements(boolean enabled);

    void startActivity(Context packageContext, Class cls);

    void closeActivity();
}
