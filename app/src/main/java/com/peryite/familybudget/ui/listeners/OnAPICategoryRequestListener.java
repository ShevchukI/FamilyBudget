package com.peryite.familybudget.ui.listeners;

import com.peryite.familybudget.entities.BudgetCategory;

import java.util.List;

public interface OnAPICategoryRequestListener extends BaseAPIRequestListener{
    void setCategories(List<BudgetCategory> budgetCategories);
}
