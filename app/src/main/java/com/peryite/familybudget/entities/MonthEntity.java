package com.peryite.familybudget.entities;

public class MonthEntity {
    private MonthType monthType;
    private float price;

    public MonthEntity(MonthType monthType) {
        this.monthType = monthType;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void addPrice(float price){
        this.price +=price;
    }

    public enum MonthType {
        January ("January"),
        February  ("February"),
        March  ("March"),
        April ("April"),
        May ("May"),
        June ("June"),
        July ("July"),
        August ("August"),
        September ("September"),
        October ("October"),
        November ("November"),
        December ("December");

        private final String name;

        private MonthType(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }
}
