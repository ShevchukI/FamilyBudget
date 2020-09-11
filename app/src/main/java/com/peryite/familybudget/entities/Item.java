package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Item {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("itemsDate")
    private Timestamp date;
    @SerializedName("price")
    private Double price;
    @SerializedName("category")
    private BudgetCategory budgetCategory;

    private transient boolean expanded;

    private Item(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public static class Builder{
        private Long id;
        private String name;
        private String description;
        private Timestamp date;
        private Double price;
        private BudgetCategory budgetCategory;

        public Builder(){

        }

        public Builder withName(String name){
            this.name = name;

            return this;
        }

        public Builder withDescription(String description){
            this.description = description;

            return this;
        }

        public Builder withDate(Timestamp date){
            this.date = date;

            return this;
        }

        public Builder withPrice(Double price){
            this.price = price;

            return this;
        }

        public Builder withCategory(BudgetCategory budgetCategory){
            this.budgetCategory = budgetCategory;

            return this;
        }

        public Builder withNowDate(){
            this.date = new Timestamp(System.currentTimeMillis());

            return this;
        }

        public Builder asEarned(Double price){
            this.name = "Earnings";
            this.date = new Timestamp(System.currentTimeMillis());
            this.price = price;

            return this;
        }

        public Item build(){
            Item item = new Item();
            item.id = this.id;
            item.name = this.name;
            item.description = this.description;
            item.date = this.date;
            item.price = this.price;
            item.budgetCategory = this.budgetCategory;

            return item;
        }
    }
}