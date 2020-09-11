package com.peryite.familybudget.ui.listeners;

import com.peryite.familybudget.entities.BudgetCategory;

public interface OnBudgetCategoryListener extends BudgetFragmentListener {
    void openCategory(BudgetCategory budgetCategory);

    void refreshBudget();
}
