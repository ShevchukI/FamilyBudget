package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

public class StatisticPoint {
    @SerializedName("category")
    private BudgetCategory budgetCategory;
    @SerializedName("price")
    private float price;
    @SerializedName("amount")
    private int amountItem;
    @SerializedName("month")
    private int monthNumber;

    public StatisticPoint() {
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmountItem() {
        return amountItem;
    }

    public void setAmountItem(int amountItem) {
        this.amountItem = amountItem;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }
}
