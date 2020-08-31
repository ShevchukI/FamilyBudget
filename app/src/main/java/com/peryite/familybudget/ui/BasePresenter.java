package com.peryite.familybudget.ui;

public interface BasePresenter {
    void attachView(BaseView view);

    void detachView();
}
