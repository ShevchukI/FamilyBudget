package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

public class ItemStatisticEntity {
    @SerializedName("price")
    private float price;
    @SerializedName("amount")
    private int amount;
    @SerializedName("month")
    private int month;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
