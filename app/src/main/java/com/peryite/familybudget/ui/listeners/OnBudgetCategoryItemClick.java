package com.peryite.familybudget.ui.listeners;

public interface OnBudgetCategoryItemClick {
    void onOpenCategoryClick(int id);

    void onAddCategoryItemClick(int id);

    void onEditCategoryClick(int id);

    void onDeleteCategoryClick(int id);

    void showListenerMessage(String text);
}
