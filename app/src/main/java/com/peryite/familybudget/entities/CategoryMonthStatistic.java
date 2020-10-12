package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryMonthStatistic {
    @SerializedName("category")
    private BudgetCategory budgetCategory;
    @SerializedName("itemStatistics")
    private List<ItemStatisticEntity> itemStatisticEntities;

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public List<ItemStatisticEntity> getItemStatisticEntities() {
        return itemStatisticEntities;
    }

    public void setItemStatisticEntities(List<ItemStatisticEntity> itemStatisticEntities) {
        this.itemStatisticEntities = itemStatisticEntities;
    }
}
