package com.peryite.familybudget.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class CategoryItem {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private Double price;
    @SerializedName("description")
    private String description;
    @SerializedName("itemsDate")
    private Timestamp date;

    @Expose
    private boolean expanded;

    public CategoryItem() {
    }

    public CategoryItem(Long id, String name, Double price, String description, Timestamp date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.date = date;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
